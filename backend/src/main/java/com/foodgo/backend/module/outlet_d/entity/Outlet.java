package com.foodgo.backend.module.outlet_d.entity;

import com.foodgo.backend.common.base.BaseEntity;
import com.foodgo.backend.module.booking_d.entity.Booking;
import com.foodgo.backend.module.location_d.entity.District;
import com.foodgo.backend.module.review_d.entity.Review;
import com.foodgo.backend.module.user_d.entity.UserAccount;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "outlet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Outlet extends BaseEntity {
  @Column(name = "name", nullable = false, length = 255)
  private String name;

  @Column(name = "address", length = 500)
  private String address;

  @Column(name = "description", length = 1000)
  private String description;

  @Column(name = "is_active", nullable = false)
  private boolean isActive = true;

  //1. QUAN HỆ MANY - TO - ONE: Outlet <--> UserAccount
  // Outlet sở hữu quan hệ (fk_user_id_outlet)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private UserAccount owner;

  //2. QUAN HỆ MANY - TO - ONE: Outlet <--> District
  // Outlet sở hữu quan hệ (fk_district_id_outlet)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "district_id", nullable = false)
  private District district;

  //3. QUAN HỆ ONE - TO - MANY: Outlet <--> FeatureOfOutlet
  // FeatureOfOutlet sở hữu quan hệ (fk_outlet_id_feature_of_outlet)
  @OneToMany(mappedBy = "outlet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<FeatureOfOutlet> features;

  //4. QUAN HỆ ONE - TO - MANY: Outlet <--> OperatingHours
  // OperatingHours sở hữu quan hệ (fk_outlet_id_operating_hours)
  @OneToMany(mappedBy = "outlet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<OperatingHours> operatingHours;

  //5. QUAN HỆ ONE - TO - MANY: Outlet <--> Review
  // Review sở hữu quan hệ (fk_outlet_id_review)
  @OneToMany(mappedBy = "outlet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Review> reviews;

  //6. QUAN HỆ ONE - TO - MANY: Outlet <--> Booking
  // Booking sở hữu quan hệ (fk_outlet_id_booking)
  @OneToMany(mappedBy = "outlet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Booking> bookings;

  //7. QUAN HỆ MANY - TO - ONE: Outlet <--> OutletType
  // Booking sở hữu quan hệ (fk_outlet_id_booking)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "type_id", nullable = false)
  private OutletType type;

}
