package com.ifacehub.tennis.controller;

import com.ifacehub.tennis.entity.ContactUs;
import com.ifacehub.tennis.requestDto.ContactUsDto;
import com.ifacehub.tennis.requestDto.RoleDto;
import com.ifacehub.tennis.service.ContactUsService;
import com.ifacehub.tennis.service.EmailService;
import com.ifacehub.tennis.util.ResponseObject;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "*")
public class ContactUsController {
    @Autowired
    private ContactUsService contactUsService;

    @Autowired
    private EmailService emailService;


    @GetMapping("/hello")
    public String hello() {
        return "Hello from Spring Boot!";
    }

    @PostMapping("/saveContact")
    public ResponseEntity<ResponseObject> saveContact(@RequestBody ContactUs contactUs){
        ResponseObject response = contactUsService.saveContact(contactUs);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getContactById(@PathVariable  Long id){
        ResponseObject responseObject = contactUsService.getContactById(id);
        return new ResponseEntity<>(responseObject,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateContact(@PathVariable Long id, @RequestBody ContactUs contactUs) {
        ResponseObject response = contactUsService.updateContact(id, contactUs);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getAllRole() {
        ResponseObject response = contactUsService.getAllContact();
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    //Send email
    @PostMapping
    public ResponseEntity<ResponseObject> sendContactForm(@RequestBody ContactUsDto contactUsDto) throws MessagingException {
        ResponseObject response = emailService.sendContactUsEmail(contactUsDto);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
