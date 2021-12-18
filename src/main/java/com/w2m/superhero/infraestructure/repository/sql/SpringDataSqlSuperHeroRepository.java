package com.w2m.superhero.infraestructure.repository.sql;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataSqlSuperHeroRepository extends CrudRepository<SuperHeroEntity, Long> {

  List<SuperHeroEntity> findAll();

  List<SuperHeroEntity> findByNameIgnoreCaseContaining(String name);

}
