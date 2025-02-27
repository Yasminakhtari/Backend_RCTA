package com.ifacehub.tennis.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateRangeDto {
    private LocalDateTime from;
    private LocalDateTime to;
}
