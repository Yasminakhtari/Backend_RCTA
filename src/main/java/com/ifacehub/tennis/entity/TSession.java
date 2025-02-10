package com.ifacehub.tennis.entity;

import com.ifacehub.tennis.requestDto.TSessionDto;
import com.ifacehub.tennis.util.Utils;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
public class TSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long sessionId;
    private Long userId;
    private List<Long> playersId;
    private String status;
    private Long courseId;
    public static TSession toEntity(TSessionDto tSessionDto) {
        return Utils.mapper().convertValue(tSessionDto, TSession.class);
    }
}
