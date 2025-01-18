package com.ifacehub.tennis.serviceImpl;

import com.ifacehub.tennis.entity.Location;
import com.ifacehub.tennis.repository.LocationRepository;
import com.ifacehub.tennis.requestDto.LocationDto;
import com.ifacehub.tennis.service.LocationService;
import com.ifacehub.tennis.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {
    @Autowired
    private LocationRepository locationRepository;
    @Override
    public ResponseObject createLocation(LocationDto locationDto) {
        try{
            Location location = Location.toEntity(locationDto);
            location.setStatus("active");
            Location savedLocation = locationRepository.save(location);
            return new ResponseObject(savedLocation, "SUCCESS", HttpStatus.OK, "Location saved successfully");
        } catch (Exception e){
            return new ResponseObject(HttpStatus.BAD_REQUEST, "ERROR", "Failed to saved location: " + e.getMessage());
        }
    }

    @Override
    public ResponseObject getLocationById(Long id) {
        try {
            Location location = locationRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Location not found with ID: " + id));
            return new ResponseObject(location, "SUCCESS", HttpStatus.OK, "Location fetched successfully");
        } catch (Exception e) {
            return new ResponseObject(HttpStatus.NOT_FOUND, "ERROR", "Failed to fetch location: " + e.getMessage());
        }
    }

    @Override
    public ResponseObject updateLocation(Long id, LocationDto locationDto) {
        try {
            Location existingLocation = locationRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Location not found with ID: " + id));

            // Update location details
            existingLocation.setLocationName(locationDto.getLocationName());
            existingLocation.setAddress(locationDto.getAddress());
            existingLocation.setCity(locationDto.getCity());
            existingLocation.setState(locationDto.getState());
            existingLocation.setZipCode(locationDto.getZipCode());

            Location updatedLocation = locationRepository.save(existingLocation);
            return new ResponseObject(updatedLocation, "SUCCESS", HttpStatus.OK, "Location updated successfully");
        } catch (Exception e) {
            return new ResponseObject(HttpStatus.BAD_REQUEST, "ERROR", "Failed to update location: " + e.getMessage());
        }
    }

    @Override
    public ResponseObject updateLocationStatus(Long id, String status) {
        try {
            Location existingLocation = locationRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Location not found with ID: " + id));

            // Toggle status between 'active' and 'inactive'
            if ("active".equalsIgnoreCase(existingLocation.getStatus())) {
                existingLocation.setStatus("inactive");
            } else if ("inactive".equalsIgnoreCase(existingLocation.getStatus())) {
                existingLocation.setStatus("active");
            } else {
                throw new RuntimeException("Invalid status value: " + existingLocation.getStatus());
            }

            Location updatedLocation = locationRepository.save(existingLocation);
            return new ResponseObject(updatedLocation, "SUCCESS", HttpStatus.OK, "Location updated successfully");
        } catch (Exception e) {
            return new ResponseObject(HttpStatus.BAD_REQUEST, "ERROR", "Failed to update location: " + e.getMessage());
        }
    }

    @Override
    public ResponseObject getAllLocation() {
        try {
            List<Location> locationList = locationRepository.findAllByOrderByIdDesc();

            return new ResponseObject(locationList, "SUCCESS", HttpStatus.OK, "Location fetched successfully");
        } catch (Exception e) {
            return new ResponseObject(null, "ERROR", HttpStatus.NOT_FOUND, "Location not found");
        }
    }

}
