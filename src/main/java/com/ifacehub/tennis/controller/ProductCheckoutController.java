package com.ifacehub.tennis.controller;

import com.ifacehub.tennis.requestDto.ProductRequestDto;
import com.ifacehub.tennis.responseDto.StripeResponseDto;
import com.ifacehub.tennis.service.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.StripeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/product/v1")
public class ProductCheckoutController {
    @Autowired
    private StripeService stripeService;

    @Value("${stripe.secretKey}")
    private String secretKey;

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponseDto> checkoutProducts(@RequestBody ProductRequestDto productRequest) {
        StripeResponseDto stripeResponse = stripeService.checkoutProducts(productRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(stripeResponse);
    }

     @PostMapping("/create-payment-intent")
     public Map<String, String> createPaymentIntent(@RequestBody Map<String, Object> data) {
       // Stripe.apiKey = "pk_test_51QD6hCBRiCyZIwhRt6NQtUgAP3sjs1SfTNBUVH5EffpHsUXAFRtC8NbQRhMgQ10wUiU80WqBUKAaep882BgqX3ok00Ss2CxnzJ";  // Replace with your Stripe Secret Key
         Stripe.apiKey = secretKey;

         Map<String, Object> params = new HashMap<>();
         params.put("amount", data.get("amount"));  // Amount in cents
        params.put("currency", "usd");

        try {
             PaymentIntent intent = PaymentIntent.create(params);
            Map<String, String> responseData = new HashMap<>();
            responseData.put("clientSecret", intent.getClientSecret());
            return responseData;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Payment intent creation failed");
        }
     }
    // ProductCheckoutController.java - Update createPaymentIntent
//@PostMapping("/create-payment-intent")
//public Map<String, String> createPaymentIntent(@RequestBody Map<String, Object> data) {
//    Stripe.apiKey = secretKey;
//    
//    // ⭐ Add metadata for order tracking
//    Map<String, String> metadata = new HashMap<>();
//    metadata.put("orderId", data.get("orderId").toString());
//    
//    Map<String, Object> params = new HashMap<>();
//    params.put("amount", data.get("amount"));
//    params.put("currency", "usd");
//    params.put("metadata", metadata); // ⭐ Add metadata
//
//    try {
//        PaymentIntent intent = PaymentIntent.create(params);
//        Map<String, String> responseData = new HashMap<>();
//        responseData.put("clientSecret", intent.getClientSecret());
//        return responseData;
//    } catch (Exception e) {
//        throw new RuntimeException("Payment intent creation failed: " + e.getMessage());
//    }
//}
    
    ////////////////////////
    @GetMapping("/checkout/session/{sessionId}")
    public ResponseEntity<StripeResponseDto> getCheckoutSession(@PathVariable String sessionId) 
        throws StripeException {
        Session session = Session.retrieve(sessionId);
        return ResponseEntity.ok(StripeResponseDto.builder()
            .status(session.getPaymentStatus())
            .message("Session retrieved")
            .sessionId(session.getId())
            .sessionUrl(session.getUrl())
            .build());
    }
    ////////////
    
}
