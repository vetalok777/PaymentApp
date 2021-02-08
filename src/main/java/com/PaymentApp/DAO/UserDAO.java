package com.PaymentApp.DAO;

import com.PaymentApp.entities.User;

import java.sql.SQLException;

public interface UserDAO {

    int insertUser(User user) throws SQLException;
    String findUserPassword(String login) throws SQLException;
     User findUser(String login) throws SQLException;
}
