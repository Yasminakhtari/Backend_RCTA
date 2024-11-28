package com.ifacehub.tennis.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcademyCoachDto {
    private String coachName;
    private String coachRole;
    private String coachExperience;
}
