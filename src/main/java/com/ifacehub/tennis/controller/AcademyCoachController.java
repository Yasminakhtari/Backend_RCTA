package com.ifacehub.tennis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifacehub.tennis.requestDto.AcademyCoachDto;
import com.ifacehub.tennis.service.AcademyCoachService;
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

@RestController
@RequestMapping("/api/coach")
@CrossOrigin(origins = "*")
public class AcademyCoachController {
    @Autowired
    private AcademyCoachService academyCoachService;
    @Autowired
    private FileService fileService;
    @PostMapping
    public ResponseEntity<ResponseObject> createCoach(
            @RequestParam("coachData")  String  coachData,
            @RequestParam("file") MultipartFile file) {
        ObjectMapper objectMapper = new ObjectMapper();
        AcademyCoachDto dto =new AcademyCoachDto();
        try {
            dto = objectMapper.readValue(coachData, AcademyCoachDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ResponseObject response = academyCoachService.createCoach(dto,file);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getCoachById(@PathVariable Long id) {
        ResponseObject response = academyCoachService.getCoachById(id);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateCoach(@PathVariable Long id, @RequestBody AcademyCoachDto academyCoachDto) {
        ResponseObject response = academyCoachService.updateCoach(id, academyCoachDto);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteCoach(@PathVariable Long id) {
        ResponseObject response = academyCoachService.deleteCoach(id);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
    @GetMapping
    public ResponseEntity<ResponseObject> getAllCoach() {
        ResponseObject response = academyCoachService.getAllCoach();
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
