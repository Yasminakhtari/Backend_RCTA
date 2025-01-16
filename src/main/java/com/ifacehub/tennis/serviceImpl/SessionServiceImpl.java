package com.ifacehub.tennis.serviceImpl;

import com.ifacehub.tennis.entity.Role;
import com.ifacehub.tennis.entity.Session;
import com.ifacehub.tennis.repository.SessionRepository;
import com.ifacehub.tennis.requestDto.SessionDto;
import com.ifacehub.tennis.service.SessionService;
import com.ifacehub.tennis.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionServiceImpl implements SessionService {
    @Autowired
    private SessionRepository sessionRepository;
    @Override
    public ResponseObject createSession(SessionDto sessionDto) {
        try{
            Session session = Session.toEntity(sessionDto);
            Session savedSession = sessionRepository.save(session);
            return new ResponseObject(savedSession, "SUCCESS", HttpStatus.OK, "Session saved successfully");
        } catch (Exception e){
            return new ResponseObject(HttpStatus.BAD_REQUEST, "ERROR", "Failed to saved session: " + e.getMessage());
        }
    }

    @Override
    public ResponseObject getSessionById(Long id) {
        try {
            Session session = sessionRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Session not found with ID: " + id));
            return new ResponseObject(session, "SUCCESS", HttpStatus.OK, "Session fetched successfully");
        } catch (Exception e) {
            return new ResponseObject(HttpStatus.NOT_FOUND, "ERROR", "Failed to fetch session: " + e.getMessage());
        }
    }

    @Override
    public ResponseObject updateSession(Long id, SessionDto sessionDto) {
        try {
            Session existingSession = sessionRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Session not found with ID: " + id));

            // Update session details
            existingSession.setCourseId(sessionDto.getCourseId());
            existingSession.setCoachId(sessionDto.getCoachId());
            existingSession.setFromDate(sessionDto.getFromDate());
            existingSession.setToDate(sessionDto.getToDate());
            existingSession.setDays(sessionDto.getDays());
            existingSession.setStartTime(sessionDto.getStartTime());
            existingSession.setEndTime(sessionDto.getEndTime());
            existingSession.setMaxCapacity(sessionDto.getMaxCapacity());
            existingSession.setMaxWaitingCapacity(sessionDto.getMaxWaitingCapacity());
            existingSession.setPrice(sessionDto.getPrice());
            existingSession.setLocationId(sessionDto.getLocationId());

            Session updatedSession = sessionRepository.save(existingSession);
            return new ResponseObject(updatedSession, "SUCCESS", HttpStatus.OK, "Session updated successfully");
        } catch (Exception e) {
            return new ResponseObject(HttpStatus.BAD_REQUEST, "ERROR", "Failed to update session: " + e.getMessage());
        }
    }

    @Override
    public ResponseObject deleteSession(Long id) {
        try {
            Session session = sessionRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Session not found with ID: " + id));
            sessionRepository.delete(session);
            return new ResponseObject(null, "SUCCESS", HttpStatus.OK, "Session deleted successfully");
        } catch (Exception e) {
            return new ResponseObject(HttpStatus.NOT_FOUND, "ERROR", "Failed to delete session: " + e.getMessage());
        }
    }

    @Override
    public ResponseObject getAllSession() {
        try {
            List<Session> sessionList = sessionRepository.findAll();
            return new ResponseObject(sessionList, "SUCCESS", HttpStatus.OK, "Session fetched successfully");
        } catch (Exception e) {
            return new ResponseObject(null, "ERROR", HttpStatus.NOT_FOUND, "Session not found");
        }
    }
}
