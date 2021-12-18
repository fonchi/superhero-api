package com.w2m.superhero.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.w2m.superhero.domain.SuperHero;
import java.time.Instant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SuperHeroResponseDto {

  private Long id;
  private String name;
  @JsonProperty("creation_date")
  private Instant creationDate;
  @JsonProperty("update_date")
  private Instant updateDate;

  public static SuperHeroResponseDto fromEntity(SuperHero superHero) {
    return SuperHeroResponseDto.builder()
        .id(superHero.getId())
        .name(superHero.getName())
        .creationDate(superHero.getCreationDate())
        .updateDate(superHero.getUpdateDate())
        .build();
  }
}
