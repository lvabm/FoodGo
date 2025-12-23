package com.foodgo.backend.module.owner.service;

import com.foodgo.backend.module.owner.dto.OwnerDashboardStats;

import java.util.UUID;

public interface OwnerDashboardService {
  OwnerDashboardStats getOwnerStats();
  OwnerDashboardStats getOutletStats(UUID outletId);
}
