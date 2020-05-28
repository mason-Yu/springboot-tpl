/**
 * 
 */
package com.hanhan.store.generated.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;

/**
 * @author JerryXia
 *
 */
public class ApplicationContextStartedListener implements ApplicationListener<ContextStartedEvent> {
    private static final Logger log = LoggerFactory.getLogger(ApplicationContextStartedListener.class);

    @Override
    public void onApplicationEvent(ContextStartedEvent event) {
        if (log.isInfoEnabled()) {
            log.info("find Application:'{}' context started", event.getApplicationContext().getId());
        }
    }
}

