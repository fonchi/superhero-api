package com.w2m.superhero.domain;

import java.time.Instant;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SuperHero {

  @Id
  @GeneratedValue
  private String id;
  private String name;
  private Instant creationDate;
}
