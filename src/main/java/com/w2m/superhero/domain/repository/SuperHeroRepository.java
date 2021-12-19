package com.w2m.superhero.domain.repository;

import com.w2m.superhero.domain.model.SuperHero;
import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;

public interface SuperHeroRepository {

  @Cacheable("superhero")
  Optional<SuperHero> findById(Long id);

  @Cacheable("superhero")
  List<SuperHero> findAll();

  @Cacheable("superhero")
  List<SuperHero> findByFilters(String name);

  SuperHero save(SuperHero superHero);

  void delete(SuperHero superHero);

}
