package com.suhoi.in.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suhoi.dto.AuthDto;
import com.suhoi.dto.CreateUserDto;
import com.suhoi.exception.UserActionException;
import com.suhoi.facade.UserFacade;
import com.suhoi.handler.GlobalExceptionHandler;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class AuthServlet extends HttpServlet {
    private UserFacade userFacade;
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userFacade = (UserFacade) getServletContext().getAttribute("userFacade");
        objectMapper = (ObjectMapper) getServletContext().getAttribute("objectMapper");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthDto authDto = objectMapper.readValue(req.getReader(), AuthDto.class);
        try {
            userFacade.auth(authDto);
            req.getSession().setAttribute("user", authDto);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("User auth successful! ");
        } catch (UserActionException e) {
            GlobalExceptionHandler.handleUserActionException(resp, e);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(e.getMessage());
        }
    }
}
