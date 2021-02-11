package com.PaymentApp.servlets.user;

import com.PaymentApp.DAO.CardJDBCDaoImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

public class CardReplenishmentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.setAttribute("id", req.getParameter("id"));
        session.setAttribute("status", req.getParameter("status"));
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/user-view/user-replenishment.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        HttpSession session = req.getSession();
        if (session.getAttribute("username") == null) {
            resp.sendRedirect("authorization.jsp");
        } else {
            Integer id = Integer.parseInt((String) session.getAttribute("id"));
            Integer status = Integer.parseInt((String) session.getAttribute("status"));
            BigDecimal sum = new BigDecimal(req.getParameter("sum"));
            if (status >= 1) {
                try {
                    CardJDBCDaoImpl.getInstance().updateBalance(id, sum);
                    RequestDispatcher requestDispatcher = req.getRequestDispatcher("/HomePage");
                    requestDispatcher.forward(req, resp);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/errors-pages/ReplenishmentError.jsp");
                requestDispatcher.forward(req, resp);
            }
        }
    }
}
