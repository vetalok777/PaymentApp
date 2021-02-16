package com.PaymentApp.DAO;

import com.PaymentApp.entities.UnblockRequest;

import javax.servlet.RequestDispatcher;
import java.sql.*;
import java.time.LocalDateTime;

public class UnblockRequestDAOImpl {
    private static final String URL = "jdbc:mysql://localhost/payment_system_db?serverTimezone=UTC";
    private static final String PASSWORD = "root";
    private static final String USERNAME = "root";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String INSERT_NEW_REQUEST = "INSERT INTO unblock_request (request_creation_date, card_id) VALUES(?,?);";
    private static final String FIND_REQUEST_BY_CARD_ID = "SELECT card_id FROM unblock_request WHERE card_id=(?);";

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

    public static void main(String[] args) throws SQLException {
        UnblockRequest unblockRequest = new UnblockRequest(LocalDateTime.now(), 11);
        System.out.println(UnblockRequestDAOImpl.getInstance().findRequest(1));
    }
}
