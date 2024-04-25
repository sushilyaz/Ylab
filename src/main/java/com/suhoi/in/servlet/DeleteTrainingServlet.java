package com.suhoi.in.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suhoi.dto.UpdateTrainingDto;
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

@WebServlet("/training/delete")
public class DeleteTrainingServlet extends HttpServlet {
    private TrainingFacade trainingFacade;
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        trainingFacade = (TrainingFacade) getServletContext().getAttribute("trainingFacade");
        objectMapper = (ObjectMapper) getServletContext().getAttribute("objectMapper");
    }

    /*
    Так как метод doDelete в HttpServlet не определен, поступлю так:
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getMethod().equals("DELETE")) {
            doDelete(req, resp);
        } else {
            super.service(req, resp);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = objectMapper.readValue(req.getReader(), Long.class);
        try {
            trainingFacade.delete(id);

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("Training update success");
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
