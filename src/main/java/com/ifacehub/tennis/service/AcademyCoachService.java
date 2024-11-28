package com.ifacehub.tennis.service;

import com.ifacehub.tennis.requestDto.AcademyCoachDto;
import com.ifacehub.tennis.util.ResponseObject;
import org.springframework.web.multipart.MultipartFile;

public interface AcademyCoachService {
    ResponseObject createCoach(AcademyCoachDto dto, MultipartFile file);

    ResponseObject getCoachById(Long id);

    ResponseObject updateCoach(Long id, AcademyCoachDto academyCoachDto);

    ResponseObject deleteCoach(Long id);

    ResponseObject getAllCoach();
}
