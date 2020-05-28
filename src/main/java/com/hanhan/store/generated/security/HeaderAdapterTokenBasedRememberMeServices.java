/**
 * 
 */
package com.hanhan.store.generated.security;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.util.StringUtils;

/**
 * @author JerryXia
 *
 */
public class HeaderAdapterTokenBasedRememberMeServices extends TokenBasedRememberMeServices {
    public static final String DEFAULT_HEADER_NAME_PREFIX = "X-Security-";

    private final String headerName;

    public HeaderAdapterTokenBasedRememberMeServices(String headerPrefix, String headerSuffix, String key, UserDetailsService userDetailsService) {
        this(headerPrefix + headerSuffix, key, userDetailsService);
    }

    public HeaderAdapterTokenBasedRememberMeServices(String headerName, String key, UserDetailsService userDetailsService) {
        super(key, userDetailsService);
        this.headerName = headerName;
    }

    @Override
    public void onLoginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
        String username = retrieveUserName(successfulAuthentication);
        String password = retrievePassword(successfulAuthentication);

        // If unable to find a username and password, just abort as TokenBasedRememberMeServices is unable to construct a valid token in this case.
        if (StringUtils.isEmpty(username)) {
            if (logger.isDebugEnabled()) {
                logger.debug("Unable to retrieve username");
            }
            return;
        }

        if (StringUtils.isEmpty(password)) {
            UserDetails user = getUserDetailsService().loadUserByUsername(username);
            password = user.getPassword();

            if (StringUtils.isEmpty(password)) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Unable to obtain password for user: " + username);
                }
                return;
            }
        }

        int tokenLifetime = calculateLoginLifetime(request, successfulAuthentication);
        long expiryTime = System.currentTimeMillis();
        // SEC-949
        expiryTime += 1000L * (tokenLifetime < 0 ? TWO_WEEKS_S : tokenLifetime);

        String signatureValue = makeTokenSignature(expiryTime, username, password);
        String[] tokens = new String[] { username, Long.toString(expiryTime), signatureValue };

        // setCookie(tokens, tokenLifetime, request, response);
        // if (logger.isDebugEnabled()) {
        // logger.debug("Added remember-me cookie for user '" + username + "', expiry: '" + new Date(expiryTime) + "', cookie name is: " + getCookieName());
        // }

        setHeader(tokens, response);
        if (logger.isDebugEnabled()) {
            logger.debug("Added remember-me header for user, header is: " + this.headerName);
        }
    }

    private void setHeader(String[] tokens, HttpServletResponse response) {
        String cookieValue = encodeCookie(tokens);
        response.setHeader(this.headerName, cookieValue);
    }
    
    @Override
    protected void cancelCookie(HttpServletRequest request, HttpServletResponse response) {
        // response.addCookie(empty cookie);
    }

    @Override
    protected String extractRememberMeCookie(HttpServletRequest request) {
        String headerValue = null;
        Enumeration<String> headerValuesEnumeration = request.getHeaders(this.headerName);
        while (headerValuesEnumeration.hasMoreElements()) {
            headerValue = headerValuesEnumeration.nextElement();
            if (headerValue != null) {
                break;
            }
        }
        if(headerValue == null) {
            headerValue = super.extractRememberMeCookie(request);
        }
        return headerValue;
    }
}
