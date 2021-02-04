package com.PaymentApp.servlets;

import com.PaymentApp.DAO.UserJDBCDaoImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class AuthorizationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    UserJDBCDaoImpl userJDBCDaoImpl = UserJDBCDaoImpl.getInstance();

    public AuthorizationServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jsp = "/authorization.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(jsp);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("email");
        String password = request.getParameter("password");
        String firstname = request.getParameter("firstname");
        PrintWriter out = response.getWriter();
        try {
            if (login.equals(userJDBCDaoImpl.findByLogin(login))) {
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
                        " doesn't exists!!! </span> \n" +
                        "  </p>");
                String jsp = "/authorization.jsp";
                RequestDispatcher dispatcher = request.getRequestDispatcher(jsp);
                dispatcher.include(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
