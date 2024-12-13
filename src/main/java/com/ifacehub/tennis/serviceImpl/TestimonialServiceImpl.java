package com.ifacehub.tennis.serviceImpl;

import com.ifacehub.tennis.entity.Testimonial;
import com.ifacehub.tennis.repository.TestimonialRepository;
import com.ifacehub.tennis.service.TestimonialService;
import com.ifacehub.tennis.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TestimonialServiceImpl implements TestimonialService {

    @Autowired
    private TestimonialRepository testimonialRepository;

    @Override
    public ResponseObject createTestimonial(Testimonial testimonial) {
        try {
            testimonial.setBitDeletedFlag((byte) 0);
            testimonial.setCreatedBy(String.valueOf(1L));
            testimonial.setCreatedOn(LocalDateTime.now());
            Testimonial savedTestimonial = testimonialRepository.save(testimonial);
            return new ResponseObject(savedTestimonial, "SUCCESS", HttpStatus.CREATED, "Testimonial created successfully");
        } catch (Exception e) {
            return new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR", "Failed to create testimonial: " + e.getMessage());
        }
    }

    @Override
    public ResponseObject getTestimonialById(Long id) {
        try {
            Testimonial testimonial = testimonialRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Testimonial not found"));
            return new ResponseObject(testimonial, "SUCCESS", HttpStatus.OK, "Testimonial found");
        } catch (RuntimeException e) {
            return new ResponseObject(HttpStatus.NOT_FOUND, "ERROR", e.getMessage());
        } catch (Exception e) {
            return new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR", "Failed to retrieve testimonial: " + e.getMessage());
        }
    }

    @Override
    public ResponseObject updateTestimonial(Long id, Testimonial testimonial) {
        try {
            Testimonial existingTestimonial = testimonialRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Testimonial not found"));

            existingTestimonial.setName(testimonial.getName());
            existingTestimonial.setMessage(testimonial.getMessage());
            existingTestimonial.setRating(testimonial.getRating());
            existingTestimonial.setBitDeletedFlag(testimonial.getBitDeletedFlag());
            existingTestimonial.setUpdatedOn(LocalDateTime.now());
            existingTestimonial.setUpdatedBy("1");

            Testimonial savedTestimonial = testimonialRepository.save(existingTestimonial);
            return new ResponseObject(savedTestimonial, "SUCCESS", HttpStatus.OK, "Testimonial updated successfully");
        } catch (RuntimeException e) {
            return new ResponseObject(HttpStatus.NOT_FOUND, "ERROR", e.getMessage());
        } catch (Exception e) {
            return new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR", "Failed to update testimonial: " + e.getMessage());
        }
    }

    @Override
    public ResponseObject deleteTestimonial(Long id) {
        try {
            Optional<Testimonial> testimonialOptional = testimonialRepository.findByIdAndBitDeletedFlag(id, (byte) 0);

            if (testimonialOptional.isPresent()) {
                Testimonial testimonial = testimonialOptional.get();
                testimonial.setBitDeletedFlag((byte) 1);
                testimonialRepository.save(testimonial); // Save the updated entity

                return new ResponseObject(testimonial, "SUCCESS", HttpStatus.OK, "Testimonial deleted successfully");
            } else {
                return new ResponseObject(null, "ERROR", HttpStatus.NOT_FOUND, "Testimonial not found");
            }
        } catch (RuntimeException e) {
            return new ResponseObject(null, "ERROR", HttpStatus.NOT_FOUND, "Testimonial not found");
        } catch (Exception e) {
            return new ResponseObject(null, "ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while deleting the testimonial");
        }
    }

    @Override
    public ResponseObject getAllTestimonial() {
        try {
            List<Testimonial> testimonialList = testimonialRepository.findAllActiveByRatingAndIdDesc();
            return new ResponseObject(testimonialList, "SUCCESS", HttpStatus.OK, "Testimonial fetched successfully");
        } catch (Exception e) {
            return new ResponseObject(null, "ERROR", HttpStatus.NOT_FOUND, "Testimonial not found");
        }
    }
}

