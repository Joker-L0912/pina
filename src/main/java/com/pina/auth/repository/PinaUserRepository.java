package com.pina.auth.repository;

import com.pina.auth.entity.PinaUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PinaUserRepository extends JpaRepository<PinaUser, Integer>, JpaSpecificationExecutor<PinaUser> {

    Optional<PinaUser> findByUsername(String username);
}
