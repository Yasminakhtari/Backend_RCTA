package com.ifacehub.tennis.service;

import com.ifacehub.tennis.util.ResponseObject;

public interface NotificationService {
    ResponseObject saveNotification(Long sessionId, String fromDate, String toDate);
}
