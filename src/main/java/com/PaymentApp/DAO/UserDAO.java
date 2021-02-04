package com.PaymentApp.DAO;

import com.PaymentApp.entities.User;

import java.sql.SQLException;

public interface UserDAO {
    int insertUser(User user) throws SQLException;

}
