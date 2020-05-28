/**
 * 
 */
package com.hanhan.store.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginAuthenticationFailureHandler implements AuthenticationFailureHandler {

    public LoginAuthenticationFailureHandler() {

    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String msg = null;
        Class<?> exceptionClass = exception.getClass();
        if (UsernameNotFoundException.class.equals(exceptionClass)) {
            msg = exception.getMessage();
        } else if (BadCredentialsException.class.equals(exceptionClass)) {
            msg = exception.getMessage();
        } else {
            msg = exception.getMessage();
        }
        response.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        // TODO Auto-generated method stub
        response.getOutputStream().print("{\"msg\": \"" + msg + "\"}");
    }

}
