package com.PaymentApp.servlets.user;

import com.PaymentApp.DAO.CardJDBCDaoImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class CardBlockServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("action").equals("submit")) {
            HttpSession session = req.getSession();
            session.setAttribute("id", req.getParameter("id"));
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/user-view/block-submit.jsp");
            dispatcher.forward(req, resp);
        }

        if (req.getParameter("action").equals("block")) {
            HttpSession session = req.getSession();
            int cardId = Integer.parseInt((String) session.getAttribute("id"));
            try {
                CardJDBCDaoImpl.getInstance().blockCardById(cardId);
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/user-view/block-successful.jsp");
                dispatcher.forward(req, resp);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if(req.getParameter("action").equals("unblock")){

            }
        }
    }
}
