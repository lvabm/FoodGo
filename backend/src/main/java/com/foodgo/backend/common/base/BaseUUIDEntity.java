package com.foodgo.backend.common.base;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public abstract class BaseUUIDEntity implements Serializable {
  @Id
  @UuidGenerator(style = UuidGenerator.Style.AUTO)
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @Column(nullable = false)
  private Boolean isDeleted = false;
}
