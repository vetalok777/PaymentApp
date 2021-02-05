package com.PaymentApp.servlets;

import com.PaymentApp.DAO.UserJDBCDaoImpl;
import com.PaymentApp.entities.User;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;


public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    UserJDBCDaoImpl userJDBCDaoImpl = UserJDBCDaoImpl.getInstance();


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
        int result = 0;
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try {
            result = userJDBCDaoImpl.insertUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (isValidEmailAddress(login)) {
            if (result > 0) {
                String jsp = "/user-details.jsp";
                RequestDispatcher dispatcher = request.getRequestDispatcher(jsp);
                dispatcher.forward(request, response);
            } else {
                out.print(" <style>\n" +
                        "   .colortext {\n" +
                        "     color: red; \n" +
                        "   }\n" +
                        "  </style>" +
                        "<p> <span class=\"colortext\">User with this email \n" +
                        " is already exists!!! </span> \n" +
                        "  </p>");
                String jsp = "/user-registration.jsp";
                RequestDispatcher dispatcher = request.getRequestDispatcher(jsp);
                dispatcher.include(request, response);
            }
        }
        else {
            out.print(" <style>\n" +
                    "   .colortext {\n" +
                    "     color: red; \n" +
                    "   }\n" +
                    "  </style>" +
                    "<p> <span class=\"colortext\">Invalid email!!! </span> \n" +
                    "  </p>");
            String jsp = "/user-registration.jsp";
            RequestDispatcher dispatcher = request.getRequestDispatcher(jsp);
            dispatcher.include(request, response);
        }
    }

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static void main(String[] args) {
        String email = "vetalok@gmail.com";
        System.out.println(isValidEmailAddress(email));
    }
}

