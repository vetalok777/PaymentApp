package com.PaymentApp.DAO;

import com.PaymentApp.entities.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {

    int insertUser(User user) throws SQLException;

    String findUserPassword(String login) throws SQLException;

    User findUser(String login) throws SQLException;

    List<User> findAllUsers() throws SQLException;

    int changeUserStatus(Integer status, Integer id) throws SQLException;
}
