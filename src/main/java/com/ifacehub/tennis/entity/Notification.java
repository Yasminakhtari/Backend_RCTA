package com.ifacehub.tennis.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
public class Notification extends Auditable<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @ElementCollection
//    @CollectionTable(name = "notification_users", joinColumns = @JoinColumn(name = "notification_id"))
//    @Column(name = "user_id")
    private List<Long> userIds;
    private String message;
    private String status;
    @Column(name = "session_id")
    private Long sessionId;
    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotificationUsers> notificationUsers;  // Link to users
}
