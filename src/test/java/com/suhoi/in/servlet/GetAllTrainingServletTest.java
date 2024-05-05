package com.suhoi.in.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suhoi.dto.AuthDto;
import com.suhoi.dto.TrainingDto;
import com.suhoi.facade.TrainingFacade;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class GetAllTrainingServletTest {
    @Mock
    private TrainingFacade trainingFacade;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private ServletConfig servletConfig;

    @Mock
    private ServletContext servletContext;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private GetAllTrainingsForUserServlet servlet;

    private PrintWriter writer;

    @BeforeEach
    public void setUp() throws ServletException, IOException {
        MockitoAnnotations.initMocks(this);

        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("trainingFacade")).thenReturn(trainingFacade);
        when(servletContext.getAttribute("objectMapper")).thenReturn(objectMapper);

        writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        servlet = new GetAllTrainingsForUserServlet();
        servlet.init(servletConfig);
    }

    @Test
    @DisplayName("Get all trainings for user servlet test")
    public void testDoGet() throws ServletException, IOException {
        List<TrainingDto> expectedTrainings = Arrays.asList(
                new TrainingDto(),
                new TrainingDto()
        );
        when(trainingFacade.getTrainingsForUser()).thenReturn(expectedTrainings);
        when(objectMapper.writeValueAsString(expectedTrainings)).thenReturn("test");
        servlet.doGet(request, response);

        verify(trainingFacade).getTrainingsForUser();
        verify(objectMapper).writeValueAsString(expectedTrainings);
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(writer).write(anyString());
    }
}
