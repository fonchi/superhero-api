package com.w2m.superhero.domain.service;

import com.w2m.superhero.domain.model.Superhero;
import java.util.List;

/**
 * Interface of superhero domain service
 */
public interface SuperheroService {

  /**
   * method for get superhero by resource id
   * @param id
   * @return
   */
  Superhero getSuperhero(Long id);

  /**
   * method for get superheroes by name filter param
   * @param name
   * @return
   */
  List<Superhero> getSuperheroes(String name);

  /**
   * method for update superhero that it matches with id
   * @param superhero
   * @return
   */
  Superhero updateSuperhero(Superhero superhero);

  /**
   * method for remove superhero by resource id
   * @param id
   * @return
   */
  Superhero removeSuperhero(Long id);
}
