/**
 * 
 */
package com.hanhan.store.generated;

import java.util.Map;
import java.util.Set;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.hanhan.store.generated.autoconfigure.AppProperties;
import com.hanhan.store.generated.web.CorsConfigurationService;
import com.hanhan.store.generated.web.InMemoryCorsConfigurationServiceImpl;


/**
 * @author JerryXia
 *
 */
public final class RuntimeVars {

    public static ConfigurableEnvironment env;
    public static AppProperties           sysComponent;

    public static ConfigurableApplicationContext applicationContext;

    /**
     * org.springframework.security.config.annotation.web.configurers.CorsConfigurer.CORS_CONFIGURATION_SOURCE_BEAN_NAME
     */
    public static final UrlBasedCorsConfigurationSource CORS_CONFIGURATION_SOURCE = new UrlBasedCorsConfigurationSource();

    /**
     * org.springframework.web.cors.UrlBasedCorsConfigurationSource.corsConfigurations
     */
    public static Map<String, CorsConfiguration> CORS_CONFIGURATION_SOURCE_CORS_CONFIGURATIONS;

    public static final CorsConfigurationService DEFAULT_CORS_CONFIGURATION_SERVICE = new InMemoryCorsConfigurationServiceImpl();

    /**
     * org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor.scheduledTasks
     */
    public static Map<Object, Set<ScheduledTask>> scheduledTasks;

    /**
     * org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor.registrar
     */
    public static ScheduledTaskRegistrar taskRegistrar;

    /**
     * org.springframework.scheduling.config.ScheduledTaskRegistrar.scheduledTasks
     */
    public static Set<ScheduledTask> taskRegistrarScheduledTasks;

}
