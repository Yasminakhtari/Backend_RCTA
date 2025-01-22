package com.ifacehub.tennis.service;

import com.ifacehub.tennis.requestDto.PlayersDto;
import com.ifacehub.tennis.util.ResponseObject;
import org.springframework.stereotype.Service;

@Service
public interface PlayerService {
    ResponseObject createPlayers(PlayersDto playersDto);

    ResponseObject getPlayersById(Long id);

    ResponseObject updatePlayers(Long id, PlayersDto playersDto);

    ResponseObject getAllPlayersByUserId(Long userId);

    ResponseObject deletePlayers(Long id);
}
