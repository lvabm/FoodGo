package com.foodgo.backend.module.outlet.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Entity
@Table(name = "outlets")
@Data
public class Outlet {

  @Id
  @GeneratedValue
  @Column(columnDefinition = "UUID")
  private UUID id;

  @Column(name = "owner_id", nullable = false, columnDefinition = "UUID")
  private UUID ownerId;

  @Column(nullable = false, length = 255)
  private String name;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String address;

  @Column(name = "phone_number", length = 20)
  private String phoneNumber;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive = true;
}
