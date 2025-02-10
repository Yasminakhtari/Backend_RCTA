package com.ifacehub.tennis.serviceImpl;

import com.ifacehub.tennis.entity.TSession;
import com.ifacehub.tennis.repository.PlayersRepository;
import com.ifacehub.tennis.repository.TSessionRepository;
import com.ifacehub.tennis.requestDto.TSessionDto;
import com.ifacehub.tennis.service.TSessionService;
import com.ifacehub.tennis.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TSessionServiceImpl implements TSessionService {
    @Autowired
    private TSessionRepository tSessionRepository;
    @Autowired
    private PlayersRepository playersRepository;

    @Override
    public ResponseObject saveSession(TSessionDto sessionDto) {
        try {
            List<Long> playersToAdd = sessionDto.getPlayersId();
            Long[] playersArray = playersToAdd.toArray(new Long[0]); // ✅ Convert List<Long> to Long[]

            //  Check if `sessionId` and `courseId` already exist
            List<Long> existingPlayersInSession = tSessionRepository.findExistingPlayersInSession(
                    sessionDto.getSessionId(), sessionDto.getCourseId(), playersArray
            );

            if (!existingPlayersInSession.isEmpty()) {
                List<String> playerNames = playersRepository.findNameByIds(existingPlayersInSession);
                return new ResponseObject(HttpStatus.CONFLICT, "ERROR",
                        "These players already exist in session " + sessionDto.getSessionId() + " for this course: "
                                + String.join(", ", playerNames));
            }

            // Ensure players are not assigned to any other session
            List<Long> existingPlayersInOtherSessions = tSessionRepository.findPlayersInOtherSessions(
                    sessionDto.getSessionId(), playersArray
            );

            if (!existingPlayersInOtherSessions.isEmpty()) {
                List<String> playerNames = playersRepository.findNameByIds(existingPlayersInOtherSessions);
                return new ResponseObject(HttpStatus.CONFLICT, "ERROR",
                        "These players are already assigned to another session: " + String.join(", ", playerNames));
            }

            // Update existing session or create a new session
            Optional<TSession> existingSessionOpt = tSessionRepository.findBySessionIdAndCourseId(
                    sessionDto.getSessionId(), sessionDto.getCourseId());

            TSession session;
            if (existingSessionOpt.isPresent()) {
                //  Update existing session by adding new players
                session = existingSessionOpt.get();
                List<Long> updatedPlayers = new ArrayList<>(session.getPlayersId());
                updatedPlayers.addAll(playersToAdd);
                session.setPlayersId(updatedPlayers);
            } else {
                // Create a new session
                session = TSession.toEntity(sessionDto);
                session.setStatus("Pending");
            }

            TSession savedSession = tSessionRepository.save(session);
            return new ResponseObject(savedSession, "SUCCESS", HttpStatus.OK, "Session saved/updated successfully");

        } catch (Exception e) {
            return new ResponseObject(HttpStatus.BAD_REQUEST, "ERROR", "Failed to save session: " + e.getMessage());
        }
    }

    @Override
    public ResponseObject removePlayerFromSession(Long sessionId, Long courseId, Long playerId) {
        try {
            // 🔹 Step 1: Find session by sessionId and courseId
            Optional<TSession> sessionOpt = tSessionRepository.findBySessionIdAndCourseId(sessionId, courseId);

            if (!sessionOpt.isPresent()) {
                return new ResponseObject(HttpStatus.NOT_FOUND, "ERROR",
                        "Session with ID " + sessionId + " and Course ID " + courseId + " not found.");
            }

            TSession session = sessionOpt.get();

            // 🔹 Step 2: Check if the player exists in the session
            if (!session.getPlayersId().contains(playerId)) {
                return new ResponseObject(HttpStatus.NOT_FOUND, "ERROR",
                        "Player with ID " + playerId + " not found in session.");
            }

            // 🔹 Step 3: Remove the player and update the session
            session.setPlayersId(session.getPlayersId().stream()
                    .filter(id -> !id.equals(playerId))
                    .collect(Collectors.toList()));

            tSessionRepository.save(session);

            return new ResponseObject(session, "SUCCESS", HttpStatus.OK,
                    "Removed player " + playerId + " from session successfully.");

        } catch (Exception e) {
            return new ResponseObject(HttpStatus.BAD_REQUEST, "ERROR",
                    "Failed to remove player: " + e.getMessage());
        }
    }

//        try {
//            List<Long> playersToAdd = sessionDto.getPlayersId();
//            Long[] playersArray = playersToAdd.toArray(new Long[0]);  // 🔹 Convert List<Long> to Long[]
//
////            boolean sessionExists = tSessionRepository.existsBySessionId(sessionDto.getSessionId());
////            if (sessionExists) {
////                return new ResponseObject(HttpStatus.CONFLICT, "ERROR",
////                        "Session with ID " + sessionDto.getSessionId() + " already exists. Cannot save again.");
////            }
//
//            // 🔹 Step 1: Find if any players are already assigned to a different session
//            List<Long> existingPlayersInOtherSessions = tSessionRepository.findPlayersInOtherSessions(
//                    sessionDto.getSessionId(), playersArray
//            );
//
//            // 🔹 Step 2: If players exist in another session, return an error
//            if (!existingPlayersInOtherSessions.isEmpty()) {
//                List<String> playerNames = playersRepository.findNameByIds(existingPlayersInOtherSessions);
//                return new ResponseObject(HttpStatus.CONFLICT, "ERROR",
//                        "These players are already assigned to another session: " + String.join(", ", playerNames));
//            }
//
//            // 🔹 Step 3: Save the session if no conflicts
//            TSession session = TSession.toEntity(sessionDto);
//            session.setStatus("Pending");
//            TSession savedSession = tSessionRepository.save(session);
//
//            return new ResponseObject(savedSession, "SUCCESS", HttpStatus.OK, "Session saved successfully");
//
//        } catch (Exception e) {
//            return new ResponseObject(HttpStatus.BAD_REQUEST, "ERROR", "Failed to save session: " + e.getMessage());
//        }
//    }
}
