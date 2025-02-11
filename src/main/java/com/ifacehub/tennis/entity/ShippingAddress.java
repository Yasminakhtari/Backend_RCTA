package com.ifacehub.tennis.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShippingAddress extends Auditable<Long> {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    private Long userId;
    private Long orderId;
	private String name;
    private String email;
    private String phone;
	private String locality;
	private String address;
	private String city;
	private String state;
	private String pinCode;
	private String country;
	private String mobile;
}
