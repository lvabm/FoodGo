package com.foodgo.backend.module.advertisement.entity;

import com.foodgo.backend.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "advertisement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Advertisement extends BaseEntity {
    @Column(name = "outlet_id", nullable = false)
    private UUID outletId;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "content", length = 1000)
    private String content;

    @Column(name = "image_url", length = 255)
    private String imageUrl;
}
