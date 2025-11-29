package com.foodgo.backend.module.user.repository;

import com.foodgo.backend.module.user.entity.UserAccount;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {
  @Query(
      "SELECT ua FROM UserAccount ua LEFT JOIN FETCH ua.userRoles ur LEFT JOIN FETCH ur.role WHERE ua.username = :username")
  Optional<UserAccount> findByUsernameWithRoles(@Param("username") String username);

  Optional<UserAccount> findByUsername(String username);

  Optional<UserAccount> findByEmail(String email);

  Boolean existsByEmail(String email);
}
