package com.ifacehub.tennis.serviceImpl;

import com.ifacehub.tennis.entity.Players;
import com.ifacehub.tennis.entity.Session;
import com.ifacehub.tennis.repository.PlayersRepository;
import com.ifacehub.tennis.repository.SessionRepository;
import com.ifacehub.tennis.requestDto.PlayersDto;
import com.ifacehub.tennis.service.PlayerService;
import com.ifacehub.tennis.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlayerServiceImpl implements PlayerService {
    @Autowired
    private PlayersRepository playersRepository;
    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public ResponseObject createPlayers(PlayersDto playersDto) {
        try{
            Players players = Players.toEntity(playersDto);
            players.setBitDeletedFlag((byte) 0);
            Players savedPlayers = playersRepository.save(players);
            return new ResponseObject(savedPlayers, "SUCCESS", HttpStatus.OK, "Players saved successfully");
        } catch (Exception e){
        	e.printStackTrace();
            return new ResponseObject(HttpStatus.BAD_REQUEST, "ERROR", "Failed to saved player: " + e.getMessage());
        }
    }

    @Override
    public ResponseObject getPlayersById(Long id) {
//        try {
//            Players players = playersRepository.findById(id)
//                    .orElseThrow(() -> new RuntimeException("Players not found with ID: " + id));
//            return new ResponseObject(players, "SUCCESS", HttpStatus.OK, "Players fetched successfully");
//        } catch (Exception e) {
//            return new ResponseObject(HttpStatus.NOT_FOUND, "ERROR", "Failed to fetch players: " + e.getMessage());
//        }
        try {
            // Fetch player by ID
            Players player = playersRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Player not found with ID: " + id));

            // Fetch sessions where the player is part of
            List<Session> sessions = sessionRepository.findSessionsByPlayersId(id);

            // Combine player and session data
            Map<String, Object> response = new HashMap<>();
            response.put("player", player);
            response.put("sessions", sessions);

            return new ResponseObject(response, "SUCCESS", HttpStatus.OK, "Player and sessions fetched successfully");
        } catch (Exception e) {
            return new ResponseObject(HttpStatus.BAD_REQUEST, "ERROR", "Failed to fetch data: " + e.getMessage());
        }
    }


    @Override
    public ResponseObject updatePlayers(Long id, PlayersDto playersDto) {
        try {
            Players existingPlayers = playersRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Location not found with ID: " + id));

            // Update location details
            existingPlayers.setName(playersDto.getName());
            existingPlayers.setAge(playersDto.getAge());
            existingPlayers.setBatch(playersDto.getBatch());

            Players updatedPlayers = playersRepository.save(existingPlayers);
            return new ResponseObject(updatedPlayers, "SUCCESS", HttpStatus.OK, "Players updated successfully");
        } catch (Exception e) {
            return new ResponseObject(HttpStatus.BAD_REQUEST, "ERROR", "Failed to update player: " + e.getMessage());
        }
    }

    @Override
    public ResponseObject getAllPlayersByUserId(Long userId) {
        try {
            List<Players> playersList = playersRepository.findByUserIdAndBitDeletedFlagOrderByIdDesc(userId,(byte) 0);

            return new ResponseObject(playersList, "SUCCESS", HttpStatus.OK, "Players fetched successfully");
        } catch (Exception e) {
            return new ResponseObject(null, "ERROR", HttpStatus.NOT_FOUND, "Players not found");
        }
    }

    @Override
    public ResponseObject deletePlayers(Long id,String reason) {
        try {
            Players players = playersRepository.findByIdAndBitDeletedFlag(id,(byte) 0)
                    .orElseThrow(() -> new RuntimeException("Player not found with ID: " + id));
            players.setBitDeletedFlag((byte) 1);
            players.setRemoveReason(reason);
            playersRepository.save(players);
            return new ResponseObject(players, "SUCCESS", HttpStatus.OK, "Players deleted successfully");
        } catch (Exception e) {
            return new ResponseObject(HttpStatus.NOT_FOUND, "ERROR", "Failed to delete players: " + e.getMessage());
        }
    }
}
