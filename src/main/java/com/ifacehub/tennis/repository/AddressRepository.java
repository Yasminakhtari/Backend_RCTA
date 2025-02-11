package com.ifacehub.tennis.repository;
import com.ifacehub.tennis.entity.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<ShippingAddress,Long>{	

}
