package com.ifacehub.tennis.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@CrossOrigin(origins = "http://localhost:3003")
@RestController
public class CaptchaController {

    @Value("${recaptcha.secret}")
    private String recaptchaSecret;

    private static final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    @PostMapping("/api/auth/verify-captcha")
    public ResponseEntity<?> verifyCaptcha(@RequestParam("token") String token) {
    	System.out.println("ooookkkkkkkkkkkkk");
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("secret", recaptchaSecret);
        params.put("response", token);

        ResponseEntity<Map> response = restTemplate.postForEntity(RECAPTCHA_VERIFY_URL, params, Map.class);
        Map<String, Object> responseBody = response.getBody();

        if (responseBody != null && (Boolean) responseBody.get("success")) {
            return ResponseEntity.ok("CAPTCHA verified");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CAPTCHA verification failed");
        }
    }
}
