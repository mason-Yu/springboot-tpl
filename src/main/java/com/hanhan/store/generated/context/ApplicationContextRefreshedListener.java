/**
 * 
 */
package com.hanhan.store.generated.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author JerryXia
 *
 */
public class ApplicationContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger log = LoggerFactory.getLogger(ApplicationContextRefreshedListener.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (log.isInfoEnabled()) {
            log.info("find Application:'{}' context refreshed", event.getApplicationContext().getId());
        }
    }
}

