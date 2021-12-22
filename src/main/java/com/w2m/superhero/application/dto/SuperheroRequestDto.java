package com.w2m.superhero.application.dto;

import com.w2m.superhero.domain.model.Superhero;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
  @NotNull
  @NotBlank
  @Size(min = 1, max = 50, message = "name must be less than 50 characters")
  private String name;

  /**
   * Constructor to map dto to entity
   */
  public Superhero toEntity() {
    return Superhero.builder()
        .id(id)
        .name(name)
        .build();
  }

}
