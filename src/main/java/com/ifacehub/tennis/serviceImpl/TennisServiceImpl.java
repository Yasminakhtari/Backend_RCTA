package com.ifacehub.tennis.serviceImpl;

import com.ifacehub.tennis.entity.Tennis;
import com.ifacehub.tennis.repository.TennisRepository;
import com.ifacehub.tennis.service.TennisService;
import com.ifacehub.tennis.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;

@Service
public class TennisServiceImpl implements TennisService {

    @Autowired
    private FileService fileService;
    @Autowired
    private TennisRepository tennisRepository;


    @Override
    public ResponseObject createTennisService(Tennis tennis) {
        try {
//            if (file != null && !file.isEmpty()) {
//                String fileName = fileService.saveFile(file);
//                tennis.setImgUrl(fileName); // Save file name in the database
//            }
            Tennis savedTennis= tennisRepository.save(tennis);
            return new ResponseObject(savedTennis, "SUCCESS", HttpStatus.CREATED, "Tennis Service created successfully");
        } catch (Exception e) {
            return new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR", "Failed to create tennis service: " + e.getMessage());
        }
    }

    @Override
    public List<Tennis> getAllTennis() {
        return tennisRepository.findAll();
    }

    @Override
    public List<Tennis> getAllByStatus(String status) {
        return tennisRepository.findAllByStatus(status);
    }

    @Override
    public ResponseObject updateTennis(Long id,Tennis tennis) {
        if (tennis == null) {
            return new ResponseObject(HttpStatus.BAD_REQUEST, "ERROR", "Tennis object is invalid.");
        }

        try {
            // Fetch the existing entity by ID
            Tennis existingTennis = tennisRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Tennis entity with ID " + id + " not found."));

            // Update fields only if they are not null
            updateFields(existingTennis, tennis);

            // Save the updated entity
            Tennis updatedTennis = tennisRepository.save(existingTennis);

            // Return success response
            return new ResponseObject(updatedTennis, "SUCCESS", HttpStatus.OK, "Data updated successfully");
        } catch (IllegalArgumentException e) {
            // Handle specific exception for entity not found
            return new ResponseObject("", "ERROR", HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            // Handle any unexpected exceptions
            return new ResponseObject("", "ERROR", HttpStatus.INTERNAL_SERVER_ERROR,
                    "An unexpected error occurred while updating the Tennis.");
        }
//        if (tennis == null || tennis.getId() == null) {
//            return new ResponseObject(HttpStatus.BAD_REQUEST,"ERROR","Tennis object or ID is invalid.");
//        }
//        try {
//            // Fetch the existing entity
//            Tennis existingTennis = tennisRepository.findById(tennis.getId())
//                    .orElseThrow(() -> new IllegalArgumentException(
//                            "Tennis entity with ID " + tennis.getId() + " not found."));
//
//            // Update the fields only if they are not null
//            updateFields(existingTennis, tennis);
//
//            // Save the updated entity
//            tennisRepository.save(existingTennis);
//            return new ResponseObject(tennis,"SUCCESS",HttpStatus.OK, "Data updated successfully");
//        } catch (IllegalArgumentException e) {
////            return ResponseEntity.status(404).body(new ApiResponse("Error: Ivblu not found. Please check the ID."));
//            return new ResponseObject("","ERROR",HttpStatus.BAD_REQUEST, "Tennis not found. Please check the ID.");
//        } catch (Exception e) {
//            return new ResponseObject("","ERROR",HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred while updating the Tennis.");
//        }
    }

    @Override
    public ResponseObject toggleStatus(Long id, String status) {
        Optional<Tennis> tennisOptional = tennisRepository.findById(id);

        if (tennisOptional.isPresent()) {
            Tennis tennis = tennisOptional.get();
            tennis.setStatus(status); // Update the status
            Tennis saveTennis = tennisRepository.save(tennis);// Save the updated entity

//            return ResponseEntity.ok(new ApiResponse("Service status updated successfully"));
            return new ResponseObject(saveTennis,"SUCCESS",HttpStatus.OK,"Service status updated successfully");
        } else {
            return  new ResponseObject("","ERROR",HttpStatus.INTERNAL_SERVER_ERROR,"Not found"); // Return 404 if the service is not found
        }
    }

    @Override
    public ResponseObject deleteTennis(Long id) {
        try {
            Optional<Tennis> tennisOptional = tennisRepository.findById(id);

            if (tennisOptional.isPresent()) {
                Tennis tennis = tennisOptional.get();
                tennisRepository.save(tennis); // Save the updated entity

                return new ResponseObject(tennis, "SUCCESS", HttpStatus.OK, "Data deleted successfully");
            } else {
                return new ResponseObject(null, "ERROR", HttpStatus.NOT_FOUND, "Data not found");
            }
        } catch (RuntimeException e) {
            return new ResponseObject(null, "ERROR", HttpStatus.NOT_FOUND, "Data not found");
        } catch (Exception e) {
            return new ResponseObject(null, "ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while deleting the data");
        }
    }

    @Override
    public List<Object[]> findAllCategoriesAndSubCategories() {
        return tennisRepository.findAllCategoryAndSubCategory();
    }

    @Override
    public ResponseObject getTennisById(Long id) {
        try {
            Optional<Tennis> tennisOptional = tennisRepository.findById(id);

            if (tennisOptional.isPresent()) {
                Tennis tennis = tennisOptional.get();
                return new ResponseObject(tennisOptional, "SUCCESS", HttpStatus.OK, "Data retrieved successfully");
            } else {
                return new ResponseObject(null, "ERROR", HttpStatus.NOT_FOUND, "Data not found");
            }
        } catch (RuntimeException e) {
            return new ResponseObject(null, "ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while fetching the Data");
        } catch (Exception e) {
            return new ResponseObject(null, "ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        }
    }

    private void updateFields(Tennis existingTennis, Tennis updatedTennis) {
        if (updatedTennis.getGroups() != null) {
            existingTennis.setGroups(updatedTennis.getGroups());
        }
        if (updatedTennis.getCategory() != null) {
            existingTennis.setCategory(updatedTennis.getCategory());
        }
        if (updatedTennis.getSubcategory() != null) {
            existingTennis.setSubcategory(updatedTennis.getSubcategory());
        }
        if (updatedTennis.getImgUrl() != null) {
            existingTennis.setImgUrl(updatedTennis.getImgUrl());
        }
        if (updatedTennis.getName() != null) {
            existingTennis.setName(updatedTennis.getName());
        }
        if (updatedTennis.getDescription() != null) {
            existingTennis.setDescription(updatedTennis.getDescription());
        }
        if (updatedTennis.getDuration() != null) {
            existingTennis.setDuration(updatedTennis.getDuration());
        }
        if (updatedTennis.getPrice() != null) {
            existingTennis.setPrice(updatedTennis.getPrice());
        }
        if (updatedTennis.getStatus() != null) {
            existingTennis.setStatus(updatedTennis.getStatus());
        }
        if (updatedTennis.getDiscount() != null) {
            existingTennis.setDiscount(updatedTennis.getDiscount());
        }
        if (updatedTennis.getDisbegindate() != null) {
            existingTennis.setDisbegindate(updatedTennis.getDisbegindate());
        }
        if (updatedTennis.getDisenddate() != null) {
            existingTennis.setDisenddate(updatedTennis.getDisenddate());
        }
        if (updatedTennis.getDisquantity() != null) {
            existingTennis.setDisquantity(updatedTennis.getDisquantity());
        }
        if (updatedTennis.getPrice() != null) {
            existingTennis.setPhoneNumber(updatedTennis.getPhoneNumber());
        }
    }
}
