package com.ifacehub.tennis.service;

import com.ifacehub.tennis.requestDto.TSessionDto;
import com.ifacehub.tennis.util.ResponseObject;

public interface TSessionService {
    ResponseObject saveSession(TSessionDto sessionDto);

    ResponseObject removePlayerFromSession(Long sessionId, Long courseId, Long playerId);
}
