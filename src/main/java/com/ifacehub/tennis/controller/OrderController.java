package com.ifacehub.tennis.controller;

import com.ifacehub.tennis.requestDto.OrderDto;
import com.ifacehub.tennis.requestDto.OrderRequestDto;
import com.ifacehub.tennis.service.OrderService;
import com.ifacehub.tennis.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    //Save
    @PostMapping("/save")
    public ResponseEntity<ResponseObject> saveOrder(@RequestBody OrderDto orderDto){
        ResponseObject response = orderService.saveOrder(orderDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAllOrder")
    public ResponseEntity<ResponseObject> getAllOrder() {
        ResponseObject response = orderService.getAllOrder();
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
    
    @PostMapping("/saveShippingAddress")
    public ResponseEntity<ResponseObject> saveShippingAddress(@RequestBody OrderRequestDto shippingDto) {
       ResponseObject response =  orderService.saveShippingAddress(shippingDto);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    
    @GetMapping("/getShippingAddressByOrderId/{orderId}")
    public ResponseEntity<ResponseObject> getShippingAddressByOrderId(@PathVariable Long orderId) {
        ResponseObject response = orderService.getShippingAddressByOrderId(orderId);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("/getShippingAddressByUserId/{userId}")
    public ResponseEntity<ResponseObject> getShippingAddressByUserId(@PathVariable Long userId) {
        ResponseObject response = orderService.getShippingAddressByUserId(userId);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
    
    
    
}	
