/**
 * 
 */
package com.hanhan.store.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.cors.CorsConfiguration;

import com.hanhan.store.generated.Consts;
import com.hanhan.store.generated.web.CorsConfigurationService;
import com.vip.vjtools.vjkit.mapper.JsonMapper;

/**
 * @author JerryXia
 *
 */
@Service
public class DbStorageCorsConfigurationServiceImpl implements CorsConfigurationService {
    @Autowired
    private AppSettingService appSettingService;

    private static final String APP_SETTING_KEY = "site.cors_configs";

    @Override
    public Map<String, CorsConfiguration> load() {
        String value = appSettingService.get(APP_SETTING_KEY);
        HashMap<String, CorsConfiguration> map = JsonMapper.INSTANCE.fromJson(value, Consts.HASHMAP_STRING_CORSCONFIGURATION_TYPE);
        return map;
    }

    @Override
    public boolean write(String path, CorsConfiguration corsConfiguration) {
        Map<String, CorsConfiguration> map = load();
        map.put(path, corsConfiguration);
        String value = JsonMapper.INSTANCE.toJson(map);
        appSettingService.set(APP_SETTING_KEY, value);
        return true;
    }

    @Override
    public boolean write(String path, String allowedOrigins, String allowedMethods, String allowedHeaders, String exposedHeaders, Boolean allowCredentials, Long maxAge) {
        Map<String, CorsConfiguration> map = load();
        CorsConfiguration corsConfiguration = map.get(path);
        if (corsConfiguration == null) {
            corsConfiguration = new CorsConfiguration();
            map.put(path, corsConfiguration);
        }
        corsConfiguration.setAllowedOrigins(Consts.COMMA_SPLITTER.splitToList(allowedOrigins));
        corsConfiguration.setAllowedMethods(Consts.COMMA_SPLITTER.splitToList(allowedMethods));
        corsConfiguration.setAllowedHeaders(Consts.COMMA_SPLITTER.splitToList(allowedHeaders));
        corsConfiguration.setExposedHeaders(Consts.COMMA_SPLITTER.splitToList(exposedHeaders));
        corsConfiguration.setAllowCredentials(allowCredentials);
        corsConfiguration.setMaxAge(maxAge);
        String value = JsonMapper.INSTANCE.toJson(map);
        appSettingService.set(APP_SETTING_KEY, value);
        return true;
    }
}
