package com.suhoi.in.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

/**
 * Фильтр, устанавливающий заголовки
 * Он срабатывает первый (если рассматривать со стороны request-response)
 * filterName = "A" - т.к. единственный способ задать порядок срабатывания сервлетов без использования xml конфига
 */
@WebFilter(filterName = "A")
public class HeaderFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("application/json; charset=UTF-8");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
