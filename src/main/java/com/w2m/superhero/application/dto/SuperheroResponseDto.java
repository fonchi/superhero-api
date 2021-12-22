package com.w2m.superhero.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.w2m.superhero.domain.model.Superhero;
import java.time.Instant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for response superhero data
 */
@Builder
@Getter
@Setter
public class SuperheroResponseDto {

  private Long id;
  private String name;
  @JsonProperty("creation_date")
  private Instant creationDate;
  @JsonProperty("update_date")
  private Instant updateDate;

  /**
   * Map superhero model to superhero response dto
   * @param superhero
   * @return
   */
  public static SuperheroResponseDto fromEntity(Superhero superhero) {
    return SuperheroResponseDto.builder()
        .id(superhero.getId())
        .name(superhero.getName())
        .creationDate(superhero.getCreationDate())
        .updateDate(superhero.getUpdateDate())
        .build();
  }
}
