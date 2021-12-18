package com.w2m.superhero.domain.service;

import com.w2m.superhero.domain.model.SuperHero;
import com.w2m.superhero.domain.exception.NotFoundException;
import com.w2m.superhero.domain.repository.SuperHeroRepository;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DomainSuperHeroService implements SuperHeroService {

  @Autowired
  private SuperHeroRepository superHeroRepository;

  @Override
  public SuperHero getSuperHero(Long id) {

    Optional<SuperHero> superHero = superHeroRepository.findById(id);
    if (!superHero.isPresent()) {
      throw new NotFoundException();
    }
    return superHero.get();
  }

  @Override
  public List<SuperHero> getSuperHeroes(String name) {

    if (!Strings.isEmpty(name)) {
      return superHeroRepository.findByFilters(name);
    }
    return superHeroRepository.findAll();
  }

  @Override
  public SuperHero updateSuperHero(SuperHero superHero) {

    //TODO
    //resource locking to avoid race conditions using a lock service implementation

    SuperHero oldSH = getSuperHero(superHero.getId());
    if (oldSH.areIdempotent(superHero)) {
      return oldSH;
    }
    oldSH.setName(superHero.getName());

    SuperHero updatedSH = superHeroRepository.save(oldSH);

    //TODO
    //publish superhero updating event on news queue topic using a message queue producer (e.g. Kafka)

    //TODO
    //post superhero update metrics through metrics collector service using metric tools like New Relic or DataDog
    return updatedSH;
  }

  @Override
  public SuperHero removeSuperHero(Long id) {

    //TODO
    //apply sames todos of update method: lock, publish and post metrics

    SuperHero superHero = getSuperHero(id);
    superHeroRepository.delete(superHero);

    return superHero;
  }
}
