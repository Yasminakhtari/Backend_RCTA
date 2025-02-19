package com.ifacehub.tennis.service;

import com.ifacehub.tennis.requestDto.ProductRequestDto;
import com.ifacehub.tennis.responseDto.StripeResponseDto;

public interface StripeService {
    StripeResponseDto checkoutProducts(ProductRequestDto productRequest);
}
