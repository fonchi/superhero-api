package com.w2m.superhero.application.dto;

import com.w2m.superhero.domain.model.Superhero;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for put request superhero data
 */
@Builder
@Getter
@Setter
public class SuperheroRequestDto {

  private Long id;
  private String name;

  /**
   * Constructor to map dto to entity
   * @return
   */
  public Superhero toEntity() {
    return Superhero.builder()
        .id(id)
        .name(name)
        .build();
  }

}
