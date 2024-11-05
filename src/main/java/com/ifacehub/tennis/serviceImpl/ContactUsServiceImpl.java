package com.ifacehub.tennis.serviceImpl;

import com.ifacehub.tennis.entity.ContactUs;
import com.ifacehub.tennis.repository.ContactUsRepository;
import com.ifacehub.tennis.service.ContactUsService;
import com.ifacehub.tennis.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ContactUsServiceImpl implements ContactUsService {
    @Autowired
    private ContactUsRepository contactUsRepository;
    @Override
    public ResponseObject saveContact(ContactUs contactUs) {
        try {
            ContactUs contact = new ContactUs();
            contact.setName(contactUs.getName());
            contact.setEmail(contactUs.getEmail());
            contact.setSubject(contactUs.getSubject());
            contact.setMessage(contactUs.getMessage());
            contact.setCreatedOn(LocalDateTime.now());
            contact.setCreatedBy(1L);
            ContactUs saveContact = contactUsRepository.save(contact);
            return new ResponseObject(saveContact, "SUCCESS", HttpStatus.CREATED, "Contact saved successfully");
        } catch (Exception e) {
            return new ResponseObject(HttpStatus.BAD_REQUEST, "ERROR", "Failed to save contact: " + e.getMessage());
        }
    }
}
