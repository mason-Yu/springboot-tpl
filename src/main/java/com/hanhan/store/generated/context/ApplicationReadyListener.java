/**
 * 
 */
package com.hanhan.store.generated.context;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.cors.CorsConfiguration;

import com.hanhan.store.generated.RuntimeVars;
import com.hanhan.store.generated.template.freemarker.RenderObjectIdDateFtlEx;

public class ApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {
    private static final Logger log = LoggerFactory.getLogger(ApplicationReadyListener.class);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (log.isInfoEnabled()) {
            log.info("find Application:'{}' is Ready, begin configure some bean's behavior", event.getApplicationContext().getId());
        }
        customizedCorsConfigurationSourceCorsConfigurations(event);
        customizedScheduledAnnotationBeanPostProcessor(event);
        customizedScheduledTaskRegistrar();
        customizedBeanFreeMarkerConfiguration(event);
        customizedBeanWebRequestLoggingFilter(event);
    }

    private void customizedCorsConfigurationSourceCorsConfigurations(ApplicationReadyEvent event) {
        String beanName = "RuntimeVars.CORS_CONFIGURATION_SOURCE";
        if (log.isDebugEnabled()) {
            log.debug("start customized Object: {}", beanName);
        }
        Field corsConfigurationsField = ReflectionUtils.findField(org.springframework.web.cors.UrlBasedCorsConfigurationSource.class, "corsConfigurations");
        ReflectionUtils.makeAccessible(corsConfigurationsField);
        @SuppressWarnings("unchecked")
        Map<String, CorsConfiguration> corsConfigurations = (Map<String, CorsConfiguration>) ReflectionUtils.getField(corsConfigurationsField,
                RuntimeVars.CORS_CONFIGURATION_SOURCE);
        RuntimeVars.CORS_CONFIGURATION_SOURCE_CORS_CONFIGURATIONS = corsConfigurations;
    }

    private void customizedScheduledAnnotationBeanPostProcessor(ApplicationReadyEvent event) {
        String beanName = org.springframework.scheduling.config.TaskManagementConfigUtils.SCHEDULED_ANNOTATION_PROCESSOR_BEAN_NAME;
        if (log.isDebugEnabled()) {
            log.debug("start customized Bean: {}", beanName);
        }
        try {
            Object scheduledAnnotationBeanPostProcessor = event.getApplicationContext().getBean(beanName);
            Field scheduledTasksField = ReflectionUtils.findField(org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor.class, "scheduledTasks");
            ReflectionUtils.makeAccessible(scheduledTasksField);
            @SuppressWarnings("unchecked")
            Map<Object, Set<ScheduledTask>> scheduledTasks = (Map<Object, Set<ScheduledTask>>) ReflectionUtils.getField(scheduledTasksField, scheduledAnnotationBeanPostProcessor);
            RuntimeVars.scheduledTasks = scheduledTasks;
        } catch (BeansException ex) {
            if (log.isDebugEnabled()) {
                log.debug("There is no bean: '{}'.", beanName);
            }
        }
    }

    private void customizedScheduledTaskRegistrar() {
        if (log.isDebugEnabled()) {
            log.debug("start customized Bean: {}", org.springframework.scheduling.config.ScheduledTaskRegistrar.class);
        }
        if (RuntimeVars.taskRegistrar != null) {
            Field scheduledTasksField = ReflectionUtils.findField(org.springframework.scheduling.config.ScheduledTaskRegistrar.class, "scheduledTasks");
            ReflectionUtils.makeAccessible(scheduledTasksField);
            @SuppressWarnings("unchecked")
            Set<ScheduledTask> scheduledTasks = (Set<ScheduledTask>) ReflectionUtils.getField(scheduledTasksField, RuntimeVars.taskRegistrar);
            RuntimeVars.taskRegistrarScheduledTasks = scheduledTasks;
        }
    }

    private void customizedBeanFreeMarkerConfiguration(ApplicationReadyEvent event) {
        String beanName = "freeMarkerConfiguration";
        if (log.isDebugEnabled()) {
            log.debug("begin customized Bean: {}", beanName);
        }
        try {
            freemarker.template.Configuration bean = event.getApplicationContext().getBean(beanName, freemarker.template.Configuration.class);
            // val java8ObjectWrapper = new
            // no.api.freemarker.java8.Java8ObjectWrapper(freemarker.template.Configuration.VERSION_2_3_28);
            // target.setObjectWrapper(java8ObjectWrapper);
            // String staticFileUrlPrefix = env.getProperty("app.staticfile.urlprefix", "");
            // target.setSharedVariable("renderCss", new RenderCssFtlEx(staticFileUrlPrefix, env));
            // target.setSharedVariable("renderJs", new RenderJsFtlEx(staticFileUrlPrefix, env));
            bean.setSharedVariable("showIdDate", new RenderObjectIdDateFtlEx());
        } catch (BeansException ex) {
            if (log.isDebugEnabled()) {
                log.debug("There is no bean: '{}'.", beanName);
            }
        }
    }

    private void customizedBeanWebRequestLoggingFilter(ApplicationReadyEvent event) {
        String beanName = "webRequestLoggingFilter";
        if (log.isDebugEnabled()) {
            log.debug("begin customized Bean: {}", beanName);
        }
        try {
            org.springframework.boot.actuate.trace.WebRequestTraceFilter bean = event.getApplicationContext().getBean(beanName,
                    org.springframework.boot.actuate.trace.WebRequestTraceFilter.class);
            bean.setDumpRequests(true);
        } catch (BeansException ex) {
            if (log.isDebugEnabled()) {
                log.debug("There is no bean: '{}'.", beanName);
            }
        }
    }
}