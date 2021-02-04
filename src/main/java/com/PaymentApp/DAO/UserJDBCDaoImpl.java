package com.PaymentApp.DAO;

import com.PaymentApp.entities.User;

import java.sql.*;

public class UserJDBCDaoImpl implements UserDAO {
    private static final String URL = "jdbc:mysql://localhost/payment_system_db?serverTimezone=UTC";
    private static final String PASSWORD = "root";
    private static final String USERNAME = "root";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String INSERT_NEW_USER = "INSERT INTO user (email, password, first_name) VALUES (?, ?, ?);";
    private static final String FIND_USER = "SELECT email FROM user WHERE email = (?);";


    private static UserJDBCDaoImpl userJDBCDaoImpl;

    public UserJDBCDaoImpl() {
        try {
            Class.forName(DRIVER);
            System.out.println("Successfully!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static synchronized UserJDBCDaoImpl getInstance() {
        if (userJDBCDaoImpl == null) {
            userJDBCDaoImpl = new UserJDBCDaoImpl();
        }
        return userJDBCDaoImpl;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    @Override
    public int insertUser(User user) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        UserJDBCDaoImpl userJDBCDaoImpl = UserJDBCDaoImpl.getInstance();
        int result = 0;
        try {
            connection = userJDBCDaoImpl.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_NEW_USER);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
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

    public String findByLogin(String login) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        String result = null;
        UserJDBCDaoImpl userJDBCDaoImpl = UserJDBCDaoImpl.getInstance();
        try {
            connection = userJDBCDaoImpl.getConnection();
            preparedStatement = connection.prepareStatement(FIND_USER);
            preparedStatement.setString(1, login);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                result = rs.getString(1);
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
        UserJDBCDaoImpl userJDBCDaoImpl = UserJDBCDaoImpl.getInstance();
        User user = new User("vetalok777@gmail.com", "123123", "vit");
        userJDBCDaoImpl.insertUser(user);
    }
}
