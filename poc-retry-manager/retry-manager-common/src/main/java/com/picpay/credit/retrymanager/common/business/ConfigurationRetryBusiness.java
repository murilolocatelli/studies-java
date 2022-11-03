package com.picpay.credit.retrymanager.common.business;

import java.time.LocalDateTime;
import java.util.Optional;

import com.picpay.credit.retrymanager.common.model.ConfigurationRetry;
import com.picpay.credit.retrymanager.common.repository.ConfigurationRetryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConfigurationRetryBusiness {

    @Autowired
    private ConfigurationRetryRepository configurationRetryRepository;
    
    public ConfigurationRetry create(ConfigurationRetry configurationRetry) {
        configurationRetry.setCreatedAt(LocalDateTime.now());
        configurationRetry.setUpdatedAt(LocalDateTime.now());

        return configurationRetryRepository.save(configurationRetry);
    }

    @Cacheable(value = "cacheConfigurationRetry")
    public Optional<ConfigurationRetry> findByTopic(String topic) {
        log.info("Finding ConfigurationRetry for topic {}", topic);
        return this.configurationRetryRepository.findByTopic(topic);
    }

}
