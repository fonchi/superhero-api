package com.w2m.superhero.infraestructure.repository.sql;

import com.w2m.superhero.domain.model.SuperHero;
import com.w2m.superhero.domain.repository.SuperHeroRepository;
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
public class SqlDbSuperHeroRepository implements SuperHeroRepository {

  @Autowired
  private SpringDataSqlSuperHeroRepository sqlDbSuperHeroRepository;

  @Override
  @Cacheable
  public Optional<SuperHero> findById(Long id) {
    Optional<SuperHeroEntity> entity = sqlDbSuperHeroRepository.findById(id);
    if (entity.isPresent()) {
      return Optional.of(entity.get().toModel());
    }
    return Optional.empty();
  }

  @Override
  public List<SuperHero> findAll() {
    List<SuperHeroEntity> entity = sqlDbSuperHeroRepository.findAll();
    return SuperHeroEntity.toModels(entity);
  }

  @Override
  public List<SuperHero> findByFilters(String name) {
    List<SuperHeroEntity> entities = sqlDbSuperHeroRepository.findByNameIgnoreCaseContaining(name);
    return SuperHeroEntity.toModels(entities);
  }

  @Override
  @CachePut(key = "#superHero.id")
  public SuperHero save(SuperHero superHero) {
    SuperHeroEntity entity = sqlDbSuperHeroRepository.save(new SuperHeroEntity(superHero));
    return entity.toModel();
  }

  @Override
  @CacheEvict
  public void delete(SuperHero superHero) {
    sqlDbSuperHeroRepository.delete(new SuperHeroEntity(superHero));
  }
}
