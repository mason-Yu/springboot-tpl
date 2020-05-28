/**
 * 
 */
package com.hanhan.store.generated.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.core.userdetails.UserCache;

/**
 * @author JerryXia
 *
 */
public class DaoAuthenticationProviderPostProcess implements ObjectPostProcessor<AbstractUserDetailsAuthenticationProvider> {
    private static final Logger log = LoggerFactory.getLogger(DaoAuthenticationProviderPostProcess.class);

    private final UserCache userCache;

    public DaoAuthenticationProviderPostProcess(UserCache userCache) {
        if(log.isDebugEnabled()) {
            log.debug("DaoAuthenticationProviderPostProcess userCache: {}", userCache.getClass().getName());
        }
        this.userCache = userCache;
    }

    @Override
    public <O extends AbstractUserDetailsAuthenticationProvider> O postProcess(O object) {
        if(log.isDebugEnabled()) {
            log.debug("DaoAuthenticationProviderPostProcess postProcess object: {}", object.getClass().getName());
        }
        object.setUserCache(this.userCache);
        return object;
    }
}
