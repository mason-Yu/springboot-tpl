/**
 * 
 */
package com.hanhan.store.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.hanhan.store.generated.security.AbstractRememberMeBodyWriteAuthenticationSuccessHandler;

/**
 * @author JerryXia
 *
 */
@Component
public class RememberMeBodyWriteAuthenticationSuccessHandler extends AbstractRememberMeBodyWriteAuthenticationSuccessHandler {

    @Override
    protected void writeBodyHandle(HttpServletRequest request, HttpServletResponse response, Authentication authentication, String tokenValue)
            throws IOException, ServletException {
        response.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        // TODO Auto-generated method stub
        response.getOutputStream().print("{\"token\": \"" + tokenValue + "\"}");
    }
}
