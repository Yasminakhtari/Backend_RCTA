package com.ifacehub.tennis.service;

import com.ifacehub.tennis.entity.Testimonial;
import com.ifacehub.tennis.util.ResponseObject;

public interface TestimonialService {

    ResponseObject createTestimonial(Testimonial testimonial);

    ResponseObject getTestimonialById(Long id);

    ResponseObject updateTestimonial(Long id, Testimonial testimonial);

    ResponseObject deleteTestimonial(Long id);

    ResponseObject getAllTestimonial();
}
