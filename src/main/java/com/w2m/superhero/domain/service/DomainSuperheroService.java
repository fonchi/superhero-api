package com.w2m.superhero.domain.service;

import com.w2m.superhero.application.logging.Timing;
import com.w2m.superhero.domain.exception.NotFoundException;
import com.w2m.superhero.domain.model.Superhero;
import com.w2m.superhero.domain.repository.SuperheroRepository;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DomainSuperheroService implements SuperheroService {

  private static final Logger logger = LoggerFactory.getLogger(DomainSuperheroService.class);

  @Autowired
  private SuperheroRepository superheroRepository;

  @Timing
  @Override
  public Superhero getSuperhero(Long id) {

    Optional<Superhero> superhero = superheroRepository.findById(id);
    if (!superhero.isPresent()) {
      throw new NotFoundException(String.format("Superhero not found by id = %s", id));
    }
    return superhero.get();
  }

  @Timing
  @Override
  public List<Superhero> getSuperheroes(String name) {

    if (!Strings.isEmpty(name)) {
      return superheroRepository.findByFilters(name);
    }
    return superheroRepository.findAll();
  }

  @Timing
  @Override
  public Superhero updateSuperhero(Superhero superhero) {

    //TODO
    //resource locking to avoid race conditions using a lock service implementation

    Superhero oldSH = getSuperhero(superhero.getId());
    if (oldSH.areIdempotent(superhero)) {
      logger.info("Idempotent response for superhero {}", superhero);
      return oldSH;
    }
    oldSH.setName(superhero.getName());

    Superhero updatedSH = superheroRepository.save(oldSH);

    //TODO
    //publish superhero updating event on news queue topic using a message queue producer, e.g. Kafka or RabbitMQ

    //TODO
    //post superhero update metrics through metrics collector service using metric tools like New Relic or DataDog
    return updatedSH;
  }

  @Timing
  @Override
  public Superhero removeSuperhero(Long id) {

    //TODO
    //apply sames todos of update method: lock, publish and post metrics

    Superhero superhero = getSuperhero(id);
    superheroRepository.delete(superhero);

    return superhero;
  }
}
