package com.PaymentApp.filters;

import com.PaymentApp.entities.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RoleFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        User user = (User) request.getAttribute("currentUser");
        if (user.getRole().equals("user")) {
            String html = "/user-home.html";
            RequestDispatcher dispatcher = servletRequest.getRequestDispatcher(html);
            dispatcher.forward(servletRequest, servletResponse);
        }
        if (user.getRole().equals("admin")) {
            String html = "/admin-home.html";
            RequestDispatcher dispatcher = servletRequest.getRequestDispatcher(html);
            dispatcher.forward(servletRequest, servletResponse);

        }
    }

    @Override
    public void destroy() {

    }
}
