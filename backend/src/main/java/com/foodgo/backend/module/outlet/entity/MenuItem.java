package com.foodgo.backend.module.outlet.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;
import java.math.BigDecimal;

@Entity
@Table(name = "menu_items")
@Data
public class MenuItem {

  @Id
  @GeneratedValue
  @Column(columnDefinition = "UUID")
  private UUID id;

  @Column(name = "outlet_id", nullable = false, columnDefinition = "UUID")
  private UUID outletId;

  @Column(nullable = false, length = 255)
  private String name;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal price;

  @Column(name = "image_url", length = 255)
  private String imageUrl;

  @Column(name = "is_available", nullable = false)
  private Boolean isAvailable = true;
}
