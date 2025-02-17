package com.ifacehub.tennis.serviceImpl;

import com.ifacehub.tennis.entity.Notification;
import com.ifacehub.tennis.entity.NotificationUsers;
import com.ifacehub.tennis.repository.NotificationRepository;
import com.ifacehub.tennis.repository.NotificationUsersRepository;
import com.ifacehub.tennis.repository.OrderRepository;
import com.ifacehub.tennis.requestDto.NotificationUserDto;
import com.ifacehub.tennis.responseDto.NotificationResponseDto;
import com.ifacehub.tennis.service.NotificationService;
import com.ifacehub.tennis.util.ResponseObject;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private NotificationUsersRepository notificationUsersRepository;

    @Override
    @Transactional
    public ResponseObject saveNotification(Long sessionId, String fromDate, String toDate) {
//        List<Long> userIds = orderRepository.findUserIdsBySessionId(sessionId,fromDate,toDate);
//
//        if (userIds.isEmpty()) {
//            return new ResponseObject(null, "ERROR", HttpStatus.NOT_FOUND, "No users found for session ID: " + sessionId);
//        }
//
//        // Store all user IDs in a single notification record
//        Notification notification = new Notification();
//        notification.setCreatedOn(LocalDateTime.now());
//        notification.setMessage("Please send Feedback");
//        notification.setUserIds(userIds); // ✅ Store userIds as a list
//        notification.setStatus("SENT");
//
//        notificationRepository.save(notification);
//
//        // Simulate sending notification
//        for (Long userId : userIds) {
//            System.out.println("Sending notification to user ID: " + userId + " - Message: Please send Feedback");
//        }
//
//        return new ResponseObject(notification, "SUCCESS", HttpStatus.OK, "Notifications sent successfully");
//    }
        List<Long> userIds = orderRepository.findUserIdsBySessionId(sessionId, fromDate, toDate);

        if (userIds.isEmpty()) {
            return new ResponseObject(null, "ERROR", HttpStatus.NOT_FOUND,
                    "No users found for session ID: " + sessionId);
        }

        // ✅ Create a new Notification
        Notification notification = new Notification();
        notification.setCreatedOn(LocalDateTime.now());
        notification.setMessage("Please send Feedback");
        notification.setStatus("SENT");

        // ✅ Create NotificationUsers entries for each user
        List<NotificationUsers> notificationUserList = new ArrayList<>();
        for (Long userId : userIds) {
            NotificationUsers notificationUser = new NotificationUsers();
            notificationUser.setNotification(notification);
            notificationUser.setUserId(userId);
            notificationUser.setStatus("unread");
            notificationUser.setCreatedOn(LocalDateTime.now());

            notificationUserList.add(notificationUser);
        }

        // ✅ Associate users with the notification
        notification.setNotificationUsers(notificationUserList);
        notificationRepository.save(notification);

        // ✅ Build response using Map
        Map<String, Object> response = new HashMap<>();
        response.put("notificationId", notification.getId());
        response.put("message", notification.getMessage());
        response.put("status", notification.getStatus());

        // ✅ Add users
        List<Map<String, Object>> usersList = new ArrayList<>();
        for (NotificationUsers user : notificationUserList) {
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("id", user.getId());
            userMap.put("userId", user.getUserId());
            userMap.put("status", user.getStatus());
            usersList.add(userMap);
        }
        response.put("users", usersList);

        return new ResponseObject(response, "SUCCESS", HttpStatus.OK, "Notifications sent successfully");
    }

    @Override
    public NotificationResponseDto getNotificationsForUser(Long userId) {
        List<NotificationUsers> userNotifications = notificationUsersRepository.findByUserId(userId);

        if (userNotifications.isEmpty()) {
            return null;
        }

        // ✅ Get notification details from the first entry
        Notification notification = userNotifications.get(0).getNotification();

        // ✅ Convert to DTO for clean response
        List<NotificationUserDto> userDTOs = userNotifications.stream()
                .map(user -> new NotificationUserDto(user.getId(), user.getUserId(), user.getStatus()))
                .collect(Collectors.toList());

        return new NotificationResponseDto(
                notification.getId(),
                notification.getMessage(),
                notification.getStatus(),
                userDTOs
        );
    }

    @Override
    @Transactional
    public void markNotificationAsRead(Long notificationId, Long userId) {
        notificationUsersRepository.markNotificationAsRead(notificationId, userId);
    }
}


