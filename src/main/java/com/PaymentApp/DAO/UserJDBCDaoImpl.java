package com.PaymentApp.DAO;

import com.PaymentApp.entities.User;

import java.sql.*;

public class UserJDBCDaoImpl implements UserDAO {
    private static final String URL = "jdbc:mysql://localhost/payment_system_db?serverTimezone=UTC";
    private static final String PASSWORD = "root";
    private static final String USERNAME = "root";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String INSERT_NEW_USER = "INSERT INTO user (email, password, first_name) VALUES (?, ?, ?);";
    private static final String FIND_USER_BY_PASSWORD = "SELECT password FROM user WHERE email = (?);";
    private static final String FIND_USER = "SELECT *  FROM user WHERE email = (?);";


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


    public String findUserPassword(String login) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        String result = null;
        try {
            connection = userJDBCDaoImpl.getConnection();
            preparedStatement = connection.prepareStatement(FIND_USER_BY_PASSWORD);
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

    @Override
    public User findUser(String login) throws SQLException {
        User user = new User();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = userJDBCDaoImpl.getConnection();
            preparedStatement = connection.prepareStatement(FIND_USER);
            preparedStatement.setString(1, login);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                user.setFirstName(rs.getString("first_name"));
                user.setLogin(rs.getString("email"));
                user.setId(rs.getInt("id"));
                user.setPassword(rs.getString("password"));
                user.setStatus(rs.getInt("user_status"));
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
        return user;
    }

    public static void main(String[] args) throws SQLException {
        String name = "vetalok777@gmail.com";
        userJDBCDaoImpl = UserJDBCDaoImpl.getInstance();
        User user = userJDBCDaoImpl.findUser(name);
        System.out.println(user.getFirstName());
    }
}
