package com.PaymentApp.filters;

import com.PaymentApp.DAO.AdminJDBCDaoImpl;
import com.PaymentApp.DAO.DBManager;
import com.PaymentApp.entities.Admin;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class CheckAdminFilter implements Filter {

    AdminJDBCDaoImpl adminJDBCDaoImpl = AdminJDBCDaoImpl.getInstance();


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setContentType("text/html");
        String login = request.getParameter("email");
        String password = request.getParameter("password");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        try {
            Admin admin = adminJDBCDaoImpl.findAdmin(login);

            if (login.equals(admin.getLogin())) {
                if (password.equals(admin.getPassword())) {
                    session.setAttribute("username", login);
                    String html = "/WEB-INF/admin-view/admin-home.jsp";
                    RequestDispatcher dispatcher = request.getRequestDispatcher(html);
                    dispatcher.forward(request, response);
                } else {
                    out.print("Invalid password for ADMIN!!!");
                    String jsp = "authorization.jsp";
                    RequestDispatcher dispatcher = request.getRequestDispatcher(jsp);
                    dispatcher.include(request, response);
                }
            } else {
                session.setAttribute("username", login);
                request.setAttribute("email", login);
                request.setAttribute("password", password);
                filterChain.doFilter(servletRequest, servletResponse);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void destroy() {

    }
}
