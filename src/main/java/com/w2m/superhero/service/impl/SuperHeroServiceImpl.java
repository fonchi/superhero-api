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

    //TODO
    //resource locking to avoid race conditions using a lock service implementation

    SuperHero oldSH = findById(superHero.getId());
    if (oldSH.areIdempotent(superHero)) {
      return oldSH;
    }
    oldSH.setName(superHero.getName());
    oldSH.setUpdatedDate(superHero.getUpdatedDate());

    SuperHero updatedSH = superHeroRepository.save(oldSH);

    //TODO
    //publish superhero updating event on news queue topic using a message queue producer (e.g. Kafka)

    //TODO
    //post superhero update metrics through metrics collector service using metric tools like New Relic or DataDog
    return updatedSH;
  }

  @Override
  public SuperHero remove(Long id) {

    SuperHero superHero = findById(id);
    superHeroRepository.delete(superHero);

    return superHero;
  }
}
