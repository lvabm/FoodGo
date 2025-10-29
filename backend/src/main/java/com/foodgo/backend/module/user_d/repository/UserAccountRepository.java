package com.foodgo.backend.module.user_d.repository;

import com.foodgo.backend.module.user_d.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
  Optional<UserAccount> findByUsername(String username);

  Optional<UserAccount> findByEmail(String email);
}
