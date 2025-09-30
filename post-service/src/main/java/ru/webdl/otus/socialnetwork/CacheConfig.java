package ru.webdl.otus.socialnetwork;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    @Primary
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeineCacheBuilder());
        cacheManager.setCacheNames(List.of("authorPosts"));
        return cacheManager;
    }

    Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .initialCapacity(1000)
                .maximumSize(10000)
                .expireAfterAccess(30, TimeUnit.MINUTES)
                .recordStats();
    }

    @Bean
    public CacheManager userFeedCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("authorPosts");
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .initialCapacity(500)
                .maximumSize(2000)
                .expireAfterWrite(15, TimeUnit.MINUTES)
                .expireAfterAccess(5, TimeUnit.MINUTES)
                .recordStats());
        return cacheManager;
    }
}
