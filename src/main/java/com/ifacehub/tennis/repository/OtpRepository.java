package com.ifacehub.tennis.repository;

import com.ifacehub.tennis.entity.OTP;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<OTP,String>{
	List<OTP> findByCreationTimeBefore(LocalDateTime expiry);
}
