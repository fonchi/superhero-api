package com.w2m.superhero.domain.repository;

import com.w2m.superhero.domain.model.SuperHero;
import java.util.List;
import java.util.Optional;

public interface SuperHeroRepository {

  Optional<SuperHero> findById(Long id);

  List<SuperHero> findAll();

  List<SuperHero> findByFilters(String name);

  SuperHero save(SuperHero superHero);

  void delete(SuperHero superHero);

}
