package com.ifacehub.tennis.requestDto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationUserDto {
//     private Long id;
//     private Long userId;
//     private String status;

    private Long id;
    private Long userId;
    private String status;
    private LocalDateTime createdOn;
}
