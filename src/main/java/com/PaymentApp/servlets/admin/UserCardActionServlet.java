package com.PaymentApp.servlets.admin;

import com.PaymentApp.DAO.CardJDBCDaoImpl;
import com.PaymentApp.servlets.LogOutServlet;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class UserCardActionServlet extends HttpServlet {
    final Logger LOGGER = Logger.getLogger(UserActionServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("action").equals("block")) {
            try {
                CardJDBCDaoImpl.getInstance().blockCardById(Integer.valueOf(req.getParameter("cardId")));
            LOGGER.info("Blocked user card");
            } catch (SQLException e) {
               LOGGER.error(e.getMessage());
            }
        }
        String login = (String) req.getAttribute("login");
        req.setAttribute("login", login);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/UserCards");
        dispatcher.forward(req, resp);
    }


}
