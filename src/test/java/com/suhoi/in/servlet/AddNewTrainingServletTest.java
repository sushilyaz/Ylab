package com.suhoi.in.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suhoi.dto.CreateTrainingDto;
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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.time.Duration;
import java.util.HashMap;

import static org.mockito.Mockito.*;

public class AddNewTrainingServletTest {
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

    private AddNewTrainingServlet servlet;

    @BeforeEach
    public void setUp() throws ServletException {
        MockitoAnnotations.initMocks(this);

        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("trainingFacade")).thenReturn(trainingFacade);
        when(servletContext.getAttribute("objectMapper")).thenReturn(objectMapper);

        servlet = new AddNewTrainingServlet();
        servlet.init(servletConfig);
    }

    @Test
    @DisplayName("Test add new training servlet")
    public void testDoPost() throws ServletException, IOException {
        CreateTrainingDto trainingDto = CreateTrainingDto.builder()
                .typeOfTrain("gym")
                .advanced(new HashMap<>())
                .calories(5000)
                .duration(Duration.ofHours(1))
                .build();

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(trainingDto.toString())));
        when(objectMapper.readValue(request.getReader(), CreateTrainingDto.class)).thenReturn(trainingDto);

        servlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_CREATED);
        verify(response.getWriter()).write("Training added success");
    }
}
