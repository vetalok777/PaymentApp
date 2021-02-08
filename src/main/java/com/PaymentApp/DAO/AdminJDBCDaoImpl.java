package com.PaymentApp.DAO;

import com.PaymentApp.entities.Admin;
import com.PaymentApp.entities.User;

import java.sql.*;

public class AdminJDBCDaoImpl implements AdminDAO {
    private static final String URL = "jdbc:mysql://localhost/payment_system_db?serverTimezone=UTC";
    private static final String PASSWORD = "root";
    private static final String USERNAME = "root";
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
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }


    @Override
    public Admin findAdmin(String login) throws SQLException {
        Admin admin = new Admin();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = adminJDBCDaoImpl.getConnection();
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
        return admin;
    }


    public static void main(String[] args) throws SQLException {
        Admin admin = getInstance().findAdmin("vetalok777@gmail.com");
        System.out.println(admin.getLogin());
    }
}
