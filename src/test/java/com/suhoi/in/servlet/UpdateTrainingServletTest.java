package com.suhoi.in.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suhoi.dto.CreateUserDto;
import com.suhoi.dto.RangeDto;
import com.suhoi.dto.UpdateTrainingDto;
import com.suhoi.facade.TrainingFacade;
import com.suhoi.facade.UserFacade;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.HashMap;

import static org.mockito.Mockito.*;

public class UpdateTrainingServletTest {
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

    private UpdateTrainingServlet servlet;

    private PrintWriter writer;

    @BeforeEach
    public void setUp() throws ServletException, IOException {
        MockitoAnnotations.initMocks(this);

        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("trainingFacade")).thenReturn(trainingFacade);
        when(servletContext.getAttribute("objectMapper")).thenReturn(objectMapper);
        writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);
        servlet = new UpdateTrainingServlet();
        servlet.init(servletConfig);
    }

    @Test
    @DisplayName("Update training servlet test")
    public void testDoPut() throws ServletException, IOException {
        UpdateTrainingDto updateTrainingDto = new UpdateTrainingDto(1L, 1L, 500, new HashMap<>());
        String updateTrainingDtoJson = "{\"id\":1,\"userId\":1,\"calories\":500,\"advanced\":{}}";
        BufferedReader reader = new BufferedReader(new StringReader(updateTrainingDtoJson));
        when(request.getReader()).thenReturn(reader);
        when(objectMapper.readValue(reader, UpdateTrainingDto.class)).thenReturn(updateTrainingDto);

        servlet.doPut(request, response);

        verify(trainingFacade).edit(updateTrainingDto);
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(writer).write("Training update success");
    }
}
