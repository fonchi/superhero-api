package com.w2m.superhero.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.w2m.superhero.TestUtils;
import com.w2m.superhero.domain.SuperHero;
import com.w2m.superhero.exception.NotFoundException;
import com.w2m.superhero.repository.SuperHeroRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class SuperHeroServiceTest {

  @Autowired
  SuperHeroService superHeroService;
  @MockBean
  SuperHeroRepository superHeroRepository;

  @Test
  public void givenValidId_whenFindById_thenReturnSuperHero() {

    Long id = 1L;
    SuperHero superHero = SuperHero.builder().id(id).name("Batman").build();

    when(superHeroRepository.findById(id)).thenReturn(Optional.of(superHero));

    Optional<SuperHero> result = superHeroService.findById(id);

    assertEquals(superHero, result.get());
  }

  @Test
  public void givenInvalidId_whenFindById_thenReturnNotFoundException() {

    Long id = -1L;

    when(superHeroRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> superHeroService.findById(id));
  }

  @Test
  public void whenFindAll_thenListAllSuperHeroes() {

    List<SuperHero> superHeroes = TestUtils.getSuperHeroes();

    when(superHeroRepository.findAll()).thenReturn(superHeroes);

    List<SuperHero> result = superHeroService.findAll();

    assertEquals(superHeroes, result);
  }

  @Test
  public void givenNameParam_whenSearchByName_thenListAllSuperHeroesThanMatched() {

    String name = "man";
    List<SuperHero> superHeroes = TestUtils.getSuperHeroes();

    when(superHeroRepository.findByNameIgnoreCaseContaining(name)).thenReturn(superHeroes);

    List<SuperHero> result = superHeroService.searchByName(name);

    assertEquals(superHeroes, result);
  }

}
