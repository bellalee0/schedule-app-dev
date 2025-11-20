package com.example.scheduleappdev.global.Filter;

import com.example.scheduleappdev.global.Exception.TodoServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.io.PrintWriter;

@Component
@Order(2)
public class FilterExceptionHandler extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        try {
            chain.doFilter(request, response);
        } catch (TodoServiceException e) {
            handleTodoServiceException(httpResponse, e);
        }
    }

    private void handleTodoServiceException(HttpServletResponse response, TodoServiceException e) throws IOException {
        response.setStatus(e.getErrorMessage().getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        FilterErrorResponseDTO errorResponse = new FilterErrorResponseDTO(e.getErrorMessage(), e.getMessage());
        writeErrorResponse(response,errorResponse);
    }
    private void writeErrorResponse(HttpServletResponse response, FilterErrorResponseDTO body) throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(body);
        try (PrintWriter writer = response.getWriter()) {
            writer.write(json);
            writer.flush();
        }
    }
}
