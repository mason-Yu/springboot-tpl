/**
 * 
 */
package com.hanhan.store.generated.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author JerryXia
 *
 */
public abstract class AbstractRememberMeBodyWriteAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private String headerAdapterTokenBasedRememberMeServicesHeaderName;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String tokenHeaderValue = null;
        Collection<String> headerValues = response.getHeaders(headerAdapterTokenBasedRememberMeServicesHeaderName);
        for (String headerValue : headerValues) {
            if (headerValue != null) {
                tokenHeaderValue = headerValue;
                break;
            }
        }
        writeBodyHandle(request, response, authentication, tokenHeaderValue);
    }

    /**
     * <pre>
     * <code>
     * response.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE);
     * response.setCharacterEncoding(StandardCharsets.UTF_8.name());
     * // TODO Auto-generated method stub
     * </code>
     * </pre>
     * 
     * @param request
     * @param response
     * @param authentication
     * @param tokenHeaderValue
     * @throws IOException
     * @throws ServletException
     */
    protected abstract void writeBodyHandle(HttpServletRequest request, HttpServletResponse response, Authentication authentication, String tokenValue)
            throws IOException, ServletException;

    public String getHeaderAdapterTokenBasedRememberMeServicesHeaderName() {
        return headerAdapterTokenBasedRememberMeServicesHeaderName;
    }

    public void setHeaderAdapterTokenBasedRememberMeServicesHeaderName(String headerAdapterTokenBasedRememberMeServicesHeaderName) {
        this.headerAdapterTokenBasedRememberMeServicesHeaderName = headerAdapterTokenBasedRememberMeServicesHeaderName;
    }
}
