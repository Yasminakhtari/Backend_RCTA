package com.ifacehub.tennis.util;

import java.security.SecureRandom;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Utils {
    public static ObjectMapper mapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
    
    
    public static String  generateOtp() {
		 int otpLength = 6;
		 
		 SecureRandom random = new SecureRandom();
		 
		 StringBuilder otp = new StringBuilder(otpLength);
		 
		 for(int i=0 ; i<otpLength ;i++) {
			 otp.append(random.nextInt(10));
		 }
		 
		 return otp.toString();
	 }
}
