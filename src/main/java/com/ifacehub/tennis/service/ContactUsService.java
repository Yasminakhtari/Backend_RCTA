package com.ifacehub.tennis.service;

import com.ifacehub.tennis.entity.ContactUs;
import com.ifacehub.tennis.util.ResponseObject;

public interface ContactUsService {

    ResponseObject saveContact(ContactUs contactUs);

    ResponseObject getContactById(Long id);

    ResponseObject updateContact(Long id, ContactUs contactUs);

    ResponseObject getAllContact();
}
