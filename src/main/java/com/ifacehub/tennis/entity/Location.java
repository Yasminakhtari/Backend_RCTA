package com.ifacehub.tennis.entity;

import com.ifacehub.tennis.requestDto.LocationDto;
import com.ifacehub.tennis.util.Utils;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String locationName;
    private String address;
    private String state;
    private String city;
    private String zipCode;
    private String status;

    public static Location toEntity(LocationDto locationDto) {
        return Utils.mapper().convertValue(locationDto, Location.class);
    }
}
