package com.foodgo.backend.module.outlet.entity;

import com.foodgo.backend.common.base.dto.BaseUUIDEntity;
import com.foodgo.backend.module.booking.entity.Booking;
import com.foodgo.backend.module.menu.entity.OutletMenuItem;
import com.foodgo.backend.module.location.entity.District;
import com.foodgo.backend.module.review.entity.Review;
import com.foodgo.backend.module.user.entity.UserAccount;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "outlet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Outlet extends BaseUUIDEntity {

  @Column(name = "name", nullable = false, length = 255)
  private String name;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @Column(name = "address", nullable = false, columnDefinition = "TEXT")
  private String address;

  @Column(name = "email", length = 255)
  private String email;

  @Column(name = "phone_number", length = 20)
  private String phoneNumber;

  @Column(name = "website", length = 255)
  private String website;

  @Column(name = "latitude", precision = 10, scale = 8)
  private BigDecimal latitude;

  @Column(name = "longitude", precision = 11, scale = 8)
  private BigDecimal longitude;

  @Column(name = "price_range", length = 20)
  private String priceRange;

  @Column(name = "capacity")
  private Integer capacity;

  @Column(name = "is_active", nullable = false)
  @Builder.Default
  private boolean isActive = true;

  @Column(name = "average_rating", precision = 3, scale = 2)
  private BigDecimal averageRating;

  @Column(name = "total_reviews")
  private Integer totalReviews;

  // 1. QUAN HỆ MANY - TO - ONE: UserAccount <--> Outlet
  // Outlet sở hữu quan hệ (fk_user_account_id_outlet)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_id", nullable = false)
  private UserAccount owner;

  // 2. QUAN HỆ MANY - TO - ONE: Outlet <--> District
  // District sở hữu quan hệ (fk_district_id_outlet)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "district_id", nullable = false)
  private District district;

  // 3. QUAN HỆ ONE - TO - MANY: Outlet <--> OutletFeatureMapping
  // OutletFeatureMapping sở hữu quan hệ (fk_outlet_id_outlet_has_feature)
  @OneToMany(mappedBy = "outlet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<OutletFeatureMapping> outletFeatureMappings;

  // 4. QUAN HỆ ONE - TO - MANY: Outlet <--> OperatingHours
  // OperatingHours sở hữu quan hệ (fk_outlet_id_operating_hours)
  @OneToMany(mappedBy = "outlet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<OperatingHours> operatingHours;

  // 5. QUAN HỆ ONE - TO - MANY: Outlet <--> Review
  // Review sở hữu quan hệ (fk_outlet_id_review)
  @OneToMany(mappedBy = "outlet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<Review> reviews;

  // 6. QUAN HỆ ONE - TO - MANY: Outlet <--> Booking
  // Booking sở hữu quan hệ (fk_outlet_id_booking)
  @OneToMany(mappedBy = "outlet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<Booking> bookings;

  // 7. QUAN HỆ ONE - TO - MANY: Outlet <--> OutletImage
  // OutletImage sở hữu quan hệ (fk_outlet_id_outlet_image)
  @OneToMany(mappedBy = "outlet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<OutletImage> outletImages;

  // 8. QUAN HỆ ONE - TO - MANY: Outlet <--> OutletMenuItem
  // OutletMenuItem sở hữu quan hệ (fk_outlet_id_outlet_menu_item)
  @OneToMany(mappedBy = "outlet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<OutletMenuItem> outletMenuItems;

  // 9. QUAN HỆ MANY - TO - ONE: OutletType <--> Outlet
  // Outlet sở hữu quan hệ (fk_outlet_type_id_outlet)
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "type_id", nullable = false)
  private OutletType type;
}
