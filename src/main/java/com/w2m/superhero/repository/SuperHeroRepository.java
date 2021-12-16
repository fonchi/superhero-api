package com.w2m.superhero.repository;

import com.w2m.superhero.domain.SuperHero;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperHeroRepository {

  SuperHero findById(String id);

}
