package com.ifacehub.tennis.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifacehub.tennis.entity.Order;
import com.ifacehub.tennis.repository.OrderRepository;
import com.ifacehub.tennis.requestDto.OrderDto;
import com.ifacehub.tennis.service.OrderService;
import com.ifacehub.tennis.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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


            if (existingOrders.isEmpty()) {
                // No existing order found, create a new one
                order = Order.toEntity(orderDto);
                order.setPaymentStatus("Pending"); // Default payment status is 'Pending'
                order.setItems(orderDto.getItems().toString()); // Directly set the items list
            } else {
                // Existing order found, check payment status and update items
                order = existingOrders.get(0); // Use the first found order

                // If payment status is still 'Pending', update the order
                if ("Pending".equals(order.getPaymentStatus())) {
                    // Compare the existing items with the new items
                    List<Map<String, Object>> existingItems = order.getItemsList();
                    List<Map<String, Object>> newItems = orderDto.getItems();

                    // Check for item differences (items added/removed/updated)
                    List<Map<String, Object>> updatedItems = compareItems(existingItems, newItems);

                    // Update the items list and payment details
                    order.setItems(updatedItems.toString());  // Directly set updated items list
                    order.setPaymentMethod(orderDto.getPaymentMethod());
                    order.setTotal(orderDto.getTotal());
                    order.setPaymentStatus("Pending"); // Mark as 'Success'
                }
                else if ("Success".equals(order.getPaymentStatus())) {
                    // If order is already 'Success', create a new order
                    order = Order.toEntity(orderDto);
                    order.setPaymentStatus("Pending"); // New order starts with 'Pending'
                    order.setItems(orderDto.getItems().toString()); // Set new items
                }
//                else {
//                    return new ResponseObject(HttpStatus.BAD_REQUEST, "ERROR", "Order is already processed or paid");
//                }
            }

            // Save the updated or new order
            Order savedOrder = orderRepository.save(order);

            return new ResponseObject(savedOrder, "SUCCESS", HttpStatus.OK, "Order saved successfully");
        } catch (Exception e) {
            return new ResponseObject(HttpStatus.BAD_REQUEST, "ERROR", "Failed to save order: " + e.getMessage());
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
