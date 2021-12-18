package com.w2m.superhero.domain;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
  @CreationTimestamp
  private Instant creationDate;
  @UpdateTimestamp
  private Instant updateDate;

  public boolean areIdempotent(SuperHero superHero) {
    return id.equals(superHero.getId()) && name.equals(superHero.getName());
  }
}
