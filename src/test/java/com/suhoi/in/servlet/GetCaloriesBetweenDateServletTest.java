package com.suhoi.in.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suhoi.dto.RangeDto;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.time.LocalDate;

import static org.mockito.Mockito.*;

public class GetCaloriesBetweenDateServletTest {
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

    private GetCaloriesBetweenDateServlet servlet;

    private PrintWriter writer;

    @BeforeEach
    public void setUp() throws ServletException, IOException {
        MockitoAnnotations.initMocks(this);

        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("trainingFacade")).thenReturn(trainingFacade);
        when(servletContext.getAttribute("objectMapper")).thenReturn(objectMapper);
        writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);
        servlet = new GetCaloriesBetweenDateServlet();
        servlet.init(servletConfig);
    }

    @Test
    @DisplayName("Get calories between date servlet test")
    public void testDoGet() throws ServletException, IOException {
        RangeDto rangeDto = new RangeDto(LocalDate.of(2024, 4, 1), LocalDate.of(2024, 4, 15));
        String rangeDtoJson = "{\"startDate\":\"2024-04-01\",\"endDate\":\"2024-04-15\"}";
        BufferedReader reader = new BufferedReader(new StringReader(rangeDtoJson));
        when(request.getReader()).thenReturn(reader);
        when(objectMapper.readValue(reader, RangeDto.class)).thenReturn(rangeDto);
        when(trainingFacade.getCaloriesBetweenDate(rangeDto)).thenReturn(1500);

        servlet.doGet(request, response);

        verify(trainingFacade).getCaloriesBetweenDate(rangeDto);
        verify(objectMapper).writeValueAsString(1500);
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }
}
