package com.ifacehub.tennis.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayersDto {
    private Long userId;
    private String name;
    private Integer age;
    private String batch;
    private String status;
}
