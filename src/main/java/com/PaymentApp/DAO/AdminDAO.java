package com.PaymentApp.DAO;

import com.PaymentApp.entities.Admin;
import com.PaymentApp.entities.User;

import java.sql.SQLException;

public interface AdminDAO {
    Admin findAdmin(String login) throws SQLException;
}
