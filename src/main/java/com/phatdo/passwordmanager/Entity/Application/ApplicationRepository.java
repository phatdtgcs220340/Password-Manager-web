package com.phatdo.passwordmanager.Entity.Application;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    Optional<Application> findByApplicationName(String applicationName);
}
