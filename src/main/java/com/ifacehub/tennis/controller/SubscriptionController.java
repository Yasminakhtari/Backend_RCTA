package com.ifacehub.tennis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifacehub.tennis.entity.Subscription;
import com.ifacehub.tennis.repository.SubscriptionRepository;

@RestController
@RequestMapping("/api/subscribe")
public class SubscriptionController {
	private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionController(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @PostMapping
    public ResponseEntity<?> saveSubscription(@RequestBody Subscription subscription) {
        try {
            Subscription saved = subscriptionRepository.save(subscription);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Invalid subscription format");
        }
    }
}
