package com.ifacehub.tennis.serviceImpl;

import com.ifacehub.tennis.entity.AcademyMilestones;
import com.ifacehub.tennis.repository.AcademyMilestonesRepository;
import com.ifacehub.tennis.requestDto.AcademyMilestoneDto;
import com.ifacehub.tennis.service.AcademyMilestonesService;
import com.ifacehub.tennis.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AcademyMilestonesServiceImpl implements AcademyMilestonesService {
    @Autowired
    private AcademyMilestonesRepository academyMilestonesRepository;

    @Override
    public ResponseObject createMilestones(AcademyMilestoneDto dto) {
        try {
            AcademyMilestones milestones = AcademyMilestones.toEntity(dto);
            milestones.setBitDeletedFlag((byte) 0);
            milestones.setCreatedBy(1L);
            milestones.setCreatedOn(LocalDateTime.now());
            AcademyMilestones savedMilestones = academyMilestonesRepository.save(milestones);
            return new ResponseObject(savedMilestones, "SUCCESS", HttpStatus.CREATED, "Academy Milestones created successfully");
        } catch (Exception e) {
            return new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR", "Failed to create academy milestones: " + e.getMessage());
        }
    }

    @Override
    public ResponseObject getMilestonesById(Long id) {
        try {
            AcademyMilestones milestones = academyMilestonesRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Academy Milestones not found"));
            return new ResponseObject(milestones, "SUCCESS", HttpStatus.OK, "Academy Milestones found");
        } catch (RuntimeException e) {
            return new ResponseObject(HttpStatus.NOT_FOUND, "ERROR", e.getMessage());
        } catch (Exception e) {
            return new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR", "Failed to retrieve academy milestones: " + e.getMessage());
        }
    }

    @Override
    public ResponseObject updateMilestones(Long id, AcademyMilestoneDto academyMilestoneDto) {
        try {
            AcademyMilestones existingMilestones = academyMilestonesRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Academy Milestone not found"));
            AcademyMilestones updatedMilestones = AcademyMilestones.toEntity(academyMilestoneDto);

            existingMilestones.setYear(updatedMilestones.getYear());
            existingMilestones.setDescription(updatedMilestones.getDescription());
            existingMilestones.setTitle(updatedMilestones.getTitle());
            existingMilestones.setBitDeletedFlag(updatedMilestones.getBitDeletedFlag());
            existingMilestones.setUpdatedOn(LocalDateTime.now());

            AcademyMilestones savedMilestones = academyMilestonesRepository.save(existingMilestones);
            return new ResponseObject(savedMilestones, "SUCCESS", HttpStatus.OK, "Academy Milestones updated successfully");
        } catch (RuntimeException e) {
            return new ResponseObject(HttpStatus.NOT_FOUND, "ERROR", e.getMessage());
        } catch (Exception e) {
            return new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR", "Failed to update academy milestones: " + e.getMessage());
        }
    }

    @Override
    public ResponseObject deleteMilestones(Long id) {
        try {
            Optional<AcademyMilestones> academyMilestonesOptional = academyMilestonesRepository.findByIdAndBitDeletedFlag(id, (byte) 0);

            if (academyMilestonesOptional.isPresent()) {
                AcademyMilestones academyMilestones = academyMilestonesOptional.get();
                academyMilestones.setBitDeletedFlag((byte) 1);
                academyMilestonesRepository.save(academyMilestones); // Save the updated entity

                return new ResponseObject(academyMilestones, "SUCCESS", HttpStatus.OK, "Academy Milestones deleted successfully");
            } else {
                return new ResponseObject(null, "ERROR", HttpStatus.NOT_FOUND, "Academy Milestones not found");
            }
        } catch (RuntimeException e) {
            return new ResponseObject(null, "ERROR", HttpStatus.NOT_FOUND, "Academy Milestones not found");
        } catch (Exception e) {
            return new ResponseObject(null, "ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while deleting the academy milestones");
        }
    }

    @Override
    public ResponseObject getAllMilestones() {
        try {
            List<AcademyMilestones> academyMilestonesList = academyMilestonesRepository.findAllActive();
            return new ResponseObject(academyMilestonesList, "SUCCESS", HttpStatus.OK, "Academy Milestones fetched successfully");
        } catch (Exception e) {
            return new ResponseObject(null, "ERROR", HttpStatus.NOT_FOUND, "Academy Milestones not found");
        }
    }
}

