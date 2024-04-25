package com.suhoi.in.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suhoi.dto.CreateTypeDto;
import com.suhoi.exception.DataAlreadyExistException;
import com.suhoi.facade.TypeOfTrainingFacade;
import com.suhoi.handler.GlobalExceptionHandler;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/type-of-training/create")
public class AddNewTypeOfTrainingServlet extends HttpServlet {
    private TypeOfTrainingFacade typeOfTrainingFacade;
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        typeOfTrainingFacade = (TypeOfTrainingFacade) getServletContext().getAttribute("typeOfTrainingFacade");
        objectMapper = (ObjectMapper) getServletContext().getAttribute("objectMapper");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CreateTypeDto createTypeDto = objectMapper.readValue(req.getReader(), CreateTypeDto.class);
        try {
            typeOfTrainingFacade.addNewTypeOfTraining(createTypeDto.getType());

            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("Training added success");
        } catch (DataAlreadyExistException e) {
            GlobalExceptionHandler.handleDataAlreadyExistException(resp, e);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(e.getMessage());
        }
    }
}
