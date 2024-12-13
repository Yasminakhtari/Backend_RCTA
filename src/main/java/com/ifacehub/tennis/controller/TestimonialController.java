package com.ifacehub.tennis.controller;

import com.ifacehub.tennis.entity.Testimonial;
import com.ifacehub.tennis.service.TestimonialService;
import com.ifacehub.tennis.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/testimonial")
@CrossOrigin(origins = "*")
public class TestimonialController {

    @Autowired
    private TestimonialService testimonialService;

    @PostMapping
    public ResponseEntity<ResponseObject> createTestimonial(@RequestBody Testimonial testimonial) {
        ResponseObject response = testimonialService.createTestimonial(testimonial);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getTestimonialById(@PathVariable Long id) {
        ResponseObject response = testimonialService.getTestimonialById(id);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateTestimonial(@PathVariable Long id, @RequestBody Testimonial testimonial) {
        ResponseObject response = testimonialService.updateTestimonial(id, testimonial);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteTestimonial(@PathVariable Long id) {
        ResponseObject response = testimonialService.deleteTestimonial(id);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
    @GetMapping
    public ResponseEntity<ResponseObject> getAllTestimonial() {
        ResponseObject response = testimonialService.getAllTestimonial();
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
