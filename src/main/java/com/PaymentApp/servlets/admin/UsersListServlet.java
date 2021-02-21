package com.PaymentApp.servlets.admin;

import com.PaymentApp.DAO.UnblockRequestDAOImpl;
import com.PaymentApp.DAO.UserJDBCDaoImpl;
import com.PaymentApp.entities.UnblockRequest;
import com.PaymentApp.entities.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UsersListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users;
        try {
            users = UserJDBCDaoImpl.getInstance().findAllUsers();
            req.setAttribute("users", users);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String html = "/WEB-INF/admin-view/users-list.jsp";
        RequestDispatcher dispatcher = req.getRequestDispatcher(html);
        dispatcher.forward(req, resp);
    }
}

