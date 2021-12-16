package com.w2m.superhero.service;

import com.w2m.superhero.domain.SuperHero;
import java.util.List;
import java.util.Optional;

public interface SuperHeroService {

  Optional<SuperHero> findById(Long id);

  List<SuperHero> findAll();

  List<SuperHero> searchByName(String name);

  SuperHero update(SuperHero superHero);
}
