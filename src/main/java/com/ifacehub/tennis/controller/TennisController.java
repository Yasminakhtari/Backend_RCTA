package com.ifacehub.tennis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifacehub.tennis.entity.Tennis;
import com.ifacehub.tennis.service.TennisService;
import com.ifacehub.tennis.serviceImpl.FileService;
import com.ifacehub.tennis.util.ResponseObject;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TennisController {

    @Autowired
    private TennisService tennisService;
    @Autowired
    private FileService fileService;

    @PostMapping("/createTennis")
    public ResponseEntity<ResponseObject> createTennisService(
//            @RequestParam("tennisData") String tennis,
//            @RequestParam(value = "file", required = false)  MultipartFile file
    		@RequestBody Tennis tennis
    ) {
    	System.out.println("form data" + tennis);
//        ObjectMapper objectMapper = new ObjectMapper();
//        Tennis tennisObj = new Tennis();
//        try {
//            tennisObj = objectMapper.readValue(tennis, Tennis.class);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();	
//        }
        ResponseObject response = tennisService.createTennisService(tennis);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAllTennis")
    public List<Tennis> getAllTennis() {
        return tennisService.getAllTennis();
    }

    @GetMapping("/getAllByStatus")
    public List<Tennis> getAllByStatus(@RequestParam String status) {
        return tennisService.getAllByStatus(status);
    }

    @PostMapping("/updateTennis")
    public ResponseEntity<ResponseObject> updateTennis(@RequestBody Tennis tennis) {
        ResponseObject response = tennisService.updateTennis(tennis);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/toggleStatus/{id}")
    public ResponseEntity<ResponseObject> toggleStatus(@PathVariable Long id, @RequestParam String status) {
        // Validate and update the service status
        ResponseObject response = tennisService.toggleStatus(id, status);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/deleteTennis/{id}")
    public ResponseEntity<ResponseObject> deleteTennis(@PathVariable Long id) {
        ResponseObject response = tennisService.deleteTennis(id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/getAllCategoriesAndSubCategories")
    public List<Object[]> getAllCategoriesAndSubCategories() {
        return tennisService.findAllCategoriesAndSubCategories();
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        try {
            // Load the file as a resource
            Resource resource = fileService.loadFileAsResource(fileName);

            // Determine the file's content type
            String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            // Return the file as a downloadable response
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
