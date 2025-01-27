package com.ifacehub.tennis.controller;

import com.ifacehub.tennis.requestDto.PlayersDto;
import com.ifacehub.tennis.service.PlayerService;
import com.ifacehub.tennis.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/players")
public class PlayersController {
    @Autowired
    private PlayerService playerService;

    //Save
    @PostMapping("/createPlayer")
    public ResponseEntity<ResponseObject> createPlayer(@RequestBody PlayersDto playersDto){
        ResponseObject response = playerService.createPlayers(playersDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get by ID
    @GetMapping
    public ResponseEntity<ResponseObject> getPlayersById(
            @RequestParam Long id
    ) {
        ResponseObject response = playerService.getPlayersById(id);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    // Update
    @PutMapping("/updatePlayers")
    public ResponseEntity<ResponseObject> updatePlayers(
            @RequestParam Long id,
            @RequestBody PlayersDto playersDto) {
        ResponseObject response = playerService.updatePlayers(id, playersDto);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
    //Get All
    @GetMapping("/getAllPlayers")
    public ResponseEntity<ResponseObject> getAllPlayers(
            @RequestParam Long userId
    ) {
        ResponseObject response = playerService.getAllPlayersByUserId(userId);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    // Delete
    @DeleteMapping
    public ResponseEntity<ResponseObject> deletePlayers(
            @RequestParam Long id,
            @RequestParam String reason) {
        ResponseObject response = playerService.deletePlayers(id,reason);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
