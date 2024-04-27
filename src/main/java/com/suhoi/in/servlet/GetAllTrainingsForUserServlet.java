package com.suhoi.in.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suhoi.dto.TrainingDto;
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
import java.util.List;

@WebServlet("/training/get-all-trainings")
public class GetAllTrainingsForUserServlet extends HttpServlet {

    private TrainingFacade trainingFacade;
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        trainingFacade = (TrainingFacade) getServletContext().getAttribute("trainingFacade");
        objectMapper = (ObjectMapper) getServletContext().getAttribute("objectMapper");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<TrainingDto> trainingsForUser = trainingFacade.getTrainingsForUser();
            String jsonResponse = objectMapper.writeValueAsString(trainingsForUser);

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(jsonResponse);
        } catch (DataNotFoundException e) {
            GlobalExceptionHandler.handleDataNotFoundException(resp, e);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(e.getMessage());
        }
    }
}
