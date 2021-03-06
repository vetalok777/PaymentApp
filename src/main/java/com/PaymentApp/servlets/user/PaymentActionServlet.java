package com.PaymentApp.servlets.user;

import com.PaymentApp.DAO.CardJDBCDaoImpl;
import com.PaymentApp.DAO.PaymentJDBCDaoImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

public class PaymentActionServlet extends HttpServlet {

    final Logger LOGGER = Logger.getLogger(PaymentActionServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("status").equals("submit")) {
            String newStatus = "successful";
            try {
                Integer receiver = (CardJDBCDaoImpl.getInstance().getCardId(req.getParameter("receiverCard")));
                PaymentJDBCDaoImpl.getInstance().updatePaymentStatus(newStatus, Integer.valueOf(req.getParameter("id")));
                CardJDBCDaoImpl.getInstance().
                        updateBalance(receiver, new BigDecimal(req.getParameter("amount")));
                resp.sendRedirect("PaymentDetails?currentPage=1&sorting=byStatus");
                LOGGER.info("Submitted payment");
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }
        if (req.getParameter("status").equals("cancel")) {
            String newStatus = "canceled";
            try {
                Integer sender = (CardJDBCDaoImpl.getInstance().getCardId(req.getParameter("senderCard")));
                PaymentJDBCDaoImpl.getInstance().updatePaymentStatus(newStatus, Integer.valueOf(req.getParameter("id")));
                CardJDBCDaoImpl.getInstance().
                        updateBalance(sender, new BigDecimal(req.getParameter("amount")));
                resp.sendRedirect("PaymentDetails?currentPage=1&sorting=byStatus");
                LOGGER.info("Canceled payment");
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }


}
