package com.picpay.credit.retrymanager.api.controller;

import java.util.concurrent.ExecutionException;

import com.picpay.credit.retrymanager.api.business.SendMessageBusiness;
import com.picpay.credit.retrymanager.api.dto.PocDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/retry-manager-api/send-message", produces = MediaType.APPLICATION_JSON_VALUE)
public class SendMessageController {

    @Autowired
    private SendMessageBusiness sendMessageBusiness;
    
    //TODO: This endpoint is for testing only. Should be removed for this project as well the other kafka settings

    @PostMapping(value = "/{bindingName}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PocDto> sendMessage(@PathVariable String bindingName, @RequestBody PocDto pocDto) throws InterruptedException, ExecutionException {
        this.sendMessageBusiness.sendMessage(bindingName, pocDto);

        return new ResponseEntity<>(pocDto, HttpStatus.CREATED);
    }

}
