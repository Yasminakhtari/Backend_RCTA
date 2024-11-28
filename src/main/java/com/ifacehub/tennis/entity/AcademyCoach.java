package com.ifacehub.tennis.entity;

import com.ifacehub.tennis.requestDto.AcademyCoachDto;
import com.ifacehub.tennis.util.Utils;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AcademyCoach extends Auditable<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String coachName;
    private String coachRole;
    private String coachExperience;
    private Byte bitDeletedFlag;
    private String filePath;

    public static AcademyCoach toEntity(AcademyCoachDto dto) {
        return Utils.mapper().convertValue(dto, AcademyCoach.class);
    }
}
