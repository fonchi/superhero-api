package com.w2m.superhero.domain.model;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SuperHero {

  private Long id;
  private String name;
  private Instant creationDate;
  private Instant updateDate;

  public boolean areIdempotent(SuperHero superHero) {
    return id.equals(superHero.getId()) && name.equals(superHero.getName());
  }
}
