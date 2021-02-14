package com.PaymentApp.servlets.user;

import com.PaymentApp.DAO.CardJDBCDaoImpl;
import com.PaymentApp.DAO.PaymentJDBCDaoImpl;
import com.PaymentApp.DAO.UserJDBCDaoImpl;
import com.PaymentApp.DTO.PaymentDTO;
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

public class PaymentsDetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        try {
            User user = UserJDBCDaoImpl.getInstance().findUser((String) session.getAttribute("username"));
            List<PaymentDTO> payments = PaymentJDBCDaoImpl.getInstance().findAllPayments(user);
            req.setAttribute("payments", payments);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/user-view/user-payments-details.jsp");
            dispatcher.forward(req, resp);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
