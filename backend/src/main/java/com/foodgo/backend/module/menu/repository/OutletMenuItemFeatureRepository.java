package com.foodgo.backend.module.menu.repository;

import com.foodgo.backend.module.menu.entity.OutletMenuItemFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OutletMenuItemFeatureRepository
    extends JpaRepository<OutletMenuItemFeature, Integer> {

  // Tìm kiếm Entity Mapping theo cặp ID (cần cho việc xóa)
  Optional<OutletMenuItemFeature> findByOutletMenuItemIdAndFeatureId(
      Integer outletMenuItemId, Integer featureId);

  boolean existsByOutletMenuItemIdAndFeatureId(Integer outletMenuItemId, Integer featureId);

  // Lấy tất cả Feature của một OutletMenuItem (cho public/read-only)
  List<OutletMenuItemFeature> findAllByOutletMenuItemId(Integer outletMenuItemId);
}
