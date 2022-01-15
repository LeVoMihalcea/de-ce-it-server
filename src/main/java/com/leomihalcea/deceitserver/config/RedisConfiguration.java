package com.leomihalcea.deceitserver.config;

import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class RedisConfiguration {
//    private static final Logger logger = LoggerFactory.getLogger(RedisConfiguration.class);
//
//    public static final String POS_MAC_CACHE_KEY = "posMacCache";
//    public static final String CACHE_MANAGER_NAME = "cacheManager";
//
//    @Bean(value = CACHE_MANAGER_NAME)
//    public CacheManager cacheManager(RedissonClient redissonClient, RedisProperties redisProperties) {
//        logger.debug("Creating cache manager from redisson client.");
//        Map<String, CacheConfig> config = redisProperties.getCacheSpecs().stream()
//                .collect(Collectors.toMap(RedisProperties.CacheSpec::getName, this::buildCache));
//        RedissonSpringCacheManager redissonSpringCacheManager = new RedissonSpringCacheManager(redissonClient, config);
//        logger.debug("Cache manager created from redisson client.");
//        return redissonSpringCacheManager;
//    }
//
//    private CacheConfig buildCache(RedisProperties.CacheSpec cacheSpec) {
//        logger.debug("Building cache spec for {}", cacheSpec.getName());
//        CacheConfig result = new CacheConfig();
//
//        result.setTTL(cacheSpec.getTtl().toMillis());
//        result.setMaxIdleTime(cacheSpec.getIdle().toMillis());
//        result.setMaxSize(cacheSpec.getMaxSize());
//        logger.debug("Cache spec for {} built.", cacheSpec.getName());
//        return result;
//    }

}