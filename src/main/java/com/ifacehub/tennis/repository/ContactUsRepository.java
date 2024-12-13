package com.ifacehub.tennis.repository;

import com.ifacehub.tennis.entity.ContactUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactUsRepository extends JpaRepository<ContactUs, Long> {

    Optional<ContactUs> findByIdAndBitDeletedFlag(Long id, byte b);

    @Query(value = "SELECT c FROM ContactUs c WHERE c.bitDeletedFlag=0")
    List<ContactUs> findAllActive();

}
