package com.ifacehub.tennis.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifacehub.tennis.entity.Order;
import com.ifacehub.tennis.entity.ShippingAddress;
import com.ifacehub.tennis.exception.ResourceNotFoundException;
import com.ifacehub.tennis.repository.OrderRepository;
import com.ifacehub.tennis.repository.ShippingAddressRepository;
import com.ifacehub.tennis.repository.UserRepository;
import com.ifacehub.tennis.requestDto.OrderDto;
import com.ifacehub.tennis.requestDto.OrderRequestDto;
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
    
    @Autowired
    private ShippingAddressRepository shippingAddressRepository;

    @Override
    public ResponseObject saveOrder(OrderDto orderDto) {

        try {
        	Order order = Order.toEntity(orderDto);//24-02
            order.setPaymentStatus("Pending");//24-02
            ObjectMapper objectMapper = new ObjectMapper();
//            Order order;
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
                String itemsJson = objectMapper.writeValueAsString(orderDto.getItems());
                order.setItems(itemsJson);
//                order.setItems(orderDto.getItems().toString()); // Store items
                order.setCreatedOn(LocalDateTime.now());
                order.setUpdatedBy(String.valueOf(orderDto.getUserId()));
            } else {
                // 🔹 Step 4: Try to find an existing 'Pending' order
                order = existingOrders.stream()
                        .filter(o -> "Pending".equals(o.getPaymentStatus()))
                        .findFirst()
                        .orElse(null);

                if (order != null) {
                    //Found a Pending order, update it
                    List<Map<String, Object>> existingItems = order.getItemsList();
                    List<Map<String, Object>> newItems = orderDto.getItems();

                    // 🔹 Check for item differences
                    List<Map<String, Object>> updatedItems = compareItems(existingItems, newItems);

                    // 🔹 Update order details
                    order.setItems(objectMapper.writeValueAsString(updatedItems));
                    order.setPaymentMethod(orderDto.getPaymentMethod());
                    order.setTotal(orderDto.getTotal());
                    order.setUpdatedOn(LocalDateTime.now());
                    order.setUpdatedBy(String.valueOf(orderDto.getUserId()));
                } else {
                    // 🔹 Step 5: No 'Pending' order found, check for 'Success' orders
                    order = existingOrders.get(0);

                    if ("Success".equals(order.getPaymentStatus())) {
                        //  If all existing orders are 'Success', create a new one
                        order = Order.toEntity(orderDto);
                        order.setPaymentStatus("Pending"); // New order starts as 'Pending'
//                        order.setItems(orderDto.getItems().toString());
                        order.setItems(objectMapper.writeValueAsString(orderDto.getItems()));
                        order.setCreatedOn(LocalDateTime.now());
                        order.setCreatedBy(String.valueOf(orderDto.getUserId()));
                    }
                }
            }

            // Save the updated or new order
            Order savedOrder = orderRepository.save(order);

            return new ResponseObject(savedOrder, "SUCCESS", HttpStatus.OK, "Order created successfully");
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

	@Override
	public ResponseObject saveShippingAddress(OrderRequestDto shippingDto) {
	    try {
	    	if (shippingDto.getUserId() == null || shippingDto.getUserId() <= 0) {
	            return new ResponseObject(null, "ERROR", HttpStatus.BAD_REQUEST, "Valid User ID is required");
	        }
	        if (shippingDto.getOrderId() == null || shippingDto.getOrderId() <= 0) {
	            return new ResponseObject(null, "ERROR", HttpStatus.BAD_REQUEST, "Valid Order ID is required");
	        }
	     // Map DTO to Entity
	        ShippingAddress shippingAddress = new ShippingAddress();
	        shippingAddress.setUserId(shippingDto.getUserId());
	        shippingAddress.setOrderId(shippingDto.getOrderId());
	        shippingAddress.setName(shippingDto.getName());
	        shippingAddress.setEmail(shippingDto.getEmail().toLowerCase());
	        shippingAddress.setPhone(shippingDto.getPhone().replace("-", ""));
	        shippingAddress.setAddressLine1(shippingDto.getAddressLine1()); 
	        shippingAddress.setAddressLine2(shippingDto.getAddressLine2()); 
	        shippingAddress.setCity(shippingDto.getCity());
	        shippingAddress.setState(shippingDto.getState());
	        shippingAddress.setZipCode(shippingDto.getZipCode().replace("-", ""));

	       
	        ShippingAddress savedAddress = shippingAddressRepository.save(shippingAddress);
	        return new ResponseObject(savedAddress, "SUCCESS", HttpStatus.OK, "Shipping Address Saved Successfully");

	    } catch (Exception e) {
	        e.printStackTrace(); 
	        return new ResponseObject(null, "ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "Failed to save Shipping Address: " + e.getMessage());
	    }
	}
	
	
	@Override
	public ResponseObject getShippingAddressByOrderId(Long orderId) {
	    try {
	        if (orderId == null || orderId <= 0) {
	            return new ResponseObject(null,"ERROR", HttpStatus.BAD_REQUEST, "Valid Order ID required");
	        }
	        
	        List<ShippingAddress> addresses = shippingAddressRepository.findByOrderId(orderId);
	        if (addresses.isEmpty()) {
	            return new ResponseObject(null,"ERROR", HttpStatus.NOT_FOUND, "No shipping address found for order ID: " + orderId);
	        }
	        
	        return new ResponseObject(addresses, "SUCCESS", HttpStatus.OK,"Shipping Adress By Id fetch sucessfully");
	    } catch (Exception e) {
	        return new ResponseObject(null,"ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch address: " + e.getMessage());
	    }
	}

	@Override
	public ResponseObject getShippingAddressByUserId(Long userId) {
	    try {
	        if (userId == null || userId <= 0) {
	            return new ResponseObject(null,"ERROR", HttpStatus.BAD_REQUEST, "Valid User ID required");
	        }
	        
	        List<ShippingAddress> addresses = shippingAddressRepository.findByUserId(userId);
	        if (addresses.isEmpty()) {
	            return new ResponseObject(null,"ERROR", HttpStatus.NOT_FOUND, "No addresses found for user ID: " + userId);
	        }
	        
	        return new ResponseObject(addresses, "SUCCESS", HttpStatus.OK,"Get Shipping Adress by userId fetch sucessfully");
	    } catch (Exception e) {
	        return new ResponseObject(null,"ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch addresses: " + e.getMessage());
	    }
	}
	
	
//	///24-02
	@Override
	public ResponseObject updateOrderStatus(Long orderId, String paymentStatus) {
	    try {
	        Order order = orderRepository.findById(orderId)
	            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
	        
	        order.setPaymentStatus(paymentStatus);
	        Order updatedOrder = orderRepository.save(order);
	        
	        return new ResponseObject(
	            updatedOrder, 
	            "SUCCESS", 
	            HttpStatus.OK, 
	            "Order status updated"
	        );
	    } catch (ResourceNotFoundException e) {
	        return new ResponseObject(
	            HttpStatus.NOT_FOUND, 
	            "ERROR", 
	            e.getMessage()
	        );
	    } catch (Exception e) {
	        return new ResponseObject(
	            HttpStatus.INTERNAL_SERVER_ERROR, 
	            "ERROR", 
	            "Error updating order: " + e.getMessage()
	        );
	    }
	}

}
