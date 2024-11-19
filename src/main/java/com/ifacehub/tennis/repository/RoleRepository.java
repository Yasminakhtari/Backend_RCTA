package com.ifacehub.tennis.repository;

import com.ifacehub.tennis.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long>, JpaSpecificationExecutor<Role> {
    @Query(value = "SELECT v FROM Role v WHERE v.bitDeletedFlag = 0")
    List<Role> findAllActive();
}
