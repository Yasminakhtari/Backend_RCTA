package com.ifacehub.tennis.controller;

import com.ifacehub.tennis.requestDto.OrderDto;
import com.ifacehub.tennis.service.OrderService;
import com.ifacehub.tennis.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
