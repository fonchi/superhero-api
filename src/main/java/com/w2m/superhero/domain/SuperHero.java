package com.w2m.superhero.domain;

import java.time.Instant;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class SuperHero {

  @Id
  @GeneratedValue
  private Long id;
  private String name;
  private Instant creationDate;
}
