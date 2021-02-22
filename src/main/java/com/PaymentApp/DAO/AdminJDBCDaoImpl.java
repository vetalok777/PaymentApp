package com.PaymentApp.DAO;

import com.PaymentApp.entities.Admin;
import com.PaymentApp.entities.User;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;

public class AdminJDBCDaoImpl implements AdminDAO {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String FIND_ADMIN = "SELECT *  FROM admin WHERE login = (?);";

    private static AdminJDBCDaoImpl adminJDBCDaoImpl;

    public AdminJDBCDaoImpl() {
        try {
            Class.forName(DRIVER);
            System.out.println("Successfully!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
        return con;
    }


    public void commitAndClose(Connection con) {
        try {
            con.commit();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void rollbackAndClose(Connection con) {
        try {
            con.rollback();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
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
        } catch (SQLException e) {
            e.printStackTrace();
            AdminJDBCDaoImpl.getInstance().rollbackAndClose(connection);
        } finally {
            assert connection != null;
            AdminJDBCDaoImpl.getInstance().commitAndClose(connection);
        }
        return admin;
    }

}
