package com.w2m.superhero.domain.repository;

import com.w2m.superhero.domain.model.Superhero;
import java.util.List;
import java.util.Optional;

public interface SuperheroRepository {

  Optional<Superhero> findById(Long id);

  List<Superhero> findAll();

  List<Superhero> findByFilters(String name);

  Superhero save(Superhero superhero);

  void delete(Superhero superhero);

}
