/**
 * 
 */
package com.hanhan.store.generated.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStoppedEvent;

/**
 * @author JerryXia
 *
 */
public class ApplicationContextStoppedListener implements ApplicationListener<ContextStoppedEvent> {
    private static final Logger log = LoggerFactory.getLogger(ApplicationContextStoppedListener.class);

    @Override
    public void onApplicationEvent(ContextStoppedEvent event) {
        if (log.isInfoEnabled()) {
            log.info("find Application:'{}' context stopped", event.getApplicationContext().getId());
        }
    }
}
