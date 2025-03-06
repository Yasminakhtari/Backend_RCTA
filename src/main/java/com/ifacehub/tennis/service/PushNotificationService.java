package com.ifacehub.tennis.service;

import com.google.gson.Gson;
import com.ifacehub.tennis.entity.Subscription;
import com.ifacehub.tennis.repository.SubscriptionRepository;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PushNotificationService {

    private final PushService pushService;
    private final SubscriptionRepository subscriptionRepository;

    // Initialize BouncyCastle once
//    static {
//        Security.addProvider(new BouncyCastleProvider());
//    }

    @Autowired
    public PushNotificationService(PushService pushService, 
                                  SubscriptionRepository subscriptionRepository) {
        this.pushService = pushService;
        this.subscriptionRepository = subscriptionRepository;
    }

    public void sendPushToAllUsers(String title, String message, String url) {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        
        subscriptions.forEach(subscription -> {
            try {
                Map<String, String> payload = new HashMap<>();
                payload.put("title", title);
                payload.put("body", message);
                payload.put("url", url);
                String jsonPayload = new Gson().toJson(payload);

                Notification notification = new Notification(
                    subscription.getEndpoint(),
                    subscription.getKeys().getP256dh(),
                    subscription.getKeys().getAuth(),
                    jsonPayload.getBytes(StandardCharsets.UTF_8),
                    3600 // TTL (1 hour)
                );

                HttpResponse response = pushService.send(notification);
                handleResponseStatus(subscription, response);
            } catch (Exception e) {
                handleFailedSubscription(subscription, e);
            }
        });
    }

    private void handleResponseStatus(Subscription subscription, HttpResponse response) {
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 410) { // 410 Gone
            subscriptionRepository.delete(subscription);
        }
    }

    private void handleFailedSubscription(Subscription subscription, Exception e) {
        if (e instanceof HttpException) {
        	if (e.getMessage().contains("410")) {  
        	    subscriptionRepository.delete(subscription);
        	}

        }
        
    }
}