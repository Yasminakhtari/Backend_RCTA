package com.ifacehub.tennis.service;

import com.ifacehub.tennis.requestDto.SessionDto;
import com.ifacehub.tennis.util.ResponseObject;

public interface SessionService {
    ResponseObject createSession(SessionDto sessionDto);

    ResponseObject getSessionById(Long id);

    ResponseObject updateSession(Long id, SessionDto sessionDto);

    ResponseObject deleteSession(Long id);

    ResponseObject getAllSession(Long userId);
}
