package com.ifacehub.tennis.controller;

import com.ifacehub.tennis.requestDto.LocationDto;
import com.ifacehub.tennis.responseDto.NotificationResponseDto;
import com.ifacehub.tennis.service.NotificationService;
import com.ifacehub.tennis.util.ResponseObject;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<ResponseObject> saveNotification(@RequestBody Map<String, Object> requestBody) {
    Long sessionId = requestBody.containsKey("sessionId") ? ((Number) requestBody.get("sessionId")).longValue() : null;
    Long userId = requestBody.containsKey("userId") ? ((Number) requestBody.get("userId")).longValue() : null;
    String message = (String) requestBody.get("message");
    String fromDate = (String) requestBody.get("fromDate");
    String toDate = (String) requestBody.get("toDate");

    ResponseObject response = notificationService.saveNotification(sessionId,userId, message, fromDate, toDate);
    return new ResponseEntity<>(response, HttpStatus.OK);
    //Save
//    @PostMapping
//    public ResponseEntity<ResponseObject> saveNotification(@RequestParam(required = false) Long sessionId,
//                                                           @RequestParam String message,
//                                                           @RequestParam String fromDate,
//                                                           @RequestParam String toDate){
//        ResponseObject response = notificationService.saveNotification(sessionId,message,fromDate,toDate);
//        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //  Get notifications for a specific user
    // @GetMapping("/user")
    // public ResponseEntity<NotificationResponseDto> getNotificationsForUser(@RequestParam Long userId) {
    //     NotificationResponseDto response = notificationService.getNotificationsForUser(userId);
    //     if (response == null) {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    //     }
    //     return ResponseEntity.ok(response);
    // }
    @GetMapping("/user")
    public ResponseEntity<ResponseObject> getUserNotifications(
        @RequestParam Long userId,
        @RequestParam(defaultValue = "false") boolean unreadOnly,
        @RequestParam(defaultValue = "createdOn") String sortBy,
        @RequestParam(defaultValue = "desc") String sortDirection) {
        
        try {
            List<NotificationResponseDto> notifications = notificationService.getUserNotifications(userId, unreadOnly,sortBy,sortDirection);
            
            if(notifications.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ResponseObject(null, "SUCCESS", HttpStatus.NO_CONTENT, "No notifications found"));
            }
            
            return ResponseEntity.ok(
                new ResponseObject(notifications, "SUCCESS", HttpStatus.OK, "Notifications retrieved")
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject(null, "ERROR", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }


    //  Mark notification as read for a specific user
    // @PutMapping("/update")
    // public ResponseEntity<String> markNotificationAsRead(@RequestParam Long notificationId, @RequestParam Long userId) {
    //     notificationService.markNotificationAsRead(notificationId, userId);
    //     return ResponseEntity.ok("Notification marked as read for user " + userId);
    // }
    @PutMapping("/{notificationId}/read")
    public ResponseEntity<ResponseObject> markNotificationAsRead(
        @PathVariable Long notificationId,
        @RequestParam Long userId) {
        
        try {
            notificationService.markNotificationAsRead(notificationId, userId);
            return ResponseEntity.ok(
                new ResponseObject(null, "SUCCESS", HttpStatus.OK, "Notification marked as read")
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject(null, "ERROR", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }
}
