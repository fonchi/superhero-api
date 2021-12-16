package com.w2m.superhero.service.impl;

import static java.util.Objects.isNull;

import com.w2m.superhero.domain.SuperHero;
import com.w2m.superhero.exception.NotFoundException;
import com.w2m.superhero.repository.SuperHeroRepository;
import com.w2m.superhero.service.SuperHeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SuperHeroServiceImpl implements SuperHeroService {

  @Autowired
  private SuperHeroRepository superHeroRepository;

  @Override
  public SuperHero findById(String id) {

    SuperHero superHero = superHeroRepository.findById(id);
    if (isNull(superHero)) {
      throw new NotFoundException();
    }
    return superHero;
  }
}
