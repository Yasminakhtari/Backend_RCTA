package com.ifacehub.tennis.serviceImpl;

import com.ifacehub.tennis.entity.ContactUs;
import com.ifacehub.tennis.repository.ContactUsRepository;
import com.ifacehub.tennis.service.ContactUsService;
import com.ifacehub.tennis.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ContactUsServiceImpl implements ContactUsService {
    @Autowired
    private ContactUsRepository contactUsRepository;
    @Autowired
    private EmailService emailService;

    @Override
    public ResponseObject saveContact(ContactUs contactUs) {
        try {
            ContactUs contact = new ContactUs();
            contact.setCity(contactUs.getCity());
            contact.setState(contactUs.getState());
            contact.setCountry(contactUs.getCountry());
            contact.setPhoneNumber(contactUs.getPhoneNumber());
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

    @Override
    public ResponseObject updateContact(Long id, ContactUs contactUs) {
        try {
            ContactUs existingContact = contactUsRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Contact not found"));
            existingContact.setPhoneNumber(contactUs.getPhoneNumber());
            existingContact.setCity(contactUs.getCity());
            existingContact.setState(contactUs.getState());
            existingContact.setCountry(contactUs.getCountry());
            existingContact.setBitDeletedFlag(contactUs.getBitDeletedFlag());
            existingContact.setUpdatedOn(LocalDateTime.now());

            ContactUs savedRole = contactUsRepository.save(existingContact);
            return new ResponseObject(savedRole, "SUCCESS", HttpStatus.OK, "Contact address updated successfully");
        } catch (RuntimeException e) {
            return new ResponseObject(HttpStatus.NOT_FOUND, "ERROR", e.getMessage());
        } catch (Exception e) {
            return new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR", "Failed to update role: " + e.getMessage());
        }
    }

    @Override
    public ResponseObject getAllContact() {
        try {
            List<ContactUs> contactUsList = contactUsRepository.findAllActive();
            return new ResponseObject(contactUsList, "SUCCESS", HttpStatus.OK, "Contact Address fetched successfully");
        } catch (Exception e) {
            return new ResponseObject(null, "ERROR", HttpStatus.NOT_FOUND, "Contact Address not found");
        }
    }
}
