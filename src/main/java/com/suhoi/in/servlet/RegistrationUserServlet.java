package com.suhoi.in.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suhoi.dto.AuthDto;
import com.suhoi.dto.CreateUserDto;
import com.suhoi.exception.NoValidDataException;
import com.suhoi.exception.UserActionException;
import com.suhoi.facade.UserFacade;
import com.suhoi.handler.GlobalExceptionHandler;
import com.suhoi.model.User;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/registration")
public class RegistrationUserServlet extends HttpServlet {

    private UserFacade userFacade;
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userFacade = (UserFacade) getServletContext().getAttribute("userFacade");
        objectMapper = (ObjectMapper) getServletContext().getAttribute("objectMapper");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CreateUserDto createUserDto = objectMapper.readValue(req.getReader(), CreateUserDto.class);
        try {
            userFacade.signUp(createUserDto);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("User register success! ");
        } catch (NoValidDataException e) {
            GlobalExceptionHandler.handleNoValidDataException(resp, e);
        } catch (UserActionException e) {
            GlobalExceptionHandler.handleUserActionException(resp, e);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(e.getMessage());
        }
    }
}
