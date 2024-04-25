package com.suhoi.in.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suhoi.dto.AuthDto;
import com.suhoi.dto.CreateTypeDto;
import com.suhoi.facade.TypeOfTrainingFacade;
import com.suhoi.facade.UserFacade;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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

public class AuthServletTest {
    @Mock
    private UserFacade userFacade;

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

    @Mock
    private HttpSession session;

    private AuthServlet servlet;

    PrintWriter writer;

    @BeforeEach
    public void setUp() throws ServletException, IOException {
        MockitoAnnotations.initMocks(this);

        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("userFacade")).thenReturn(userFacade);
        when(servletContext.getAttribute("objectMapper")).thenReturn(objectMapper);
        writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);
        servlet = new AuthServlet();
        servlet.init(servletConfig);

        when(request.getSession()).thenReturn(session);
    }

    @Test
    @DisplayName("Authenticate user servlet test")
    public void testDoGet() throws ServletException, IOException {
        AuthDto authDto = new AuthDto();
        authDto.setUsername("testuser");
        authDto.setPassword("testpass");


        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(authDto.toString())));
        when(objectMapper.readValue(request.getReader(), AuthDto.class)).thenReturn(authDto);

        servlet.doGet(request, response);

        verify(userFacade).auth(authDto);
        verify(session).setAttribute("user", authDto);
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(response.getWriter()).write("User auth successful! ");
    }
}
