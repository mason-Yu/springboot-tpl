/**
 * 
 */
package com.hanhan.store.generated.autoconfigure;

import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.scheduling.annotation.AsyncAnnotationBeanPostProcessor;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author JerryXia
 *
 */
@EnableAsync
@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class ApplicationAsyncConfiguration extends AsyncConfigurerSupport {
    private static final Logger log = LoggerFactory.getLogger(ApplicationAsyncConfiguration.class);

    /**
     * beanName: taskExecutor
     * 
     * @return org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
     */
    @Bean(name = AsyncAnnotationBeanPostProcessor.DEFAULT_TASK_EXECUTOR_BEAN_NAME, destroyMethod = "destroy")
    public ThreadPoolTaskExecutor taskExecutor() {
        if (log.isDebugEnabled()) {
            log.debug("Initializing ExecutorService 'taskExecutor' -> begin constructor");
        }
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(availableProcessors);
        executor.setThreadNamePrefix("task-executor-");
        // executor.setWaitForTasksToCompleteOnShutdown(true);
        // executor.setAwaitTerminationSeconds(4);
        return executor;
    }

    @Override
    public Executor getAsyncExecutor() {
        if (log.isDebugEnabled()) {
            log.debug("getAsyncExecutor -> begin");
        }
        return null;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        if (log.isDebugEnabled()) {
            log.debug("getAsyncUncaughtExceptionHandler -> begin");
        }
        SimpleAsyncUncaughtExceptionHandler handler = new SimpleAsyncUncaughtExceptionHandler();
        return handler;
    }
}
