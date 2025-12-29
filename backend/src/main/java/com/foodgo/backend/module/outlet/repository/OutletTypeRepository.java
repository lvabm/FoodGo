package com.foodgo.backend.module.outlet.repository;

import com.foodgo.backend.module.outlet.entity.OutletType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OutletTypeRepository
    extends JpaRepository<OutletType, Integer>, JpaSpecificationExecutor<OutletType> {

}
