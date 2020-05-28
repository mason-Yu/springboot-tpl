/**
 * 
 */
package com.hanhan.store.generated.web;

import java.util.Map;

/**
 * @author JerryXia
 *
 */
public interface CorsConfigurationService {

    Map<String, org.springframework.web.cors.CorsConfiguration> load();

    boolean write(String path, org.springframework.web.cors.CorsConfiguration corsConfiguration);

    boolean write(String path, String allowedOrigins, String allowedMethods, String allowedHeaders, String exposedHeaders, Boolean allowCredentials, Long maxAge);
}
