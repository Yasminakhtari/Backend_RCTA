package com.ifacehub.tennis.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TSessionDto {
    private Long sessionId;
    private Long userId;
    private List<Long> playersId;
    private String status;
    private Long courseId;

}
