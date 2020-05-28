/**
 * 
 */
package com.hanhan.store.generated.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author JerryXia
 *
 */
@SuppressWarnings("deprecation")
public class ApplicationStartedListener implements ApplicationListener<ApplicationStartedEvent> {
    private static final Logger log = LoggerFactory.getLogger(ApplicationReadyListener.class);

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        if (log.isInfoEnabled()) {
            log.info("find Application is starting, args: {}", String.join(", ", event.getArgs()));
        }
    }
}
