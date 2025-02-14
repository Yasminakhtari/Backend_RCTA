package com.ifacehub.tennis.requestDto;



import lombok.Data;

@Data
public class OrderRequestDto {
    
    private Long userId;
    private Long orderId;
	private String name;
    private String email;
    private String phone;
    private String addressLine1; 
    private String addressLine2; 
    private String city;
    private String state;
    private String zipCode;
	
	
}
