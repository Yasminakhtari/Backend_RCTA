package com.ifacehub.tennis.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionDto {
    private Long courseId;
    private Long coachId;
    private Long locationId;
    private List<String> days;
    private String startTime;
    private String endTime;
    private int maxCapacity;
    private int maxWaitingCapacity;
    private double price;
//    private DateRangeDto dateRange;
    private Date fromDate;
    private Date toDate;
    private Long userId;
    private List<Long> playersId;
    private String status;             // Status (e.g., "Active", "Inactive")
}

