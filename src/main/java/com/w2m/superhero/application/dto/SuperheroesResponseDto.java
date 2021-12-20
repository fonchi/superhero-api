package com.w2m.superhero.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.w2m.superhero.domain.model.Superhero;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SuperheroesResponseDto {

  private int total;
  @JsonProperty("superheroes")
  private List<SuperheroResponseDto> superheroes;

  public static SuperheroesResponseDto fromEntities(List<Superhero> superheroes) {
    List<SuperheroResponseDto> dtos = superheroes.stream()
        .map(SuperheroResponseDto::fromEntity).collect(Collectors.toList());
    return SuperheroesResponseDto.builder().total(superheroes.size()).superheroes(dtos).build();
  }

}
