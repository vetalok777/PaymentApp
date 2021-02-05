package com.PaymentApp.filters;

import com.PaymentApp.DAO.UserJDBCDaoImpl;
import com.PaymentApp.entities.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class AuthorizationFilter implements Filter {
    UserJDBCDaoImpl userJDBCDaoImpl = UserJDBCDaoImpl.getInstance();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String login = servletRequest.getParameter("email");
        String password = servletRequest.getParameter("password");
        PrintWriter out = servletResponse.getWriter();
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        try {
            User user = userJDBCDaoImpl.findUser(login);
            if (user != null) {
                if (login.equals(user.getLogin())
                        && (password.equals(user.getPassword()))) {
                    request.setAttribute("currentUser", user);
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    out.print(" <style>\n" +
                            "   .colortext {\n" +
                            "     color: red; \n" +
                            "   }\n" +
                            "  </style>" +
                            "<p> <span class=\"colortext\">Invalid username or password!!! \n" +
                            " </span> \n" +
                            "  </p>");
                    String jsp = "/authorization.jsp";
                    RequestDispatcher dispatcher = servletRequest.getRequestDispatcher(jsp);
                    dispatcher.include(servletRequest, servletResponse);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {

    }
}
