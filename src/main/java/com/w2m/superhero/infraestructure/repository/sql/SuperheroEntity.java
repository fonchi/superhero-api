package com.w2m.superhero.infraestructure.repository.sql;

import com.w2m.superhero.domain.model.Superhero;
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

/**
 * Superhero persistence entity
 */
@Entity(name = "superhero")
@Getter
@NoArgsConstructor
public class SuperheroEntity {

  @Id
  @GeneratedValue
  private Long id;
  private String name;
  @CreationTimestamp
  private Instant creationDate;
  @UpdateTimestamp
  private Instant updateDate;

  /**
   * Constructor entity based on superhero domain model
   * @param superhero
   */
  public SuperheroEntity(Superhero superhero) {
    id = superhero.getId();
    name = superhero.getName();
    creationDate = superhero.getCreationDate();
    updateDate = superhero.getUpdateDate();
  }

  /**
   * Mapping a superhero entity to a domain model
   * @return
   */
  public Superhero toModel() {
    return Superhero.builder()
        .id(id)
        .name(name)
        .creationDate(creationDate)
        .updateDate(updateDate)
        .build();
  }

  /**
   * Mapping a list of superhero entities to a list of superhero models
   * @param entities
   * @return
   */
  public static List<Superhero> toModels(List<SuperheroEntity> entities) {
    return entities.stream().map(SuperheroEntity::toModel).collect(Collectors.toList());
  }

}
