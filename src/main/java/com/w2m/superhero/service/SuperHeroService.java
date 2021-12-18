package com.w2m.superhero.service;

import com.w2m.superhero.domain.SuperHero;
import java.util.List;

public interface SuperHeroService {

  SuperHero getSuperHero(Long id);

  List<SuperHero> getSuperHeroes(String name);

  SuperHero updateSuperHero(SuperHero superHero);

  SuperHero removeSuperHero(Long id);
}
