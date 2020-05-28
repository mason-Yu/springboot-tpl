/**
 * 
 */
package com.hanhan.store.generated.task;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;

import com.vip.vjtools.vjkit.collection.MapUtil;
import com.vip.vjtools.vjkit.mapper.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;

import com.hanhan.store.generated.RuntimeVars;
import com.hanhan.store.generated.web.CorsConfigurationService;

/**
 * @author JerryXia
 *
 */
@Component
@Slf4j
public class CorsConfigRefreshTask {

    private CorsConfigurationService corsConfigurationService;

    @PostConstruct
    public void init() {
        try {
            this.corsConfigurationService = RuntimeVars.applicationContext.getBean(CorsConfigurationService.class);
        } catch(BeansException ex) {
            this.corsConfigurationService = RuntimeVars.DEFAULT_CORS_CONFIGURATION_SERVICE;
        }
    }

    @Scheduled(initialDelay = 1000, fixedDelay = 5 * 60 * 1000)
    public void refreshNewerCorsConfig() {
        if (log.isDebugEnabled()) {
            log.debug("refreshNewerCorsConfig -> begin");
        }
        Map<String, CorsConfiguration> corsConfigs = corsConfigurationService.load();
        if (log.isDebugEnabled()) {
            log.debug("refreshNewerCorsConfig -> result: {}", JsonMapper.INSTANCE.toJson(corsConfigs));
        }
        if(MapUtils.isNotEmpty(corsConfigs)){
            Set<Entry<String, CorsConfiguration>> entrys = corsConfigs.entrySet();
            for (Entry<String, CorsConfiguration> entry : entrys) {
                CorsConfiguration corsConfiguration = RuntimeVars.CORS_CONFIGURATION_SOURCE_CORS_CONFIGURATIONS.get(entry.getKey());
                if (corsConfiguration == null) {
                    RuntimeVars.CORS_CONFIGURATION_SOURCE_CORS_CONFIGURATIONS.put(entry.getKey(), entry.getValue());
                } else {
                    corsConfiguration.setAllowedOrigins(entry.getValue().getAllowedOrigins());
                    corsConfiguration.setAllowedMethods(entry.getValue().getAllowedMethods());
                    corsConfiguration.setAllowedHeaders(entry.getValue().getAllowedHeaders());
                    corsConfiguration.setExposedHeaders(entry.getValue().getExposedHeaders());
                    corsConfiguration.setAllowCredentials(entry.getValue().getAllowCredentials());
                    corsConfiguration.setMaxAge(entry.getValue().getMaxAge());
                }
            }
        }

    }
}
