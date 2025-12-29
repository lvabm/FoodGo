package com.foodgo.backend.module.owner.repository;

import com.foodgo.backend.module.owner.entity.OwnerRegistrationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OwnerRegistrationRequestRepository
    extends JpaRepository<OwnerRegistrationRequest, UUID>,
        JpaSpecificationExecutor<OwnerRegistrationRequest> {

  List<OwnerRegistrationRequest> findByUserId(UUID userId);

  Optional<OwnerRegistrationRequest> findByOutletId(UUID outletId);

  List<OwnerRegistrationRequest> findByStatus(OwnerRegistrationRequest.RequestStatus status);
}

