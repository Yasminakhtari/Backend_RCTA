package com.ifacehub.tennis.entity;

import com.ifacehub.tennis.requestDto.AcademyMilestoneDto;
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
public class AcademyMilestones extends Auditable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer year;
    private String title;
    private String description;
    private Byte bitDeletedFlag;

    public static AcademyMilestones toEntity(AcademyMilestoneDto dto) {
        return Utils.mapper().convertValue(dto, AcademyMilestones.class);
    }
}
