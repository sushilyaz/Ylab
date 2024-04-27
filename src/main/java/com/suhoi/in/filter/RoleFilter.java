package com.suhoi.in.filter;

import com.suhoi.model.Role;
import com.suhoi.model.User;
import com.suhoi.util.UserUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Фильтр, срабатывающий в последнюю очередь. По url, предоставленному ниже может обращаться только админ
 */
@WebFilter(filterName = "C", urlPatterns = "/type-of-training/create")
public class RoleFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (UserUtils.getCurrentUser() == null || !UserUtils.getCurrentUser().getRole().equals(Role.ADMIN)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Permission denied. Only administrators can do this");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
