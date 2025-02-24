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

	@Value("${stripe.success.url}")
	private String frontendSuccessUrl;

	@Value("${stripe.cancel.url}")
	private String frontendCancelUrl;

	@Override
	public StripeResponseDto checkoutProducts(ProductRequestDto productRequest) {
//        Stripe.apiKey = secretKey;
//        System.out.println("Stripe Secret Key: " + secretKey);

		// Create the product data
//        SessionCreateParams.LineItem.PriceData.ProductData productData =
//                SessionCreateParams.LineItem.PriceData.ProductData.builder()
//                        .setName(productRequest.getName())
//                        .build();

		// Create the price data
//        SessionCreateParams.LineItem.PriceData priceData =
//                SessionCreateParams.LineItem.PriceData.builder()
//                        .setCurrency(productRequest.getCurrency() != null ? productRequest.getCurrency() : "USD")
//                        .setUnitAmount(productRequest.getAmount())
//                        .setProductData(productData)
//                        .build();

		// Create the line item
//        SessionCreateParams.LineItem lineItem =
//                SessionCreateParams.LineItem.builder()
//                        .setQuantity(productRequest.getQuantity())
//                        .setPriceData(priceData)
//                        .build();
//        
		// Create checkout session parameters with metadata and URLs
//        SessionCreateParams params =
//                SessionCreateParams.builder()
//                        .setMode(SessionCreateParams.Mode.PAYMENT)
//                        .setSuccessUrl(frontendSuccessUrl + "?session_id={CHECKOUT_SESSION_ID}")
//                        .setCancelUrl(frontendCancelUrl)
//                        .addLineItem(lineItem)
//                        .putMetadata("orderId", productRequest.getOrderId().toString())
//                        .build();

		// Create the checkout session parameters
//        SessionCreateParams params =
//                SessionCreateParams.builder()
//                        .setMode(SessionCreateParams.Mode.PAYMENT)
//                        .setSuccessUrl("http://localhost:8082/success")
//                        .setCancelUrl("http://localhost:8082/cancel")
//                        .addLineItem(lineItem)
//                        .build();

		// System.out.println("Session Params: " + params.toString());

		// 5. Create Stripe session
//        Session session = Session.create(params);//24-02
//        

		// Create the session
//        Session session = null;
//        try {
//            session = Session.create(params);
//            System.out.println("Session Created: " + session.getId());
//        } catch (StripeException e) {
//            System.err.println("Stripe Exception: " + e.getMessage());
//            e.printStackTrace();
//        }
//
//        if (session == null) {
//            return StripeResponseDto.builder()
//                    .status("FAILED")
//                    .message("Session creation failed")
//                    .build();
//        }
//
//        return StripeResponseDto.builder()
//        		.paymentStatus(session.getPaymentStatus())
//                .status("SUCCESS")
//                .message("Payment session created")
//                .sessionId(session.getId())
//                .sessionUrl(session.getUrl())
//                .build();
		Stripe.apiKey = secretKey;

		try {
			
			if (productRequest.getOrderId() == null) {
	            throw new IllegalArgumentException("Order ID is required");
	        }
			// 1. Create product data
			SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData
					.builder().setName(productRequest.getName()).build();

			// 2. Create price data
			SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
					.setCurrency(productRequest.getCurrency() != null ? productRequest.getCurrency() : "USD")
					.setUnitAmount(productRequest.getAmount()).setProductData(productData).build();

			// 3. Create line item
			SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
					.setQuantity(productRequest.getQuantity()).setPriceData(priceData).build();

			// 4. Build session parameters
			SessionCreateParams params = SessionCreateParams.builder().setMode(SessionCreateParams.Mode.PAYMENT)
					.setSuccessUrl(frontendSuccessUrl + "?session_id={CHECKOUT_SESSION_ID}")
					.setCancelUrl(frontendCancelUrl).addLineItem(lineItem)
					.putMetadata("orderId", productRequest.getOrderId().toString()).build();

			// 5. Create Stripe session
			Session session = Session.create(params);

			// 6. Return success response
			return StripeResponseDto.builder().paymentStatus(session.getPaymentStatus()).status("SUCCESS")
					.message("Payment session created").sessionId(session.getId()).sessionUrl(session.getUrl()).build();

		} catch (StripeException e) {
			// 7. Handle Stripe errors
			System.err.println("Stripe Error: " + e.getMessage());
			return StripeResponseDto.builder().status("FAILED").paymentStatus("FAILED")
					.message("Payment failed: " + e.getMessage()).build();
		} catch (Exception e) {
			// 8. Handle generic errors
			System.err.println("System Error: " + e.getMessage());
			return StripeResponseDto.builder().status("ERROR").paymentStatus("FAILED")
					.message("System error: " + e.getMessage()).build();
		}
	}
}
