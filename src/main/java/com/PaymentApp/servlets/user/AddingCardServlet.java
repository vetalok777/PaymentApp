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
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;

public class AddingCardServlet extends HttpServlet {

    final Logger LOGGER = Logger.getLogger(AddingCardServlet.class);
    public static String CARD_NAME_ALREADY_EXISTS = (" <style>\n" +
            "   .colortext {\n" +
            "     color: red; \n" +
            "   }\n" +
            "  </style>" +
            "<p> <span class=\"colortext\">Card with this name \n" +
            " is already exists!!! </span> \n" +
            "  </p>");

    public static String CARD_NAME_INVALID = (" <style>\n" +
            "   .colortext {\n" +
            "     color: red; \n" +
            "   }\n" +
            "  </style>" +
            "<p> <span class=\"colortext\">Card name must be \n" +
            " only letters!!! </span> \n" +
            "  </p>");


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/user-view/add-card.jsp");
        dispatcher.forward(req, resp);
        LOGGER.info("forwarding to add-card.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cardName = req.getParameter("name");
        HttpSession session = req.getSession();
        PrintWriter out = resp.getWriter();
        try {
            User user = UserJDBCDaoImpl.getInstance().findUser((String) session.getAttribute("username"));
            if (cardName != null && isValidCardName(cardName)) {
                if (CardJDBCDaoImpl.getInstance().findCardByName(cardName, user)) {
                    out.println(CARD_NAME_ALREADY_EXISTS);
                    RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/user-view/add-card.jsp");
                    dispatcher.include(req, resp);
                } else {
                    Card card = new Card(cardName, CardJDBCDaoImpl.randomCardNumber(), new BigDecimal(0), user.getId());
                    CardJDBCDaoImpl.getInstance().insertCard(card);
                    req.setAttribute("number", card.getNumber());
                    RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/user-view/add-successful.jsp");
                    dispatcher.forward(req, resp);
                    LOGGER.info("forwarding to add-successful.jsp");
                }
            } else {
                out.println(CARD_NAME_INVALID);
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/user-view/add-card.jsp");
                dispatcher.include(req, resp);
                LOGGER.info("Card name invalid");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

    }


    public static boolean isValidCardName(String cardName) {
        String ePattern = "^[a-zA-Z]{1,20}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(cardName);
        return m.matches();
    }
}
