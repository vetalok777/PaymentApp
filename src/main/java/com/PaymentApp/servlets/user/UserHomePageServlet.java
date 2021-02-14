package com.PaymentApp.servlets.user;

import com.PaymentApp.DAO.CardJDBCDaoImpl;
import com.PaymentApp.DAO.UserJDBCDaoImpl;
import com.PaymentApp.entities.Card;
import com.PaymentApp.entities.User;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserHomePageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        HttpSession session = req.getSession();
        if (session.getAttribute("username") == null) {
            resp.sendRedirect("/PaymentApp/authorization.jsp");
        }
        try {
            User user = UserJDBCDaoImpl.getInstance().findUser((String) session.getAttribute("username"));
            List<Card> cards = CardJDBCDaoImpl.getInstance().findAllCards(user);
            req.setAttribute("cards", cards);
            req.setAttribute("login", user.getFirstName());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/user-view/user-home.jsp");
        dispatcher.forward(req, resp);
    }
}
