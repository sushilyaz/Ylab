package com.suhoi.handler;

import com.suhoi.exception.DataAlreadyExistException;
import com.suhoi.exception.DataNotFoundException;
import com.suhoi.exception.UserActionException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class GlobalExceptionHandler {
    public static void handleUserActionException(HttpServletResponse response, UserActionException ex) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_CONFLICT);
        response.getWriter().write(ex.getMessage());
    }

    public static void handleDataNotFoundException(HttpServletResponse response, DataNotFoundException ex) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.getWriter().write(ex.getMessage());
    }

    public static void handleDataAlreadyExistException(HttpServletResponse response, DataAlreadyExistException ex) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_CONFLICT);
        response.getWriter().write(ex.getMessage());
    }
}
