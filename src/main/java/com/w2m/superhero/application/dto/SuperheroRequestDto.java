package com.w2m.superhero.application.dto;

import com.w2m.superhero.domain.model.Superhero;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SuperheroRequestDto {

  private Long id;
  private String name;

  public Superhero toEntity() {
    return Superhero.builder()
        .id(id)
        .name(name)
        .build();
  }

}
