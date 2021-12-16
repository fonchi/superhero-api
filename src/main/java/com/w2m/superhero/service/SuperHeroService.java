package com.w2m.superhero.service;

import com.w2m.superhero.domain.SuperHero;
import java.util.List;

public interface SuperHeroService {

  SuperHero findById(Long id);

  List<SuperHero> findAll();

  List<SuperHero> searchByName(String name);

  SuperHero update(SuperHero superHero);

  SuperHero remove(Long id);
}
