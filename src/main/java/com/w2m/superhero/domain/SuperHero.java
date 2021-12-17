package com.w2m.superhero.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SuperHero {

  @Id
  @GeneratedValue
  private Long id;
  private String name;
  private LocalDateTime creationDate;
  private LocalDateTime updatedDate;

  public boolean areIdempotent(SuperHero superHero) {
    return id.equals(superHero.getId()) && name.equals(superHero.getName());
  }
}
