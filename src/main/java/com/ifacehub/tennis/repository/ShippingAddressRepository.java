package com.ifacehub.tennis.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifacehub.tennis.entity.ShippingAddress;
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress,Long> {

	List<ShippingAddress> findByOrderId(Long orderId);
    List<ShippingAddress> findByUserId(Long userId);
}
