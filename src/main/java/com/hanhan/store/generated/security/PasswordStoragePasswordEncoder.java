/**
 * 
 */
package com.hanhan.store.generated.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hanhan.store.generated.security.PasswordStorage.CannotPerformOperationException;
import com.hanhan.store.generated.security.PasswordStorage.InvalidHashException;

/**
 * @author JerryXia
 *
 */
public class PasswordStoragePasswordEncoder implements PasswordEncoder {
    private static final Logger log = LoggerFactory.getLogger(PasswordStoragePasswordEncoder.class);

    @Override
    public String encode(CharSequence rawPassword) {
        try {
            return PasswordStorage.createHash(rawPassword.toString());
        } catch (CannotPerformOperationException e) {
            if(log.isErrorEnabled()) {
                log.error("PasswordStoragePasswordEncoder encode fail", e);
            }
        }
        return null;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (encodedPassword == null) {
            throw new InternalAuthenticationServiceException("encodedPassword is null");
        }
        try {
            return PasswordStorage.verifyPassword(rawPassword.toString(), encodedPassword);
        } catch (CannotPerformOperationException e) {
            if(log.isErrorEnabled()) {
                log.error("PasswordStoragePasswordEncoder matches fail", e);
            }
        } catch (InvalidHashException e) {
            if(log.isErrorEnabled()) {
                log.error("PasswordStoragePasswordEncoder matches fail", e);
            }
        }
        return false;
    }

}
