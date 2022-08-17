package com.springboot.lookoutside.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationFailureHandlerCustom implements AuthenticationFailureHandler {
	
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.warn("Authentication Error: {}", exception.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
