package com.ifacehub.tennis.requestDto;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class OrderDto {
    private Long userId;
    private Double total;
    private String paymentMethod;
    private String paymentStatus;
    List<Map<String, Object>> items;
    private Long orderId;
}
