package com.suhoi.in.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suhoi.dto.CreateTypeDto;
import com.suhoi.facade.TypeOfTrainingFacade;
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

import static org.mockito.Mockito.*;

public class AddNewTypeOfTrainingServletTest {
    @Mock
    private TypeOfTrainingFacade typeOfTrainingFacade;

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

    private AddNewTypeOfTrainingServlet servlet;

    @BeforeEach
    public void setUp() throws ServletException {
        MockitoAnnotations.initMocks(this);

        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("typeOfTrainingFacade")).thenReturn(typeOfTrainingFacade);
        when(servletContext.getAttribute("objectMapper")).thenReturn(objectMapper);

        servlet = new AddNewTypeOfTrainingServlet();
        servlet.init(servletConfig);
    }

    @Test
    @DisplayName("Test add new type of training servlet")
    public void testDoPost() throws ServletException, IOException {
        CreateTypeDto cardio = CreateTypeDto.builder()
                .type("cardio")
                .build();

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(cardio.toString())));
        when(objectMapper.readValue(request.getReader(), CreateTypeDto.class)).thenReturn(cardio);

        servlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_CREATED);
        verify(response.getWriter()).write("Training added success");
    }
}
