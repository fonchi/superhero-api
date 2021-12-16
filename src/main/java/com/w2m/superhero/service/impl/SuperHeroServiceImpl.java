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
  public SuperHero findById(Long id) {

    Optional<SuperHero> superHero = superHeroRepository.findById(id);
    if (!superHero.isPresent()) {
      throw new NotFoundException();
    }
    return superHero.get();
  }

  @Override
  public List<SuperHero> findAll() {

    List<SuperHero> superHeroes = superHeroRepository.findAll();
    return superHeroes;
  }

  @Override
  public List<SuperHero> searchByName(String name) {

    List<SuperHero> superHeroes = superHeroRepository.findByNameIgnoreCaseContaining(name);
    return superHeroes;
  }

  @Override
  public SuperHero update(SuperHero superHero) {

    SuperHero oldSH = findById(superHero.getId());
    oldSH.setName(superHero.getName());
    oldSH.setUpdatedDate(superHero.getUpdatedDate());

    SuperHero updatedSH = superHeroRepository.save(oldSH);
    return updatedSH;
  }
}
