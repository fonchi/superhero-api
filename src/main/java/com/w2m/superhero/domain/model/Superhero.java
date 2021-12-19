package com.w2m.superhero.domain.model;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Superhero {

  private Long id;
  private String name;
  private Instant creationDate;
  private Instant updateDate;

  public boolean areIdempotent(Superhero superhero) {
    return id.equals(superhero.getId()) && name.equals(superhero.getName());
  }
}
