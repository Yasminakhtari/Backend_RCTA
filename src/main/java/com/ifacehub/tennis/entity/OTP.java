package com.ifacehub.tennis.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Id;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OTP {
	
	@Id
	private String email;

	private String otpCode;
	private LocalDateTime creationTime;
}
