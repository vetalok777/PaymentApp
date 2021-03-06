package com.PaymentApp.filters;

import com.PaymentApp.DAO.UserJDBCDaoImpl;
import com.PaymentApp.entities.User;
import com.PaymentApp.servlets.user.UserHomePageServlet;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class AuthorizationFilter implements Filter {
    UserJDBCDaoImpl userJDBCDaoImpl = UserJDBCDaoImpl.getInstance();
    final Logger LOGGER = Logger.getLogger(AuthorizationFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String login = (String) request.getAttribute("email");
        String password = (String) request.getAttribute("password");
        PrintWriter out = servletResponse.getWriter();
        LOGGER.info("Authorization filter working");
        try {
            User user = userJDBCDaoImpl.findUser(login);
            if (user != null) {
                if (user.getStatus() < 1) {
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/errors-pages/user-blocked-error.jsp");
                    dispatcher.forward(servletRequest, servletResponse);
                }
                if (login.equals(user.getLogin())
                        && (password.equals(user.getPassword()))) {
                    HttpSession session = request.getSession();
                    session.setAttribute("username", login);
                    request.setAttribute("name", user.getFirstName());

                    response.sendRedirect(request.getContextPath() + "/HomePage");

                } else {
                    out.print(" <style>\n" +
                            "   .colortext {\n" +
                            "     color: red; \n" +
                            "   }\n" +
                            "  </style>" +
                            "<p> <span class=\"colortext\">Invalid username or password!!! \n" +
                            " </span> \n" +
                            "  </p>");
                    RequestDispatcher dispatcher = servletRequest.getRequestDispatcher("/authorization.jsp");
                    dispatcher.include(servletRequest, servletResponse);
                }
            } else {
                out.print(" <style>\n" +
                        "   .colortext {\n" +
                        "     color: red; \n" +
                        "   }\n" +
                        "  </style>" +
                        "<p> <span class=\"colortext\">Invalid username or password!!! \n" +
                        " </span> \n" +
                        "  </p>");
                RequestDispatcher dispatcher = servletRequest.getRequestDispatcher("/authorization.jsp");
                dispatcher.include(servletRequest, servletResponse);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void destroy() {

    }
}
