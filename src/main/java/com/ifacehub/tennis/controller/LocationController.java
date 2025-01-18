package com.ifacehub.tennis.controller;

import com.ifacehub.tennis.requestDto.LocationDto;
import com.ifacehub.tennis.service.LocationService;
import com.ifacehub.tennis.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/location")
public class LocationController {
    @Autowired
    private LocationService locationService;

    //Save
    @PostMapping("/createLocation")
    public ResponseEntity<ResponseObject> createLocation(@RequestBody LocationDto locationDto){
        ResponseObject response = locationService.createLocation(locationDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get by ID
    @GetMapping
    public ResponseEntity<ResponseObject> getLocationById(
            @RequestParam Long id
    ) {
        ResponseObject response = locationService.getLocationById(id);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    // Update
    @PutMapping("/updateLocation")
    public ResponseEntity<ResponseObject> updateLocation(
            @RequestParam Long id,
            @RequestBody LocationDto locationDto) {
        ResponseObject response = locationService.updateLocation(id, locationDto);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    // Update Status
    @PutMapping("/updateStatus")
    public ResponseEntity<ResponseObject> updateLocationStatus(
            @RequestParam Long id,
            @RequestParam String status
    ) {
        ResponseObject response = locationService.updateLocationStatus(id,status);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("/getAllLocation")
    public ResponseEntity<ResponseObject> getAllLocation() {
        ResponseObject response = locationService.getAllLocation();
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
