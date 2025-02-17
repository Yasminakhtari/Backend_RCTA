package com.ifacehub.tennis.controller;

import com.ifacehub.tennis.requestDto.LocationDto;
import com.ifacehub.tennis.responseDto.NotificationResponseDto;
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
    // ✅ Get notifications for a specific user
    @GetMapping("/user")
    public ResponseEntity<NotificationResponseDto> getNotificationsForUser(@RequestParam Long userId) {
        NotificationResponseDto response = notificationService.getNotificationsForUser(userId);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(response);
    }

    // ✅ Mark notification as read for a specific user
    @PutMapping("/update")
    public ResponseEntity<String> markNotificationAsRead(@RequestParam Long notificationId, @RequestParam Long userId) {
        notificationService.markNotificationAsRead(notificationId, userId);
        return ResponseEntity.ok("Notification marked as read for user " + userId);
    }
}
