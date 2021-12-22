package com.w2m.superhero.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.w2m.superhero.domain.model.Superhero;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for return a list of superheroes
 */
@Builder
@Getter
@Setter
public class SuperheroesResponseDto {

  private int total;
  @JsonProperty("superheroes")
  private List<SuperheroResponseDto> superheroes;

  /**
   * map list of superheroes to superheroes response dto
   * @param superheroes
   * @return
   */
  public static SuperheroesResponseDto fromEntities(List<Superhero> superheroes) {
    List<SuperheroResponseDto> dtos = superheroes.stream()
        .map(SuperheroResponseDto::fromEntity).collect(Collectors.toList());
    return SuperheroesResponseDto.builder().total(superheroes.size()).superheroes(dtos).build();
  }

}
