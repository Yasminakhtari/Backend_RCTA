package com.ifacehub.tennis.service;

import com.ifacehub.tennis.requestDto.OrderDto;
import com.ifacehub.tennis.requestDto.OrderRequestDto;
import com.ifacehub.tennis.util.ResponseObject;

public interface OrderService {
    ResponseObject saveOrder(OrderDto orderDto);

    ResponseObject getAllOrder();
    
    ResponseObject saveShippingAddress(OrderRequestDto shippingDto);
    
    ResponseObject getShippingAddressByOrderId(Long orderId);
    ResponseObject getShippingAddressByUserId(Long userId);
    ResponseObject updateOrderStatus(Long orderId, String paymentStatus);

}
