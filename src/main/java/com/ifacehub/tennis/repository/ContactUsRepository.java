package com.ifacehub.tennis.repository;

import com.ifacehub.tennis.entity.ContactUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactUsRepository extends JpaRepository<ContactUs, Long> {

    Optional<ContactUs> findByIdAndBitDeletedFlag(Long id, byte b);
//    @Query(value = "ContactUs c where c.email=:email AND c.bitDeletedFlag=0")
    @Query(value = "SELECT v FROM ContactUs v WHERE v.email=:email")
    Optional<ContactUs> findByEmailAndBitDeletedFlag(String email, byte b);
}
