package com.ifacehub.tennis.service;

import java.util.List;

import com.ifacehub.tennis.responseDto.NotificationResponseDto;
import com.ifacehub.tennis.util.ResponseObject;

public interface NotificationService {
    ResponseObject saveNotification(Long sessionId,Long userId,String message, String fromDate, String toDate);
    // NotificationResponseDto getNotificationsForUser(Long userId);
    void markNotificationAsRead(Long notificationId, Long userId);
    List<NotificationResponseDto> getUserNotifications(Long userId,boolean unreadOnly,String sortBy, String sortDirection);
}
