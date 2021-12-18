package com.w2m.superhero.dto;

import com.w2m.superhero.domain.SuperHero;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SuperHeroRequestDto {

  private Long id;
  private String name;

  public SuperHero toEntity() {
    return SuperHero.builder()
        .id(id)
        .name(name)
        .build();
  }

}
