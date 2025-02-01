package com.ifacehub.tennis.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.ifacehub.tennis.requestDto.OrderDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Entity
@Data
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Double total;
    private String paymentMethod;
    private String paymentStatus;
    @Column(columnDefinition = "TEXT")
    private String items; // Store as JSON string


    private static final ObjectMapper objectMapper = new ObjectMapper();  // ObjectMapper to handle JSON

    // Convert DTO to Entity
    public static Order toEntity(OrderDto orderDto) {
        Order order = new Order();
        order.setUserId(orderDto.getUserId());
        order.setTotal(orderDto.getTotal());
        order.setPaymentMethod(orderDto.getPaymentMethod());
        order.setPaymentStatus(orderDto.getPaymentStatus());

        // Convert List<Map<String, Object>> to JSON string
        try {
            String itemsJson = objectMapper.writeValueAsString(orderDto.getItems());
            order.setItems(itemsJson);  // Store as JSON string
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return order;
    }

    // Get items as List<Map<String, Object>> from the JSON string
    public List<Map<String, Object>> getItemsList() {
        try {
            if (this.items != null) {
                // Deserialize JSON string back to List<Map<String, Object>>
                return objectMapper.readValue(this.items, TypeFactory.defaultInstance().constructCollectionType(List.class, Map.class));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();  // Log the error
        }
        return Collections.emptyList();  // Return empty list if JSON is invalid
    }

//    public static Order toEntity(OrderDto orderDto) {
//        return Utils.mapper().convertValue(orderDto, Order.class);
//    }
}
