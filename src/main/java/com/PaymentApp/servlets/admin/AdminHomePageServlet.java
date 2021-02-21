package com.PaymentApp.servlets.admin;

import com.PaymentApp.DAO.UnblockRequestDAOImpl;
import com.PaymentApp.entities.UnblockRequest;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AdminHomePageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UnblockRequest> requests;
        try {
            requests = UnblockRequestDAOImpl.getInstance().findAllRequests();
            req.setAttribute("requests", requests);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String html = "/WEB-INF/admin-view/admin-home.jsp";
        RequestDispatcher dispatcher = req.getRequestDispatcher(html);
        dispatcher.forward(req, resp);
    }
}
