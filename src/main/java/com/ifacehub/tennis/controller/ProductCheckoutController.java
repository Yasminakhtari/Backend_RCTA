package com.ifacehub.tennis.controller;

import com.ifacehub.tennis.requestDto.ProductRequestDto;
import com.ifacehub.tennis.responseDto.StripeResponseDto;
import com.ifacehub.tennis.service.StripeService;
import com.stripe.net.StripeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product/v1")
public class ProductCheckoutController {
    @Autowired
    private StripeService stripeService;

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponseDto> checkoutProducts(@RequestBody ProductRequestDto productRequest) {
        StripeResponseDto stripeResponse = stripeService.checkoutProducts(productRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(stripeResponse);
    }
}
