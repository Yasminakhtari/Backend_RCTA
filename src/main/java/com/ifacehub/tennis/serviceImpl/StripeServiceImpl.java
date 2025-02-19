package com.ifacehub.tennis.serviceImpl;

import com.ifacehub.tennis.requestDto.ProductRequestDto;
import com.ifacehub.tennis.responseDto.StripeResponseDto;
import com.ifacehub.tennis.service.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeServiceImpl implements StripeService {

    @Value("${stripe.secretKey}")
    private String secretKey;
    @Override
    public StripeResponseDto checkoutProducts(ProductRequestDto productRequest) {
        Stripe.apiKey = secretKey;
        System.out.println("Stripe Secret Key: " + secretKey);

        // Create the product data
        SessionCreateParams.LineItem.PriceData.ProductData productData =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName(productRequest.getName())
                        .build();

        // Create the price data
        SessionCreateParams.LineItem.PriceData priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency(productRequest.getCurrency() != null ? productRequest.getCurrency() : "USD")
                        .setUnitAmount(productRequest.getAmount())
                        .setProductData(productData)
                        .build();

        // Create the line item
        SessionCreateParams.LineItem lineItem =
                SessionCreateParams.LineItem.builder()
                        .setQuantity(productRequest.getQuantity())
                        .setPriceData(priceData)
                        .build();

        // Create the checkout session parameters
        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl("http://localhost:8083/success")
                        .setCancelUrl("http://localhost:8083/cancel")
                        .addLineItem(lineItem)
                        .build();

        System.out.println("Session Params: " + params.toString());

        // Create the session
        Session session = null;
        try {
            session = Session.create(params);
            System.out.println("Session Created: " + session.getId());
        } catch (StripeException e) {
            System.err.println("Stripe Exception: " + e.getMessage());
            e.printStackTrace();
        }

        if (session == null) {
            return StripeResponseDto.builder()
                    .status("FAILED")
                    .message("Session creation failed")
                    .build();
        }

        return StripeResponseDto.builder()
                .status("SUCCESS")
                .message("Payment session created")
                .sessionId(session.getId())
                .sessionUrl(session.getUrl())
                .build();
    }
}

