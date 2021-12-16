package com.w2m.superhero.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.w2m.superhero.domain.SuperHero;
import com.w2m.superhero.exception.NotFoundException;
import com.w2m.superhero.repository.SuperHeroRepository;
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

    String id = "id";
    SuperHero superHero = SuperHero.builder().id(id).name("Batman").build();

    when(superHeroRepository.findById(id)).thenReturn(superHero);

    SuperHero result = superHeroService.findById(id);

    assertEquals(superHero, result);
  }

  @Test
  public void givenInvalidId_whenFindById_thenReturnNotFoundException() {

    String id = "nonExistentId";

    when(superHeroRepository.findById(id)).thenReturn(null);

    assertThrows(NotFoundException.class, () -> superHeroService.findById(id));
  }

}
