package com.ifacehub.tennis.service;

import com.ifacehub.tennis.requestDto.LocationDto;
import com.ifacehub.tennis.util.ResponseObject;

public interface LocationService {
    ResponseObject createLocation(LocationDto locationDto);

    ResponseObject getLocationById(Long id);

    ResponseObject updateLocation(Long id, LocationDto locationDto);

    ResponseObject updateLocationStatus(Long id, String status);

    ResponseObject getAllLocation();
}
