package com.w2m.superhero.domain.service;

import com.w2m.superhero.domain.model.Superhero;
import java.util.List;

public interface SuperheroService {

  Superhero getSuperhero(Long id);

  List<Superhero> getSuperheroes(String name);

  Superhero updateSuperhero(Superhero superhero);

  Superhero removeSuperhero(Long id);
}
