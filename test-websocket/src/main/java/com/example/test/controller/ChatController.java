package com.example.test.controller;

import com.example.test.dto.Connected;
import com.example.test.dto.Message;
import com.example.test.dto.OutputMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private Set<Connected> connectedSet = new HashSet<>();

    @MessageMapping("/message")
    @SendTo("/topic/message")
    public OutputMessage message(Message message) {
        System.out.println("Thread: " + Thread.currentThread().getName() + " Message = " + message);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String time = LocalDateTime.now().format(dateTimeFormatter);

        return new OutputMessage(message.getFrom(), message.getText(), time);
    }

    @MessageMapping("/messageTo")
    public void messageTo(Message message, Principal user) {
        System.out.println("Thread: " + Thread.currentThread().getName() + " Private Message = " + message);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String time = LocalDateTime.now().format(dateTimeFormatter);

        this.simpMessagingTemplate.convertAndSend(
                "/queue/message/" + message.getTo() ,
                new OutputMessage(message.getFrom(), "Private Message: " + message.getText(), time));
    }

    @MessageMapping("/connect")
    @SendTo("/topic/connected")
    public Set<Connected> connected(Connected connected) {
        System.out.println("Thread: " + Thread.currentThread().getName() + " Connected = " + connected);

        connectedSet.add(connected);

        return connectedSet;
    }

    @MessageMapping("/disconnect")
    @SendTo("/topic/connected")
    public Set<Connected> disconnect(Connected connected) {
        System.out.println("Thread: " + Thread.currentThread().getName() + " Connected = " + connected);

        connectedSet.remove(connected);

        return connectedSet;
    }

}
