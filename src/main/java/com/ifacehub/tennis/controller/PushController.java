package com.ifacehub.tennis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifacehub.tennis.requestDto.PushRequest;
import com.ifacehub.tennis.service.PushNotificationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/send-push")
public class PushController {
	private final PushNotificationService pushService;

    @Autowired
    public PushController(PushNotificationService pushService) {
        this.pushService = pushService;
    }

    @PostMapping
    public ResponseEntity<?> sendPushNotification(
        @Valid @RequestBody PushRequest request
    ) {
        try {
        	String frontendUrl = "https://frontendrctatennis.vercel.app/";
            pushService.sendPushToAllUsers(
                request.getTitle(),
                request.getMessage(),
                frontendUrl
            );
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error sending push notifications: " + e.getMessage());
        }
    }
}
