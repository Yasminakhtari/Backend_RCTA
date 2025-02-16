package com.ifacehub.tennis.serviceImpl;

import com.ifacehub.tennis.entity.Notification;
import com.ifacehub.tennis.repository.NotificationRepository;
import com.ifacehub.tennis.repository.OrderRepository;
import com.ifacehub.tennis.service.NotificationService;
import com.ifacehub.tennis.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public ResponseObject saveNotification(Long sessionId, String fromDate, String toDate) {
        List<Long> userIds = orderRepository.findUserIdsBySessionId(sessionId,fromDate,toDate);

        if (userIds.isEmpty()) {
            return new ResponseObject(null, "ERROR", HttpStatus.NOT_FOUND, "No users found for session ID: " + sessionId);
        }

        // Store all user IDs in a single notification record
        Notification notification = new Notification();
        notification.setCreatedOn(LocalDateTime.now());
        notification.setMessage("Please send Feedback");
        notification.setUserIds(userIds); // ✅ Store userIds as a list
        notification.setStatus("SENT");

        notificationRepository.save(notification);

        // Simulate sending notification
        for (Long userId : userIds) {
            System.out.println("Sending notification to user ID: " + userId + " - Message: Please send Feedback");
        }

        return new ResponseObject(notification, "SUCCESS", HttpStatus.OK, "Notifications sent successfully");
    }
}


