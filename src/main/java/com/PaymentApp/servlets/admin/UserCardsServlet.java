package com.PaymentApp.servlets.admin;

import com.PaymentApp.DAO.CardJDBCDaoImpl;
import com.PaymentApp.DAO.UnblockRequestDAOImpl;
import com.PaymentApp.DAO.UserJDBCDaoImpl;
import com.PaymentApp.entities.Card;
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

public class UserCardsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Card> cards;
        try {
            String login = req.getParameter("login");
            User user = UserJDBCDaoImpl.getInstance().findUser(login);
            cards = CardJDBCDaoImpl.getInstance().findAllCards(user, "ORDER BY card_status");
            req.setAttribute("cards", cards);
            req.setAttribute("login", login);
            String jsp = "/WEB-INF/admin-view/user-cards.jsp";
            RequestDispatcher dispatcher = req.getRequestDispatcher(jsp);
            dispatcher.forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

