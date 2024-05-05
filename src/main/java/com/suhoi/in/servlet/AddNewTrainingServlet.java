package com.suhoi.in.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suhoi.dto.CreateTrainingDto;
import com.suhoi.exception.DataAlreadyExistException;
import com.suhoi.exception.DataNotFoundException;
import com.suhoi.facade.TrainingFacade;
import com.suhoi.handler.GlobalExceptionHandler;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/training/create")
public class AddNewTrainingServlet extends HttpServlet {
    private TrainingFacade trainingFacade;
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        trainingFacade = (TrainingFacade) getServletContext().getAttribute("trainingFacade");
        objectMapper = (ObjectMapper) getServletContext().getAttribute("objectMapper");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CreateTrainingDto createTrainingDto = objectMapper.readValue(req.getReader(), CreateTrainingDto.class);
        try {
            trainingFacade.addNewTraining(createTrainingDto);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("Training added success");
        } catch (DataNotFoundException e) {
            GlobalExceptionHandler.handleDataNotFoundException(resp, e);
        } catch (DataAlreadyExistException e) {
            GlobalExceptionHandler.handleDataAlreadyExistException(resp, e);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(e.getMessage());
        }
    }
}
