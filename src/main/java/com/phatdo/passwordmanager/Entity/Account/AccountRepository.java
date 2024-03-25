package com.phatdo.passwordmanager.Entity.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUserIdAndApplicationId(Integer userId, Integer applicationId);
}
