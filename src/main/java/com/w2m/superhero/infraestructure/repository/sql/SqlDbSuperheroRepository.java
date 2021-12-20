package com.w2m.superhero.infraestructure.repository.sql;

import com.w2m.superhero.domain.model.Superhero;
import com.w2m.superhero.domain.repository.SuperheroRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@CacheConfig(cacheNames = "superhero-cache")
public class SqlDbSuperheroRepository implements SuperheroRepository {

  @Autowired
  private SpringDataSqlSuperheroRepository sqlDbSuperheroRepository;

  @Override
  @Cacheable
  public Optional<Superhero> findById(Long id) {
    Optional<SuperheroEntity> entity = sqlDbSuperheroRepository.findById(id);
    if (entity.isPresent()) {
      Superhero superhero = entity.get().toModel();
      return Optional.of(superhero);
    }
    return Optional.empty();
  }

  @Override
  public List<Superhero> findAll() {
    List<SuperheroEntity> entity = sqlDbSuperheroRepository.findAll();
    return SuperheroEntity.toModels(entity);
  }

  @Override
  public List<Superhero> findByFilters(String name) {
    List<SuperheroEntity> entities = sqlDbSuperheroRepository.findByNameIgnoreCaseContaining(name);
    return SuperheroEntity.toModels(entities);
  }

  @Override
  @CachePut(key = "#superhero.id")
  public Superhero save(Superhero superhero) {
    SuperheroEntity entity = sqlDbSuperheroRepository.save(new SuperheroEntity(superhero));
    return entity.toModel();
  }

  @Override
  @CacheEvict(key = "#superhero.id")
  public void delete(Superhero superhero) {
    sqlDbSuperheroRepository.delete(new SuperheroEntity(superhero));
  }
}
