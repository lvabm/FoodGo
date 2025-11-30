package com.foodgo.backend.module.user.repository;

import com.foodgo.backend.module.user.entity.UserAccount;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserAccountRepository
    extends JpaRepository<UserAccount, UUID>, JpaSpecificationExecutor<UserAccount> {

  // Tải đồng thời quan hệ Role
  @EntityGraph(attributePaths = {"role"})
  Optional<UserAccount> findByUsername(String username);

  // Tải đồng thời quan hệ Role
  @EntityGraph(attributePaths = {"role"})
  Optional<UserAccount> findByEmail(String email);

  Boolean existsByEmail(String email);
}
