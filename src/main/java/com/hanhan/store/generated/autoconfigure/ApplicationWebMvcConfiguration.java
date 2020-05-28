/**
 *
 */
package com.hanhan.store.generated.autoconfigure;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.hanhan.store.generated.RuntimeVars;

/**
 * <p>
 * {@link org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter}
 * </p>
 * 
 * <p>
 * {@link org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.EnableWebMvcConfiguration}
 * </p>
 * 
 * <p>
 * {@link org.springframework.web.servlet.config.annotation.EnableWebMvc} will import {@link org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport}
 * </p>
 *
 * @author guqk
 */
@Configuration
public class ApplicationWebMvcConfiguration extends WebMvcConfigurerAdapter {
    private static final Logger log                   = LoggerFactory.getLogger(ApplicationWebMvcConfiguration.class);
    private static final String CORS_FILTER_BEAN_NAME = "corsFilter";

    //@Bean(name = CORS_FILTER_BEAN_NAME)
    public CorsFilter corsFilter() {
        CorsFilter corsFilter = new CorsFilter(RuntimeVars.CORS_CONFIGURATION_SOURCE);
        return corsFilter;
    }

    /**
     * Note that remove an exists path CorsConfiguration will not take effect, and override an exists one is permitted.
     * 
     * @return
     */
    @Bean
    @ConditionalOnBean(name = CORS_FILTER_BEAN_NAME)
    public FilterRegistrationBean corsFilterRegistrationBean() {
        if (log.isDebugEnabled()) {
            log.debug("register CorsFilter, expected after requestContextFilter(order=-105) and before springSecurityFilterChain(order={}), view the console log.",
                    org.springframework.boot.autoconfigure.security.SecurityProperties.DEFAULT_FILTER_ORDER);
        }
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        // org.springframework.security.config.annotation.web.configurers.CorsConfigurer.CORS_FILTER_BEAN_NAME
        // registrationBean.setName(CORS_FILTER_BEAN_NAME);
        registrationBean.setOrder(FilterRegistrationBean.REQUEST_WRAPPER_FILTER_MAX_ORDER - 104);
        registrationBean.setFilter(corsFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
    
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        if (log.isDebugEnabled()) {
            log.debug("configure MessageConverters -> begin");
        }
        for(HttpMessageConverter<?> converter : converters) {
            if (log.isDebugEnabled()) {
                log.debug(converter.getClass().getName());
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("configure MessageConverters -> end");
        }
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        if (log.isDebugEnabled()) {
            log.debug("extend MessageConverters -> begin");
        }
        //converters.add(new Mapping2EntityFormHttpMessageConverter());
        for(HttpMessageConverter<?> converter : converters) {
            if (log.isDebugEnabled()) {
                log.debug(converter.getClass().getName());
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("extend MessageConverters -> end");
        }
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        if (log.isDebugEnabled()) {
            log.debug("configure AsyncSupport -> begin");
        }

        if (log.isDebugEnabled()) {
            log.debug("configure AsyncSupport -> end");
        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (log.isDebugEnabled()) {
            log.debug("add Interceptors -> begin");
        }

        if (log.isDebugEnabled()) {
            log.debug("add Interceptors -> end");
        }
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        if (log.isDebugEnabled()) {
            log.debug("add CorsMappings -> begin");
        }
        // registry.addMapping("/apiproxy").allowedOrigins("*").allowedMethods("*").allowedHeaders("*").allowCredentials(true).
        // maxAge(36001234L);
        if (log.isDebugEnabled()) {
            log.debug("add CorsMappings -> end");
        }
    }

    /**
     * <p>
     * 默认情况下，Spring Boot 将从 classpath 中的/static(或/public或/resources或/META-INF/resources)目录或ServletContext的根目录中提供静态内容。
     * </p>
     * <p>
     * 它使用来自 Spring MVC 的ResourceHttpRequestHandler，因此您可以通过添加自己的WebMvcConfigurerAdapter并覆盖addResourceHandlers方法来修改该行为。
     * </p>
     * 
     * <p>
     * 默认情况下，资源映射到/**，但您可以通过spring.mvc.static-path-pattern进行调整。
     * </p>
     * <p>
     * 例如，将所有资源重新定位到/resources/**可以实现如下：<code>spring.mvc.static-path-pattern=/resources/**</code>
     * <p>
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (log.isDebugEnabled()) {
            log.debug("add ResourceHandlers -> begin");
        }

        if (log.isDebugEnabled()) {
            log.debug("add ResourceHandlers -> end");
        }
    }
}
