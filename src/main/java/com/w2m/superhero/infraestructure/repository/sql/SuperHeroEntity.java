package com.w2m.superhero.infraestructure.repository.sql;

import com.w2m.superhero.domain.model.SuperHero;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "super_hero")
@Getter
@NoArgsConstructor
public class SuperHeroEntity {

  @Id
  @GeneratedValue
  private Long id;
  private String name;
  @CreationTimestamp
  private Instant creationDate;
  @UpdateTimestamp
  private Instant updateDate;

  public SuperHeroEntity(SuperHero superHero) {
    id = superHero.getId();
    name = superHero.getName();
    creationDate = superHero.getCreationDate();
    updateDate = superHero.getUpdateDate();
  }

  public SuperHero toModel() {
    return SuperHero.builder()
        .id(id)
        .name(name)
        .creationDate(creationDate)
        .updateDate(updateDate)
        .build();
  }

  public static List<SuperHero> toModels(List<SuperHeroEntity> entities) {
    return entities.stream().map(SuperHeroEntity::toModel).collect(Collectors.toList());
  }

}
