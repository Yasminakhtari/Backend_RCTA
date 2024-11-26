package com.ifacehub.tennis.service;

import com.ifacehub.tennis.requestDto.ContactUsDto;
import com.ifacehub.tennis.util.ResponseObject;
import jakarta.mail.MessagingException;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;
    @Value("${web3forms.api.url}")
    private String web3FormsApiUrl;

    @Value("${web3forms.api.key}")
    private String apiKey;


    public ResponseObject sendContactUsEmail(ContactUsDto contactUsDto) throws MessagingException {
        // Prepare the context for Thymeleaf
        Context context = new Context();
        context.setVariable("name", contactUsDto.getName());
        context.setVariable("email", contactUsDto.getEmail());
        context.setVariable("subject", contactUsDto.getSubject());
        context.setVariable("message", contactUsDto.getMessage());

        // Render the Thymeleaf template
        String renderedTemplate = templateEngine.process("email-template", context);
        String plainTextMessage = Jsoup.parse(renderedTemplate)
                .wholeText()
                .replaceAll("(?m)\\s*$", ""); // Trim trailing spaces from lines
        // Prepare the payload for Web3Forms
        Map<String, String> payload = new HashMap<>();
        payload.put("access_key", apiKey);
        payload.put("from_name", contactUsDto.getName());
//        payload.put("from_email", contactUsDto.getEmail());
        payload.put("subject", contactUsDto.getSubject());
//        payload.put("html", renderedTemplate);
        payload.put("", plainTextMessage); // Optionally include plain text

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(web3FormsApiUrl, payload,String.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Failed to send email via Web3Forms");
            }
            return new ResponseObject(null, "SUCCESS", HttpStatus.OK, "Contact form successfully sent");

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseObject(HttpStatus.BAD_REQUEST, "ERROR","Failed to sent email: " + e.getMessage());

        }
    }


}




