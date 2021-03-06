package com.PaymentApp.servlets.user;

import com.PaymentApp.DAO.CardJDBCDaoImpl;
import com.PaymentApp.DAO.UserJDBCDaoImpl;
import com.PaymentApp.entities.Card;
import com.PaymentApp.entities.User;
import org.apache.log4j.Logger;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserHomePageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final Logger LOGGER = Logger.getLogger(UserHomePageServlet.class);
        HttpSession session = req.getSession();
        if (session.getAttribute("username") == null || session.getAttribute("username").equals("")) {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/authorization.jsp");
            dispatcher.forward(req, resp);
        } else {
            try {
                List<Card> cards = new ArrayList<>();
                User user = UserJDBCDaoImpl.getInstance().findUser((String) session.getAttribute("username"));
                if (req.getParameter("sorting") == null) {
                    String sort = "ORDER BY card_status DESC;";
                    cards = CardJDBCDaoImpl.getInstance().findAllCards(user, sort);
                    LOGGER.info("Sorting user cards by status");
                } else if (req.getParameter("sorting").equals("byNumber")) {
                    String sort = "ORDER BY card_number DESC;";
                    cards = CardJDBCDaoImpl.getInstance().findAllCards(user, sort);
                    LOGGER.info("Sorting user cards by number");
                } else if (req.getParameter("sorting").equals("byBalance")) {
                    String sort = "ORDER BY balance DESC;";
                    cards = CardJDBCDaoImpl.getInstance().findAllCards(user, sort);
                    LOGGER.info("Sorting user cards by balance");
                } else if (req.getParameter("sorting").equals("byName")) {
                    String sort = "ORDER BY card_name DESC;";
                    cards = CardJDBCDaoImpl.getInstance().findAllCards(user, sort);
                    LOGGER.info("Sorting user cards by name");
                }
                req.setAttribute("cards", cards);
                req.setAttribute("login", user.getFirstName());
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/user-view/user-home.jsp");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
