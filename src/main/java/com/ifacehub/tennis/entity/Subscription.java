package com.ifacehub.tennis.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String endpoint;

    @Embedded
    private Keys keys;

    @Embeddable
    @Getter
    @Setter
    public static class Keys {
    	@Column(name = "p256dh", columnDefinition = "TEXT")
        private String p256dh;
        
        @Column(name = "auth", columnDefinition = "TEXT")
        private String auth;
    }
}
