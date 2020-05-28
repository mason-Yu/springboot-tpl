/**
 * 
 */
package com.hanhan.store.generated.web;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;

/**
 * @author JerryXia
 *
 */
public class InMemoryCorsConfigurationServiceImpl implements CorsConfigurationService {
    private LinkedHashMap<String, CorsConfiguration> storageConfigs = new LinkedHashMap<String, CorsConfiguration>();

    @Override
    public Map<String, CorsConfiguration> load() {
        return this.storageConfigs;
    }

    @Override
    public boolean write(String path, CorsConfiguration corsConfiguration) {
        this.storageConfigs.put(path, corsConfiguration);
        return true;
    }

    @Override
    public boolean write(String path, String allowedOrigins, String allowedMethods, String allowedHeaders, String exposedHeaders, Boolean allowCredentials, Long maxAge) {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        if (StringUtils.hasLength(allowedOrigins)) {
            corsConfiguration.setAllowedOrigins(Arrays.asList(allowedOrigins.split(",")));
        } else {
            // here is default allowed origin
            corsConfiguration.addAllowedOrigin("http://localhost:8000");
            corsConfiguration.addAllowedOrigin("http://localhost:8080");
        }
        if (StringUtils.hasLength(allowedMethods)) {
            corsConfiguration.setAllowedMethods(Arrays.asList(allowedMethods.split(",")));
        }
        if (StringUtils.hasLength(allowedHeaders)) {
            corsConfiguration.setAllowedHeaders(Arrays.asList(allowedHeaders.split(",")));
        }
        if (StringUtils.hasLength(exposedHeaders)) {
            corsConfiguration.setExposedHeaders(Arrays.asList(exposedHeaders.split(",")));
        }
        corsConfiguration.setAllowCredentials(allowCredentials);
        corsConfiguration.setMaxAge(maxAge);
        return write(path, corsConfiguration);
    }
}
