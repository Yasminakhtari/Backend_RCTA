package com.ifacehub.tennis.controller;

import com.ifacehub.tennis.requestDto.AcademyMilestoneDto;
import com.ifacehub.tennis.service.AcademyMilestonesService;
import com.ifacehub.tennis.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/milestones")
@CrossOrigin(origins = "*")
public class AcademyMilestonesController {
    @Autowired
    private AcademyMilestonesService academyMilestonesService;
    @PostMapping
    public ResponseEntity<ResponseObject> createMilestones(@RequestBody AcademyMilestoneDto dto) {
        ResponseObject response = academyMilestonesService.createMilestones(dto);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getMilestonesById(@PathVariable Long id) {
        ResponseObject response = academyMilestonesService.getMilestonesById(id);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateMilestones(@PathVariable Long id, @RequestBody AcademyMilestoneDto academyMilestoneDto) {
        ResponseObject response = academyMilestonesService.updateMilestones(id, academyMilestoneDto);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteMilestones(@PathVariable Long id) {
        ResponseObject response = academyMilestonesService.deleteMilestones(id);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
    @GetMapping
    public ResponseEntity<ResponseObject> getAllMilestones() {
        ResponseObject response = academyMilestonesService.getAllMilestones();
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
