package com.ifacehub.tennis.service;

import com.ifacehub.tennis.requestDto.OrderDto;
import com.ifacehub.tennis.util.ResponseObject;

public interface OrderService {
    ResponseObject saveOrder(OrderDto orderDto);
}
