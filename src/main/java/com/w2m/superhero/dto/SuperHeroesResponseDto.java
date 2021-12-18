package com.w2m.superhero.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.w2m.superhero.domain.SuperHero;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SuperHeroesResponseDto {

  private int total;
  @JsonProperty("super_heroes")
  private List<SuperHeroResponseDto> superHeroes;

  public static SuperHeroesResponseDto fromEntities(List<SuperHero> superHeroes) {
    List<SuperHeroResponseDto> dtos = superHeroes.stream()
        .map(SuperHeroResponseDto::fromEntity).collect(Collectors.toList());
    return SuperHeroesResponseDto.builder().total(superHeroes.size()).superHeroes(dtos).build();
  }

}
