package com.suhoi.in.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suhoi.dto.CreateUserDto;
import com.suhoi.dto.RangeDto;
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

import static org.mockito.Mockito.*;
public class RegistrationServletTest {
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

    private RegistrationUserServlet servlet;

    private PrintWriter writer;

    @BeforeEach
    public void setUp() throws ServletException, IOException {
        MockitoAnnotations.initMocks(this);

        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("userFacade")).thenReturn(userFacade);
        when(servletContext.getAttribute("objectMapper")).thenReturn(objectMapper);
        writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);
        servlet = new RegistrationUserServlet();
        servlet.init(servletConfig);
    }

    @Test
    @DisplayName("Registration user servlet test")
    public void testDoPost() throws ServletException, IOException {
        CreateUserDto createUserDto = new CreateUserDto("testuser", "testpass");
        String createUserDtoJson = "{\"username\":\"testuser\",\"password\":\"testpass\"}";
        BufferedReader reader = new BufferedReader(new StringReader(createUserDtoJson));
        when(request.getReader()).thenReturn(reader);
        when(objectMapper.readValue(reader, CreateUserDto.class)).thenReturn(createUserDto);

        servlet.doPost(request, response);

        verify(userFacade).signUp(createUserDto);
        verify(response).setStatus(HttpServletResponse.SC_CREATED);
        verify(writer).write("User register success! ");
    }
}
