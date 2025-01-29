package com.ifacehub.tennis.controller;

import com.ifacehub.tennis.requestDto.SessionDto;
import com.ifacehub.tennis.service.SessionService;
import com.ifacehub.tennis.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/session")
public class SessionController {
    @Autowired
    private SessionService sessionService;

    //Save
    @PostMapping("/createSession")
    public ResponseEntity<ResponseObject> createSession(@RequestBody SessionDto sessionDto){
        ResponseObject response = sessionService.createSession(sessionDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getSessionById(@PathVariable Long id) {
        ResponseObject response = sessionService.getSessionById(id);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateSession(@PathVariable Long id, @RequestBody SessionDto sessionDto) {
        ResponseObject response = sessionService.updateSession(id, sessionDto);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteSession(@PathVariable Long id) {
        ResponseObject response = sessionService.deleteSession(id);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    // GetAll
    @GetMapping
    public ResponseEntity<ResponseObject> getAllSession(
            @RequestParam(name = "userId", defaultValue = "0") Long userId
    ) {
        ResponseObject response = sessionService.getAllSession(userId);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
