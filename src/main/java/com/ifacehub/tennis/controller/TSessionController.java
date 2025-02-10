package com.ifacehub.tennis.controller;

import com.ifacehub.tennis.requestDto.SessionDto;
import com.ifacehub.tennis.requestDto.TSessionDto;
import com.ifacehub.tennis.service.TSessionService;
import com.ifacehub.tennis.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/t_session")
public class TSessionController {

    @Autowired
    private TSessionService tSessionService;

    //Save
    @PostMapping("/saveSession")
    public ResponseEntity<ResponseObject> saveSession(@RequestBody TSessionDto sessionDto){
        ResponseObject response = tSessionService.saveSession(sessionDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    // Delete
    @DeleteMapping("/removePlayer")
    public ResponseEntity<ResponseObject> removePlayerFromSession(
            @RequestParam Long sessionId,
            @RequestParam Long courseId,
            @RequestParam Long playerId) {

        ResponseObject response = tSessionService.removePlayerFromSession(sessionId, courseId, playerId);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
