package com.foodgo.backend.module.outlet.repository;

import com.foodgo.backend.module.outlet.entity.OutletFeatureMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OutletFeatureMappingRepository
    extends JpaRepository<OutletFeatureMapping, Integer> {

  // Tìm kiếm Mapping theo Outlet ID và Feature ID để phục vụ việc xóa
  Optional<OutletFeatureMapping> findByOutletIdAndFeatureId(UUID outletId, Integer featureId);

  // Kiểm tra sự tồn tại (tùy chọn)
  boolean existsByOutletIdAndFeatureId(UUID outletId, Integer featureId);
}
