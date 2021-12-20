package com.w2m.superhero.infraestructure.repository.sql;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataSqlSuperheroRepository extends CrudRepository<SuperheroEntity, Long> {

  List<SuperheroEntity> findAll();

  List<SuperheroEntity> findByNameIgnoreCaseContaining(String name);

}
