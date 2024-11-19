package com.ifacehub.tennis.serviceImpl;

import com.ifacehub.tennis.entity.ContactUs;
import com.ifacehub.tennis.entity.User;
import com.ifacehub.tennis.repository.ContactUsRepository;
import com.ifacehub.tennis.service.ContactUsService;
import com.ifacehub.tennis.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ContactUsServiceImpl implements ContactUsService {
    @Autowired
    private ContactUsRepository contactUsRepository;
    @Override
    public ResponseObject saveContact(ContactUs contactUs) {
        try {
            Optional<ContactUs> findDuplicate = contactUsRepository.findByEmailAndBitDeletedFlag(contactUs.getEmail(),(byte) 0);
            if(findDuplicate.isPresent()){
                return new ResponseObject(null,"ERROR",HttpStatus.CONFLICT,"Email already exist");
            }
            ContactUs contact = new ContactUs();
            contact.setName(contactUs.getName());
            contact.setEmail(contactUs.getEmail());
            contact.setSubject(contactUs.getSubject());
            contact.setMessage(contactUs.getMessage());
            contact.setCreatedOn(LocalDateTime.now());
            contact.setCreatedBy(1L);
            contact.setBitDeletedFlag((byte)0);
            ContactUs saveContact = contactUsRepository.save(contact);
            return new ResponseObject(saveContact, "SUCCESS", HttpStatus.CREATED, "Contact saved successfully");
        } catch (Exception e) {
            return new ResponseObject(HttpStatus.BAD_REQUEST, "ERROR", "Failed to save contact: " + e.getMessage());
        }
    }

    @Override
    public ResponseObject getContactById(Long id) {
        try {
            Optional<ContactUs> contactUsOptional = contactUsRepository.findByIdAndBitDeletedFlag(id, (byte) 0);

            if (contactUsOptional.isPresent()) {
                ContactUs contactUs = contactUsOptional.get();
                return new ResponseObject(contactUs, "SUCCESS", HttpStatus.OK, "Contact retrieved successfully");
            } else {
                return new ResponseObject(null, "ERROR", HttpStatus.NOT_FOUND, "Contact not found");
            }
        } catch (RuntimeException e) {
            return new ResponseObject(null, "ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while fetching the contact");
        } catch (Exception e) {
            return new ResponseObject(null, "ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        }
    }
}
