package com.foodgo.backend.module.user.repository;

import com.foodgo.backend.module.user.entity.UserAccount;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {
  Optional<UserAccount> findByUsername(String username);

  Optional<UserAccount> findByEmail(String email);
}
