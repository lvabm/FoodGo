package com.foodgo.backend.module.user.repository;

import com.foodgo.backend.module.user.entity.Profile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfileRepository
    extends JpaRepository<Profile, Long>, JpaSpecificationExecutor<Profile> {

  @EntityGraph(attributePaths = {"country"})
  Optional<Profile> findByUserAccountId(UUID userId);
}
