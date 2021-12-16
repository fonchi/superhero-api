package com.w2m.superhero.service.impl;

import com.w2m.superhero.domain.SuperHero;
import com.w2m.superhero.exception.NotFoundException;
import com.w2m.superhero.repository.SuperHeroRepository;
import com.w2m.superhero.service.SuperHeroService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SuperHeroServiceImpl implements SuperHeroService {

  @Autowired
  private SuperHeroRepository superHeroRepository;

  @Override
  public Optional<SuperHero> findById(Long id) {

    Optional<SuperHero> superHero = superHeroRepository.findById(id);
    if (!superHero.isPresent()) {
      throw new NotFoundException();
    }
    return superHero;
  }

  @Override
  public List<SuperHero> findAll() {

    List<SuperHero> superHeroes = superHeroRepository.findAll();
    return superHeroes;
  }

  @Override
  public List<SuperHero> findLikeName(String name) {
    return null;
  }
}
