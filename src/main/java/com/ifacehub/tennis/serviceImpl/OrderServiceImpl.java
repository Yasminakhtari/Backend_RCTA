package com.ifacehub.tennis.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifacehub.tennis.entity.Order;
import com.ifacehub.tennis.repository.OrderRepository;
import com.ifacehub.tennis.repository.UserRepository;
import com.ifacehub.tennis.requestDto.OrderDto;
import com.ifacehub.tennis.service.OrderService;
import com.ifacehub.tennis.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import java.util.Map;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseObject saveOrder(OrderDto orderDto) {
//        try {
//            Order order = Order.toEntity(orderDto);
////            String itemsJson = objectMapper.writeValueAsString(orderDto.getItems());
////            order.setItems(itemsJson);
//            order.setPaymentStatus("Pending");
//            Order savedOrder = orderRepository.save(order);
//            return new ResponseObject(savedOrder, "SUCCESS", HttpStatus.OK, "Order saved successfully");
//        }
//        catch (Exception e){
//            return new ResponseObject(HttpStatus.BAD_REQUEST, "ERROR", "Failed to saved order: " + e.getMessage());
//        }
        try {
            Order order;
            // If orderId is provided, find the order and update status to "Success"
            if (orderDto.getOrderId() != null) {
                Optional<Order> existingOrderOpt = orderRepository.findById(orderDto.getOrderId());

                if (existingOrderOpt.isPresent()) {
                    order = existingOrderOpt.get();
                    order.setPaymentStatus("Success"); // Update status to "Success"
                    orderRepository.save(order);
                    return new ResponseObject(order, "SUCCESS", HttpStatus.OK, "Order updated to Success");
                } else {
                    return new ResponseObject(HttpStatus.BAD_REQUEST, "ERROR", "Order not found for the given orderId");
                }
            }

            // Check if the order with the same userId exists and has 'Pending' status
            List<Order> existingOrders = orderRepository.findByUserId(orderDto.getUserId());


            // 🔹 Step 3: If no existing order, create a new one
            if (existingOrders.isEmpty()) {
                order = Order.toEntity(orderDto);
                order.setPaymentStatus("Pending"); // Default payment status
                order.setItems(orderDto.getItems().toString()); // Store items
                order.setCreatedOn(LocalDateTime.now());
                order.setUpdatedBy(String.valueOf(orderDto.getUserId()));
            } else {
                // 🔹 Step 4: Try to find an existing 'Pending' order
                order = existingOrders.stream()
                        .filter(o -> "Pending".equals(o.getPaymentStatus()))
                        .findFirst()
                        .orElse(null);

                if (order != null) {
                    // ✅ Found a Pending order, update it
                    List<Map<String, Object>> existingItems = order.getItemsList();
                    List<Map<String, Object>> newItems = orderDto.getItems();

                    // 🔹 Check for item differences
                    List<Map<String, Object>> updatedItems = compareItems(existingItems, newItems);

                    // 🔹 Update order details
                    order.setItems(updatedItems.toString());
                    order.setPaymentMethod(orderDto.getPaymentMethod());
                    order.setTotal(orderDto.getTotal());
                    order.setUpdatedOn(LocalDateTime.now());
                    order.setUpdatedBy(String.valueOf(orderDto.getUserId()));
                } else {
                    // 🔹 Step 5: No 'Pending' order found, check for 'Success' orders
                    order = existingOrders.get(0);

                    if ("Success".equals(order.getPaymentStatus())) {
                        // ✅ If all existing orders are 'Success', create a new one
                        order = Order.toEntity(orderDto);
                        order.setPaymentStatus("Pending"); // New order starts as 'Pending'
                        order.setItems(orderDto.getItems().toString());
                        order.setCreatedOn(LocalDateTime.now());
                        order.setCreatedBy(String.valueOf(orderDto.getUserId()));
                    }
                }
            }

            // Save the updated or new order
            Order savedOrder = orderRepository.save(order);

            return new ResponseObject(savedOrder, "SUCCESS", HttpStatus.OK, "Order saved successfully");
        } catch (Exception e) {
            return new ResponseObject(HttpStatus.BAD_REQUEST, "ERROR", "Failed to save order: " + e.getMessage());
        }
    }

    @Override
    public ResponseObject getAllOrder() {
        try {
            List<Order> orderList = orderRepository.findAllByOrderByIdDesc();
            orderList.stream().forEach(order -> {
                Long userId = order.getUserId();
                userRepository.findById(userId)
                        .ifPresent(orders -> {
                            String fullName = orders.getFirstName() + " " + orders.getLastName(); // Assuming firstName and lastName exist
                            order.setUserName(fullName);
                            order.setMobileNo(orders.getMobileNo());
                        });
            });

            return new ResponseObject(orderList, "SUCCESS", HttpStatus.OK, "Location fetched successfully");
        } catch (Exception e) {
            return new ResponseObject(null, "ERROR", HttpStatus.NOT_FOUND, "Location not found");
        }
    }

    private List<Map<String, Object>> compareItems(List<Map<String, Object>> existingItems, List<Map<String, Object>> newItems) {
        // Add logic to compare existing and new items, add, remove, or update items
        List<Map<String, Object>> updatedItems = new ArrayList<>(existingItems);

        // Remove items that are not in the new list
        updatedItems.removeIf(item -> !newItems.contains(item));

        // Add new items that were not in the existing list
        newItems.forEach(item -> {
            if (!updatedItems.contains(item)) {
                updatedItems.add(item);
            }
        });

        return updatedItems;
    }
}
