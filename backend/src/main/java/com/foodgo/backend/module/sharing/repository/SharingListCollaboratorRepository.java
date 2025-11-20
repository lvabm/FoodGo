package com.foodgo.backend.module.sharing.repository;

import com.foodgo.backend.module.sharing.entity.SharingListCollaborator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SharingListCollaboratorRepository
        extends JpaRepository<SharingListCollaborator, Long> {
}
