package com.ifacehub.tennis.config;

import nl.martijndwars.webpush.PushService;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.security.GeneralSecurityException;
import java.security.Security;

@Configuration
public class WebPushConfig {

    @Value("${vapid.public.key}")
    private String vapidPublicKey;

    @Value("${vapid.private.key}")
    private String vapidPrivateKey;
    
    static {
        // Register provider early in config
        Security.addProvider(new BouncyCastleProvider());
    }

    @Bean
    public PushService pushService() throws GeneralSecurityException {
        // Explicitly specify provider for key conversion
        Security.addProvider(new BouncyCastleProvider());
        return new PushService(vapidPublicKey, vapidPrivateKey);
    }
}