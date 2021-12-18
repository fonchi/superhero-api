package com.w2m.superhero.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.w2m.superhero.domain.SuperHero;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SuperHeroRequestDto {

  private Long id;
  private String name;
  @JsonProperty("creation_date")
  private LocalDateTime creationDate;
  @JsonProperty("update_date")
  private LocalDateTime updateDate;

  public SuperHero toEntity() {
    return SuperHero.builder()
        .id(id)
        .name(name)
        .creationDate(creationDate)
        .updateDate(updateDate)
        .build();
  }

}
