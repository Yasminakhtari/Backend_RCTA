package com.ifacehub.tennis.controller;

import com.ifacehub.tennis.requestDto.ProductRequestDto;
import com.ifacehub.tennis.responseDto.StripeResponseDto;
import com.ifacehub.tennis.service.StripeService;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.net.StripeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
