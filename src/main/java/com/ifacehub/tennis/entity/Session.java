package com.ifacehub.tennis.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ifacehub.tennis.requestDto.SessionDto;
import com.ifacehub.tennis.util.Utils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long courseId;
    private Long coachId;
    private Long locationId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date fromDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date toDate;
    private List<String> days;
    private String startTime;
    private String endTime;
    private int maxCapacity;
    private int maxWaitingCapacity;
    private double price;
    private Long userId;
    private List<Long> playersId;
    private String status;             // Status (e.g., "Inprogress", "Completed")
    @Transient
    private String coachName;
    @Transient
    private String LocationName;
    @Transient
    private String category;
    @Transient
    private String subCategory;

    public static Session toEntity(SessionDto sessionDto) {
        return Utils.mapper().convertValue(sessionDto, Session.class);
//        Session session = Utils.mapper().convertValue(sessionDto, Session.class);
//
//        // Explicitly map the fromDate and toDate fields
//        if (sessionDto.getDateRange() != null) {
//            session.setFromDate(sessionDto.getDateRange().getFrom());
//            session.setToDate(sessionDto.getDateRange().getTo());
//        }
//
//        return session;
    }
}
