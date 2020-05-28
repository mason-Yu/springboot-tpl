/**
 * 
 */
package com.hanhan.store.generated.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;

import com.hanhan.store.generated.RuntimeVars;

/**
 * Before {@link org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext} refresh, after
 * bean definition
 * 
 * @author JerryXia
 *
 */
public class ApplicationPreparedListener implements ApplicationListener<ApplicationPreparedEvent> {
    private static final Logger log = LoggerFactory.getLogger(ApplicationPreparedListener.class);

    @Override
    public void onApplicationEvent(ApplicationPreparedEvent event) {
        if (log.isInfoEnabled()) {
            log.info("find Application is prepared, args: {}", String.join(", ", event.getArgs()));
        }
        RuntimeVars.applicationContext = event.getApplicationContext();
    }
}
