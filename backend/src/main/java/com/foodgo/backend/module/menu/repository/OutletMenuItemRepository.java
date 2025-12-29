package com.foodgo.backend.module.menu.repository;

import com.foodgo.backend.module.menu.entity.OutletMenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OutletMenuItemRepository
    extends JpaRepository<OutletMenuItem, Integer>, JpaSpecificationExecutor<OutletMenuItem> {

  // Phương thức cần thiết để kiểm tra trùng lặp
  boolean existsByOutletIdAndMenuItemId(UUID outletId, UUID menuItemId);
  
  // Tìm outlet menu items theo menuItemId và có imageUrl (chỉ lấy items chưa bị xóa)
  @org.springframework.data.jpa.repository.Query(
      "SELECT omi FROM OutletMenuItem omi " +
      "JOIN FETCH omi.menuItem mi " +
      "WHERE mi.id = :menuItemId " +
      "AND omi.imageUrl IS NOT NULL " +
      "AND omi.imageUrl != '' " +
      "AND (omi.isDeleted IS NULL OR omi.isDeleted = false) " +
      "ORDER BY omi.id ASC")
  java.util.List<OutletMenuItem> findTop5ByMenuItemIdAndImageUrlIsNotNullOrderByIdAsc(
      @org.springframework.data.repository.query.Param("menuItemId") java.util.UUID menuItemId);
}
