package com.picpay.credit.retrymanager.scheduler.scheduler;

import java.util.List;
import java.util.Map;

import com.picpay.credit.retrymanager.common.model.MessageRetry;
import com.picpay.credit.retrymanager.common.scheduler.BaseScheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CreateAccountScheduler extends BaseScheduler {

    private static final String FEATURE_CREATE_ACCOUNT = "create-account";

    @Scheduled(fixedDelay = 60000)
    public void process() {
        log.info("Init process.");

        List<MessageRetry> messagesRetry = super.findMessagesRetry(FEATURE_CREATE_ACCOUNT);

        log.info("Find {} messsages to process", messagesRetry.size());

        super.process(messagesRetry);

        log.info("Finish process");
    }

    @Override
    protected Map<String, String> mapTopicBinding() {
        return Map.of(
            "business-partner_create-account", "createAccount-out-0",
            "business-partner_update-account", "updateAccount-out-0");
    }

    

}
