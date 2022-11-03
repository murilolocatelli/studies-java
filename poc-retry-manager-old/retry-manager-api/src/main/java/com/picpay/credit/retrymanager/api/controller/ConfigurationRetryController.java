package com.picpay.credit.retrymanager.api.controller;

import com.picpay.credit.retrymanager.common.business.ConfigurationRetryBusiness;
import com.picpay.credit.retrymanager.common.model.ConfigurationRetry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/retry-manager-api/configuration-retry", produces = MediaType.APPLICATION_JSON_VALUE)
public class ConfigurationRetryController {

    @Autowired
    private ConfigurationRetryBusiness configurationRetryBusiness;
    
    //TODO: Implement other endpoints to configure retries (patch, delete, etc)
    //TODO: Validate payloads
    //TODO: Validate initialIntervalSeconds. Must be greater than or equal to 1 minute

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConfigurationRetry> createConfigurationRetry(@RequestBody ConfigurationRetry configurationRetry) {
        
        return new ResponseEntity<>(
            this.configurationRetryBusiness.create(configurationRetry), HttpStatus.CREATED);
    }

}
