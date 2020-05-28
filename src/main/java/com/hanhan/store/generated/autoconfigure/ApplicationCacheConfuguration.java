/**
 * 
 */
package com.hanhan.store.generated.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache;

import com.hanhan.store.generated.cache.EmptyKeyGenerator;

/**
 * @author JerryXia
 *
 */
@Configuration
@EnableCaching
public class ApplicationCacheConfuguration {
    private static final Logger log = LoggerFactory.getLogger(ApplicationCacheConfuguration.class);

    @Bean(name = "emptyKeyGenerator")
    public EmptyKeyGenerator emptyKeyGenerator() {
        return new EmptyKeyGenerator();
    }

    @Bean
    public SpringCacheBasedUserCache userCache(CacheManager cacheManager) throws Exception {
        String cacheName = "userdetails";
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            if (log.isInfoEnabled()) {
                log.info("cacheName: {} have't configuration, try to auto create.", cacheName);
            }
            if (cacheManager instanceof org.springframework.cache.ehcache.EhCacheCacheManager) {
                EhCacheCacheManager ehCacheCacheManager = (EhCacheCacheManager) cacheManager;
                ehCacheCacheManager.getCacheManager().addCacheIfAbsent(cacheName);
                cache = cacheManager.getCache(cacheName);
            } else {
                // ignore, wait add more implementation
                if (log.isWarnEnabled()) {
                    log.warn("CacheManager: {} haven't supported.", cacheManager.getClass().getName());
                }
            }
        }
        SpringCacheBasedUserCache userCache = new SpringCacheBasedUserCache(cache);
        return userCache;
    }
}
