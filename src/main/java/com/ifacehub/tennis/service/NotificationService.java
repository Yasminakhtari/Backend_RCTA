package com.ifacehub.tennis.service;

import com.ifacehub.tennis.responseDto.NotificationResponseDto;
import com.ifacehub.tennis.util.ResponseObject;

public interface NotificationService {
    ResponseObject saveNotification(Long sessionId, String fromDate, String toDate);

    NotificationResponseDto getNotificationsForUser(Long userId);

    void markNotificationAsRead(Long notificationId, Long userId);
}
