package com.PaymentApp.DAO;

import com.PaymentApp.entities.Card;
import com.PaymentApp.entities.UnblockRequest;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UnblockRequestDAOImpl implements UnblockRequestDAO {
    private static final String URL = "jdbc:mysql://localhost/payment_system_db?serverTimezone=UTC";
    private static final String PASSWORD = "root";
    private static final String USERNAME = "root";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String INSERT_NEW_REQUEST = "INSERT INTO unblock_request (request_creation_date, card_id) VALUES(?,?);";
    private static final String FIND_REQUEST_BY_CARD_ID = "SELECT card_id FROM unblock_request WHERE card_id=(?) AND request_status = 'not considered';";
    private static final String FIND_ALL_REQUESTS = "SELECT id, request_status, request_creation_date, card_id  FROM unblock_request;";
    private static final String UPDATE_REQUEST_STATUS = "UPDATE unblock_request SET request_status = (?) WHERE id=(?);";

    private static UnblockRequestDAOImpl unblockRequestDAO;
    final Logger LOGGER = Logger.getLogger(UnblockRequestDAOImpl.class);

    public UnblockRequestDAOImpl() {
        try {
            Class.forName(DRIVER);
            LOGGER.info("Driver connected successfully");
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static synchronized UnblockRequestDAOImpl getInstance() {
        if (unblockRequestDAO == null) {
            unblockRequestDAO = new UnblockRequestDAOImpl();
        }
        return unblockRequestDAO;
    }

    public Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");


            DataSource ds = (DataSource) envContext.lookup("jdbc/PaymentDB");
            connection = ds.getConnection();
            LOGGER.info("Connection is successful");
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
        return connection;
    }


    public void commitAndClose(Connection con) {
        try {
            con.commit();
            con.close();
            LOGGER.info("Connection commit and close");
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    public void rollbackAndClose(Connection con) {
        try {
            con.rollback();
            con.close();
            LOGGER.info("Connection rollback and close");
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    @Override
    public int insertRequest(UnblockRequest unblockRequest) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        UnblockRequestDAOImpl unblockRequestDAO = UnblockRequestDAOImpl.getInstance();
        int result = 0;
        try {
            connection = unblockRequestDAO.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_NEW_REQUEST);
            preparedStatement.setString(1, String.valueOf(unblockRequest.getCreationDate()));
            preparedStatement.setInt(2, unblockRequest.getCardId());
            result = preparedStatement.executeUpdate();
            LOGGER.info("Requset inserted");
        } catch (SQLException e) {
            assert connection != null;
            unblockRequestDAO.rollbackAndClose(connection);
            LOGGER.error(e.getMessage());
        } finally {
            assert connection != null;
            unblockRequestDAO.commitAndClose(connection);
        }
        return result;
    }

    @Override
    public int findRequest(Integer cardId) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        UnblockRequestDAOImpl unblockRequestDAO = UnblockRequestDAOImpl.getInstance();
        int result = 0;
        try {
            connection = unblockRequestDAO.getConnection();
            preparedStatement = connection.prepareStatement(FIND_REQUEST_BY_CARD_ID);
            preparedStatement.setInt(1, cardId);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                result = 1;
            }
            LOGGER.info("Found request");
        } catch (SQLException e) {
            assert connection != null;
            unblockRequestDAO.rollbackAndClose(connection);
            LOGGER.error(e.getMessage());
        } finally {
            assert connection != null;
            unblockRequestDAO.commitAndClose(connection);
        }
        return result;
    }

    @Override
    public List<UnblockRequest> findAllRequests() throws SQLException {
        List<UnblockRequest> requests = new ArrayList<>();
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = unblockRequestDAO.getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL_REQUESTS);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Integer id = rs.getInt("id");
                String status = rs.getString("request_status");
                LocalDateTime date = LocalDateTime.parse(rs.getString("request_creation_date"), DateTimeFormatter.ofPattern("yyyy-M-d HH':'mm':'ss.SSSSSS"));
                Integer cardId = rs.getInt("card_id");
                UnblockRequest unblockRequest = new UnblockRequest(id, status, date, cardId);
                requests.add(unblockRequest);
            }
            LOGGER.info("Found all requests");
        } catch (SQLException e) {
            assert connection != null;
            unblockRequestDAO.rollbackAndClose(connection);
            LOGGER.error(e.getMessage());
        } finally {
            assert connection != null;
            unblockRequestDAO.commitAndClose(connection);
        }
        return requests;
    }

    @Override
    public int changeRequestStatus(Integer id, String status) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        CardJDBCDaoImpl cardJDBCDaoImpl = CardJDBCDaoImpl.getInstance();
        int result = 0;
        try {
            connection = cardJDBCDaoImpl.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_REQUEST_STATUS);
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, id);
            result = preparedStatement.executeUpdate();
            LOGGER.info("Request status changed");
        } catch (SQLException e) {
            assert connection != null;
            unblockRequestDAO.rollbackAndClose(connection);
            LOGGER.error(e.getMessage());
        } finally {
            assert connection != null;
            unblockRequestDAO.commitAndClose(connection);
        }
        return result;
    }


}
