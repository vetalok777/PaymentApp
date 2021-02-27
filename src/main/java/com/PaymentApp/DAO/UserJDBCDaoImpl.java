package com.PaymentApp.DAO;

import com.PaymentApp.entities.UnblockRequest;
import com.PaymentApp.entities.User;
import org.apache.log4j.Logger;
import sun.rmi.runtime.Log;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserJDBCDaoImpl implements UserDAO {
    private static final String URL = "jdbc:mysql://localhost/payment_system_db?serverTimezone=UTC";
    private static final String PASSWORD = "root";
    private static final String USERNAME = "root";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String INSERT_NEW_USER = "INSERT INTO user (email, password, first_name) VALUES (?, ?, ?);";
    private static final String FIND_USER_BY_PASSWORD = "SELECT password FROM user WHERE email = (?);";
    private static final String FIND_USER = "SELECT *  FROM user WHERE email = (?);";
    private static final String FIND_ALL_USERS = "SELECT *  FROM user;";
    private static final String UPDATE_USER_STATUS = "UPDATE user SET user_status = (?) WHERE id=(?);";

    private static UserJDBCDaoImpl userJDBCDaoImpl;
    final Logger LOGGER = Logger.getLogger(UserJDBCDaoImpl.class);

    public UserJDBCDaoImpl() {
        try {
            Class.forName(DRIVER);
            LOGGER.info("Driver connected successfully");
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static synchronized UserJDBCDaoImpl getInstance() {
        if (userJDBCDaoImpl == null) {
            userJDBCDaoImpl = new UserJDBCDaoImpl();
        }
        return userJDBCDaoImpl;
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
            LOGGER.error(ex.getMessage());
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
            LOGGER.info("User inserted");
        } catch (SQLException e) {
            assert connection != null;
            userJDBCDaoImpl.rollbackAndClose(connection);
            LOGGER.error(e.getMessage());
        } finally {
            assert connection != null;
            userJDBCDaoImpl.commitAndClose(connection);
        }
        return result;
    }

    @Override
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
            LOGGER.info("User password is found");
        } catch (SQLException e) {
            assert connection != null;
            userJDBCDaoImpl.rollbackAndClose(connection);
            LOGGER.error(e.getMessage());
        } finally {
            assert connection != null;
            userJDBCDaoImpl.commitAndClose(connection);
        }
        return result;
    }

    @Override
    public User findUser(String login) throws SQLException {
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = userJDBCDaoImpl.getConnection();
            preparedStatement = connection.prepareStatement(FIND_USER);
            preparedStatement.setString(1, login);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setFirstName(rs.getString("first_name"));
                user.setLogin(rs.getString("email"));
                user.setId(rs.getInt("id"));
                user.setPassword(rs.getString("password"));
                user.setStatus(rs.getInt("user_status"));
            }
            LOGGER.info("Found user");
        } catch (SQLException e) {
            assert connection != null;
            userJDBCDaoImpl.rollbackAndClose(connection);
            LOGGER.error(e.getMessage());
        } finally {
            assert connection != null;
            userJDBCDaoImpl.commitAndClose(connection);
        }
        return user;
    }

    @Override
    public List<User> findAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = userJDBCDaoImpl.getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL_USERS);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Integer id = rs.getInt("id");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String firstName = rs.getString("first_name");
                Integer status = rs.getInt("user_status");
                User user = new User(id, email, password, firstName, status);
                users.add(user);
            }
            LOGGER.info("Found all users");
        } catch (SQLException e) {
            assert connection != null;
            userJDBCDaoImpl.rollbackAndClose(connection);
            LOGGER.error(e.getMessage());
        } finally {
            assert connection != null;
            userJDBCDaoImpl.commitAndClose(connection);
        }
        return users;
    }

    @Override
    public int changeUserStatus(Integer status, Integer id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int result = 0;
        try {
            connection = userJDBCDaoImpl.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_USER_STATUS);
            preparedStatement.setInt(1, status);
            preparedStatement.setInt(2, id);
            result = preparedStatement.executeUpdate();
            LOGGER.info("User status changed");
        } catch (SQLException e) {
            assert connection != null;
            userJDBCDaoImpl.rollbackAndClose(connection);
            LOGGER.error(e.getMessage());
        } finally {
            assert connection != null;
            userJDBCDaoImpl.commitAndClose(connection);
        }
        return result;
    }


}
