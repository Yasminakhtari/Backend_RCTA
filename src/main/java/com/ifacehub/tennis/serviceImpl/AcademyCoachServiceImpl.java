package com.ifacehub.tennis.serviceImpl;

import com.ifacehub.tennis.entity.AcademyCoach;
import com.ifacehub.tennis.repository.AcademyCoachRepository;
import com.ifacehub.tennis.requestDto.AcademyCoachDto;
import com.ifacehub.tennis.service.AcademyCoachService;
import com.ifacehub.tennis.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AcademyCoachServiceImpl implements AcademyCoachService {
    @Autowired
    private FileService fileService;
    @Autowired
    private AcademyCoachRepository academyCoachRepository;
    @Override
    public ResponseObject createCoach(AcademyCoachDto dto, MultipartFile file) {
        try {
            AcademyCoach academyCoach = AcademyCoach.toEntity(dto);
            academyCoach.setBitDeletedFlag((byte) 0);
            academyCoach.setCreatedBy(1L);
            academyCoach.setCreatedOn(LocalDateTime.now());
            // Save the file
            if (file != null && !file.isEmpty()) {
                String fileName = fileService.saveFile(file);
                academyCoach.setFilePath(fileName); // Save file name in the database
            }
            AcademyCoach savedCoach = academyCoachRepository.save(academyCoach);
            return new ResponseObject(savedCoach, "SUCCESS", HttpStatus.CREATED, "Academy Coach created successfully");
        } catch (Exception e) {
            return new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR", "Failed to create academy coach: " + e.getMessage());
        }
    }

    @Override
    public ResponseObject getCoachById(Long id) {
        try {
            AcademyCoach coach = academyCoachRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Academy coach not found"));
            return new ResponseObject(coach, "SUCCESS", HttpStatus.OK, "Academy coach found");
        } catch (RuntimeException e) {
            return new ResponseObject(HttpStatus.NOT_FOUND, "ERROR", e.getMessage());
        } catch (Exception e) {
            return new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR", "Failed to retrieve academy coach: " + e.getMessage());
        }
    }

    @Override
    public ResponseObject updateCoach(Long id, AcademyCoachDto academyCoachDto) {
        try {
            AcademyCoach existingCoach = academyCoachRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Academy Coach not found"));
            AcademyCoach updatedCoach = AcademyCoach.toEntity(academyCoachDto);

            existingCoach.setCoachName(updatedCoach.getCoachName());
            existingCoach.setCoachRole(updatedCoach.getCoachRole());
            existingCoach.setCoachExperience(updatedCoach.getCoachExperience());
            existingCoach.setBitDeletedFlag(updatedCoach.getBitDeletedFlag());
            existingCoach.setUpdatedOn(LocalDateTime.now());

            AcademyCoach savedCoach = academyCoachRepository.save(existingCoach);
            return new ResponseObject(savedCoach, "SUCCESS", HttpStatus.OK, "Academy Coach updated successfully");
        } catch (RuntimeException e) {
            return new ResponseObject(HttpStatus.NOT_FOUND, "ERROR", e.getMessage());
        } catch (Exception e) {
            return new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR", "Failed to update academy coach: " + e.getMessage());
        }
    }

    @Override
    public ResponseObject deleteCoach(Long id) {
        try {
            Optional<AcademyCoach> academyCoachOptional = academyCoachRepository.findByIdAndBitDeletedFlag(id, (byte) 0);

            if (academyCoachOptional.isPresent()) {
                AcademyCoach academyCoach = academyCoachOptional.get();
                academyCoach.setBitDeletedFlag((byte) 1);
                academyCoachRepository.save(academyCoach); // Save the updated entity

                return new ResponseObject(academyCoach, "SUCCESS", HttpStatus.OK, "Academy Coach deleted successfully");
            } else {
                return new ResponseObject(null, "ERROR", HttpStatus.NOT_FOUND, "Academy Coach not found");
            }
        } catch (RuntimeException e) {
            return new ResponseObject(null, "ERROR", HttpStatus.NOT_FOUND, "Academy Coach not found");
        } catch (Exception e) {
            return new ResponseObject(null, "ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while deleting the academy coach");
        }
    }

    @Override
    public ResponseObject getAllCoach() {
        try {
            List<AcademyCoach> academyCoachList = academyCoachRepository.findAllActive();
            return new ResponseObject(academyCoachList, "SUCCESS", HttpStatus.OK, "Academy coach fetched successfully");
        } catch (Exception e) {
            return new ResponseObject(null, "ERROR", HttpStatus.NOT_FOUND, "Academy coach not found");
        }
    }
}
