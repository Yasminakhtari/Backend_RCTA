package com.ifacehub.tennis.controller;

import com.ifacehub.tennis.requestDto.LocationDto;
import com.ifacehub.tennis.service.NotificationService;
import com.ifacehub.tennis.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    //Save
    @PostMapping
    public ResponseEntity<ResponseObject> saveNotification(@RequestParam Long sessionId,
                                                           @RequestParam String fromDate,
                                                           @RequestParam String toDate){
        ResponseObject response = notificationService.saveNotification(sessionId,fromDate,toDate);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
