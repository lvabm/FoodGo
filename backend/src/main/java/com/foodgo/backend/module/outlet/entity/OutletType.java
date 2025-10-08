package com.foodgo.backend.module.outlet.entity;

import com.foodgo.backend.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "outlet_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutletType extends BaseEntity {
    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "description", length = 255)
    private String description;
}
