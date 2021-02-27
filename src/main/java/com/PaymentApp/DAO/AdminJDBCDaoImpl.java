package com.PaymentApp.DAO;

import com.PaymentApp.entities.Admin;
import com.PaymentApp.entities.User;
import com.PaymentApp.servlets.user.UserHomePageServlet;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;

public class AdminJDBCDaoImpl implements AdminDAO {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String FIND_ADMIN = "SELECT *  FROM admin WHERE login = (?);";

    private static AdminJDBCDaoImpl adminJDBCDaoImpl;
    final Logger LOGGER = Logger.getLogger(AdminJDBCDaoImpl.class);
    public AdminJDBCDaoImpl() {
        try {
            Class.forName(DRIVER);
            LOGGER.info("Successfully loaded DRIVER");
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static synchronized AdminJDBCDaoImpl getInstance() {
        if (adminJDBCDaoImpl == null) {
            adminJDBCDaoImpl = new AdminJDBCDaoImpl();
        }
        return adminJDBCDaoImpl;
    }

    public Connection getConnection() throws SQLException {
        Connection con = null;
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/PaymentDB");
            con = ds.getConnection();
            LOGGER.info("Connection is successful");
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
        }
        return con;
    }


    public void commitAndClose(Connection con) {
        try {
            con.commit();
            con.close();
            LOGGER.info("Connection commit and close successfully");
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
    public Admin findAdmin(String login) throws SQLException {
        Admin admin = new Admin();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = AdminJDBCDaoImpl.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(FIND_ADMIN);
            preparedStatement.setString(1, login);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                admin.setLogin(rs.getString("login"));
                admin.setPassword(rs.getString("password"));
                admin.setId(rs.getInt("id"));
            }
            LOGGER.info("Admin was found");
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            AdminJDBCDaoImpl.getInstance().rollbackAndClose(connection);
        } finally {
            assert connection != null;
            AdminJDBCDaoImpl.getInstance().commitAndClose(connection);
        }
        return admin;
    }

}
