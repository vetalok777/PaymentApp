package com.PaymentApp.servlets.user;


import com.PaymentApp.DAO.PaymentJDBCDaoImpl;
import com.PaymentApp.DAO.UserJDBCDaoImpl;
import com.PaymentApp.DTO.PaymentDTO;

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

import java.util.List;

public class PaymentsDetailsServlet extends HttpServlet {
    final Logger LOGGER = Logger.getLogger(PaymentsDetailsServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        HttpSession session = req.getSession();
        String sPageId = req.getParameter("currentPage");
        String sorting = req.getParameter("sorting");
        int currentPage = Integer.parseInt(sPageId);
        int total = 5;
        List<PaymentDTO> payments;
        try {
            User user = UserJDBCDaoImpl.getInstance().findUser((String) session.getAttribute("username"));
            if (sorting.equals("byDateNew")) {
                String byDateNew = "ORDER BY creation_date DESC";
                payments = PaymentJDBCDaoImpl.getInstance().getPaymentsRecords(user, currentPage, total, byDateNew);
                req.setAttribute("payments", payments);
                LOGGER.info("Sorting payments by date new");
            }
            if (sorting.equals("byDateOld")) {
                String byDateOld = "ORDER BY creation_date ASC";
                payments = PaymentJDBCDaoImpl.getInstance().getPaymentsRecords(user, currentPage, total, byDateOld);
                req.setAttribute("payments", payments);
                LOGGER.info("Sorting payments by date old");
            }

            if (sorting.equals("byStatus")) {
                String byDateOld = "ORDER BY payment_status ASC";
                payments = PaymentJDBCDaoImpl.getInstance().getPaymentsRecords(user, currentPage, total, byDateOld);
                req.setAttribute("payments", payments);
                LOGGER.info("Sorting payments by status");
            }

            if (sorting.equals("byNumber")) {
                String byDateOld = "ORDER BY sender_id DESC";
                payments = PaymentJDBCDaoImpl.getInstance().getPaymentsRecords(user, currentPage, total, byDateOld);
                req.setAttribute("payments", payments);
                LOGGER.info("Sorting payments by number");
            }

            int rows = PaymentJDBCDaoImpl.getInstance().getNumberOfRows(user.getId());
            int nOfPages = rows / total;

            if (nOfPages % total > 0) {

                nOfPages++;
            }
            req.setAttribute("noOfPages", nOfPages);
            req.setAttribute("currentPage", currentPage);
            req.setAttribute("recordsPerPage", total);
            req.setAttribute("sorting", sorting);

            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/user-view/user-payments-details.jsp");
            dispatcher.forward(req, resp);

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
