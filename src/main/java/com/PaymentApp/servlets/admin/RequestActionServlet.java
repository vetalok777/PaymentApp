package com.PaymentApp.servlets.admin;

import com.PaymentApp.DAO.CardJDBCDaoImpl;
import com.PaymentApp.DAO.UnblockRequestDAOImpl;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class RequestActionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        if (req.getParameter("action").equals("considered")) {
            Integer cardId = Integer.valueOf(req.getParameter("cardId"));
            Integer requestId = Integer.valueOf(req.getParameter("Id"));
            try {
                CardJDBCDaoImpl.getInstance().changeCardStatus(cardId);
                UnblockRequestDAOImpl.getInstance().changeRequestStatus(requestId, "confirmed");
                RequestDispatcher dispatcher = req.getRequestDispatcher("/AdminHome");
                dispatcher.forward(req, resp);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (req.getParameter("action").equals("cancel")) {
            Integer requestId = Integer.valueOf(req.getParameter("Id"));
            try {
                UnblockRequestDAOImpl.getInstance().changeRequestStatus(requestId, "rejected");
                RequestDispatcher dispatcher = req.getRequestDispatcher("/AdminHome");
                dispatcher.forward(req, resp);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}

