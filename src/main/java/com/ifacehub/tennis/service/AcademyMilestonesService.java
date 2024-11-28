package com.ifacehub.tennis.service;

import com.ifacehub.tennis.requestDto.AcademyMilestoneDto;
import com.ifacehub.tennis.util.ResponseObject;

public interface AcademyMilestonesService {
    ResponseObject createMilestones(AcademyMilestoneDto dto);

    ResponseObject getMilestonesById(Long id);

    ResponseObject updateMilestones(Long id, AcademyMilestoneDto academyMilestoneDto);

    ResponseObject deleteMilestones(Long id);

    ResponseObject getAllMilestones();
}
