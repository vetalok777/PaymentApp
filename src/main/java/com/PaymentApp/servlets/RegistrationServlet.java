package com.PaymentApp.servlets;

import com.PaymentApp.DAO.CardJDBCDaoImpl;
import com.PaymentApp.DAO.UserJDBCDaoImpl;
import com.PaymentApp.entities.Card;
import com.PaymentApp.entities.User;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;


public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    UserJDBCDaoImpl userJDBCDaoImpl = UserJDBCDaoImpl.getInstance();
    CardJDBCDaoImpl cardJDBCDaoImpl = CardJDBCDaoImpl.getInstance();
    public static String INVALID_EMAIL_OR_PASSWORD = (" <style>\n" +
            "   .colortext {\n" +
            "     color: red; \n" +
            "   }\n" +
            "  </style>" +
            "<p> <span class=\"colortext\">Invalid email , password or firstName!" +
            "Password should contains : 8-15 characters, at least " +
            "one uppercase letter and one digit! </span> \n" +
            "  </p>");
    public static String EMAIL_ALREADY_EXISTS = (" <style>\n" +
            "   .colortext {\n" +
            "     color: red; \n" +
            "   }\n" +
            "  </style>" +
            "<p> <span class=\"colortext\">User with this email \n" +
            " is already exists!!! </span> \n" +
            "  </p>");


    public RegistrationServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jsp = "/user-registration.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(jsp);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("email");
        String password = request.getParameter("password");
        String firstname = request.getParameter("firstname");
        User user = new User(login, password, firstname);
        int result;
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try {
            if (isValidEmailAddress(login) && isValidPassword(password) && isValidFirstName(firstname)) {
                result = userJDBCDaoImpl.insertUser(user);
                if (result > 0) {
                    User user1 = userJDBCDaoImpl.findUser(login);
                    Card card = new Card("FirstCard", CardJDBCDaoImpl.randomCardNumber(), new BigDecimal(20), user1.getId());
                    cardJDBCDaoImpl.insertCard(card);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/user-view/user-details.jsp");
                    dispatcher.forward(request, response);
                } else {
                    out.print(EMAIL_ALREADY_EXISTS);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/user-registration.jsp");
                    dispatcher.include(request, response);
                }
            } else {
                out.print(INVALID_EMAIL_OR_PASSWORD);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/user-registration.jsp");
                dispatcher.include(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean isValidPassword(String email) {
        String ePattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,15}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    public static boolean isValidFirstName(String firstName) {
        String ePattern = "/^[a-z ,.'-]+$/i";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(firstName);
        return m.matches();
    }



}

