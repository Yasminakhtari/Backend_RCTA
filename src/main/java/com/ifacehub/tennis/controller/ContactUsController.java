package com.ifacehub.tennis.controller;

import com.ifacehub.tennis.entity.ContactUs;
import com.ifacehub.tennis.service.ContactUsService;
import com.ifacehub.tennis.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ContactUsController {
    @Autowired
    private ContactUsService contactUsService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Spring Boot!";
    }

    @PostMapping("/saveContact")
    public ResponseEntity<ResponseObject> saveContact(@RequestBody ContactUs contactUs){
        ResponseObject response = contactUsService.saveContact(contactUs);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
