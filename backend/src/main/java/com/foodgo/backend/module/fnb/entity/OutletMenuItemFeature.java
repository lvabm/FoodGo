package com.foodgo.backend.module.fnb.entity;

import com.foodgo.backend.common.base.BaseIntegerEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "outlet_menu_item_feature", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "outlet_menu_item_id", "feature_id" })
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutletMenuItemFeature extends BaseIntegerEntity<Integer> {
    @Column(name = "value", columnDefinition = "TEXT")
    private String value;

    // 1. QUAN HỆ MANY - TO - ONE: OutletMenuItem <--> OutletMenuItemFeature
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outlet_menu_item_id", nullable = false)
    private OutletMenuItem outletMenuItem;

    // 2. QUAN HỆ MANY - TO - ONE: MenuItemFeature <--> OutletMenuItemFeature
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feature_id", nullable = false)
    private MenuItemFeature feature;
}
