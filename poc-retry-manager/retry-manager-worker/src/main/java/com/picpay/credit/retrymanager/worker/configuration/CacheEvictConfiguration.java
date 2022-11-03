package com.picpay.credit.retrymanager.worker.configuration;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CacheEvictConfiguration {
    
    @Scheduled(fixedDelay = 300000)
    @CacheEvict(value = "cacheConfigurationRetry", allEntries = true)
    public void process() {
        log.info("Init process. Cache evicted");
    }

}
