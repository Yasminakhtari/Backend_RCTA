package com.ifacehub.tennis.responseDto;

import com.ifacehub.tennis.entity.Session;
import com.ifacehub.tennis.entity.Tennis;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionResponseDto {
    private Tennis tennisData;
    private List<Session> sessions;
}
