package com.ifacehub.tennis.responseDto;

import com.ifacehub.tennis.requestDto.NotificationUserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponseDto {
    private Long notificationId;
    private String message;
    private String status;
    private List<NotificationUserDto> users;
}
