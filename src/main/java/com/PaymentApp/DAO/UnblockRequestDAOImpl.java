package com.PaymentApp.DAO;

import com.PaymentApp.entities.Card;
import com.PaymentApp.entities.UnblockRequest;

import javax.servlet.RequestDispatcher;
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


    public UnblockRequestDAOImpl() {
        try {
            Class.forName(DRIVER);
            System.out.println("Successfully!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static synchronized UnblockRequestDAOImpl getInstance() {
        if (unblockRequestDAO == null) {
            unblockRequestDAO = new UnblockRequestDAOImpl();
        }
        return unblockRequestDAO;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }


}
