/**
 * 
 */
package com.hanhan.store.generated.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;

import com.hanhan.store.generated.RuntimeVars;

/**
 * @author JerryXia
 *
 */
public class ApplicationEnvironmentPreparedListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    private static final Logger log = LoggerFactory.getLogger(ApplicationEnvironmentPreparedListener.class);

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        if (log.isInfoEnabled()) {
            log.info("find Application's environment is prepared.");
        }
        RuntimeVars.env = event.getEnvironment();
    }
}
