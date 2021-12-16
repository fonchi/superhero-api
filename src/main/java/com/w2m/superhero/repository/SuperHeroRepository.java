package com.w2m.superhero.repository;

import com.w2m.superhero.domain.SuperHero;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperHeroRepository extends CrudRepository<SuperHero, Long> {

  List<SuperHero> findAll();
}
