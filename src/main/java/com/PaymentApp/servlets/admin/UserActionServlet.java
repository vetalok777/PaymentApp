package com.PaymentApp.servlets.admin;

import com.PaymentApp.DAO.UserJDBCDaoImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class UserActionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer userId = null;
        Integer status = null;
        if (req.getParameter("action").equals("block")) {
            userId = Integer.valueOf(req.getParameter("userId"));
            status = 0;
        } else if (req.getParameter("action").equals("unblock")) {
            userId = Integer.valueOf(req.getParameter("userId"));
            status = 1;
        }
        try {
            UserJDBCDaoImpl.getInstance().changeUserStatus(status, userId);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/Users");
            dispatcher.forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
