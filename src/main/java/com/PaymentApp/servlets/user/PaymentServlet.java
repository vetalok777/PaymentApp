package com.PaymentApp.servlets.user;

import com.PaymentApp.DAO.CardJDBCDaoImpl;
import com.PaymentApp.DAO.PaymentJDBCDaoImpl;
import com.PaymentApp.entities.Card;
import com.PaymentApp.entities.Payment;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class PaymentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/user-view/user-payment.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (isValidSum(req.getParameter("amount")) && !req.getParameter("receiver").equals("")) {
            BigDecimal amount = new BigDecimal(req.getParameter("amount"));
            String number = req.getParameter("number");
            String receiver = req.getParameter("receiver");
            Card cardSender = new Card(number);
            Card cardReceiver = new Card(receiver);
            try {

                if (CardJDBCDaoImpl.getInstance().getCardStatus(cardSender) < 1) {
                    RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/errors-pages/ReplenishmentError.jsp");
                    requestDispatcher.forward(req, resp);
                }
                if (!CardJDBCDaoImpl.getInstance().findCard(receiver)) {
                    RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/errors-pages/noCard-error.jsp");
                    requestDispatcher.forward(req, resp);
                } else {
                    BigDecimal balance = CardJDBCDaoImpl.getInstance().getCardBalance(cardSender);

                    if (amount.compareTo(balance) <= 0) {
                        if (CardJDBCDaoImpl.getInstance().getCardStatus(cardReceiver) >= 1) {
                            if ((number.equals(receiver))) {
                                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/errors-pages/sameCard-error.jsp");
                                dispatcher.forward(req, resp);
                            } else {
                                Integer senderId = CardJDBCDaoImpl.getInstance().getCardId(number);
                                Integer receiverId = CardJDBCDaoImpl.getInstance().getCardId(receiver);
                                CardJDBCDaoImpl.getInstance().decreaseBalance(senderId, amount);
                                Payment payment = new Payment(amount, LocalDateTime.now(), senderId, receiverId);
                                PaymentJDBCDaoImpl.getInstance().insertPayment(payment);
                                resp.sendRedirect(req.getContextPath() + "/PaymentDetails?currentPage=1&sorting=byStatus");
                            }
                        } else {
                            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/errors-pages/ReplenishmentError2.jsp");
                            dispatcher.forward(req, resp);
                        }
                    } else {
                        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/errors-pages/not-enough.jsp");
                        dispatcher.forward(req, resp);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/user-view/user-payment.jsp");
            dispatcher.forward(req, resp);
        }
    }

    public static boolean isValidSum(String sum) {
        String ePattern = "[0-9]+";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(sum);
        return m.matches();
    }

}
