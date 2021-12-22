package com.w2m.superhero.infrastructure.repository.sql;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface of relational database repository through spring data framework
 */
@Repository
public interface SpringDataSqlSuperheroRepository extends CrudRepository<SuperheroEntity, Long> {

  /**
   * returns a list of all persisted superhero entities
   * @return
   */
  List<SuperheroEntity> findAll();

  /**
   * returns a list of all persisted superhero entities than matches non case sensitive name
   * @param name
   * @return
   */
  List<SuperheroEntity> findByNameIgnoreCaseContaining(String name);

}
