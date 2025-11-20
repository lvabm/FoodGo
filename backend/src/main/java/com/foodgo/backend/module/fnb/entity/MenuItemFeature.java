package com.foodgo.backend.module.fnb.entity;

import com.foodgo.backend.common.base.BaseIntegerEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "menu_item_feature")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItemFeature extends BaseIntegerEntity<Integer> {
    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "feature_type", nullable = false, length = 20)
    private String featureType;

    @Column(name = "value_type", length = 20)
    private String valueType;

    @Column(name = "possible_values", columnDefinition = "TEXT")
    private String possibleValues;

    @Column(name = "description", length = 255)
    private String description;
}
