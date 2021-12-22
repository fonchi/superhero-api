package com.w2m.superhero.domain.repository;

import com.w2m.superhero.domain.model.Superhero;
import java.util.List;
import java.util.Optional;

/**
 * Interface of superhero domain repository
 */
public interface SuperheroRepository {

  /**
   * search method of superhero resource for returns one by id
   * @param id
   * @return
   */
  Optional<Superhero> findById(Long id);

  /**
   * search method that returns a list of all persisted superheroes
   * @return
   */
  List<Superhero> findAll();

  /**
   * search method that returns a list of all persisted superheroes than matched with the filter
   * @param name
   * @return
   */
  List<Superhero> findByFilters(String name);

  /**
   * method to save superhero resource by id
   * @param superhero
   * @return
   */
  Superhero save(Superhero superhero);

  /**
   * method to delete superhero entity by resource id
   * @param superhero
   */
  void delete(Superhero superhero);

}
