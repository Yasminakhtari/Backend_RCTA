package com.ifacehub.tennis.repository;
import com.ifacehub.tennis.entity.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<ShippingAddress,Long>{	

}
