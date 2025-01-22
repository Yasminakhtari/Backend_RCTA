package com.ifacehub.tennis.entity;

import com.ifacehub.tennis.requestDto.PlayersDto;
import com.ifacehub.tennis.util.Utils;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
public class Players {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String name;
    private Integer age;
    private String batch;
    private String status;

    public static Players toEntity(PlayersDto playersDto) {
        return Utils.mapper().convertValue(playersDto, Players.class);
    }
}
