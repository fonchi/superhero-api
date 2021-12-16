package com.w2m.superhero.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.w2m.superhero.domain.SuperHero;
import com.w2m.superhero.repository.SuperHeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SuperHeroServiceTest {

  @Autowired
  SuperHeroService superHeroService;
  @Autowired
  SuperHeroRepository superHeroRepository;

  public void givenValidId_whenFindById_thenReturnSuperHero() {

    //Setup
    String id = "id";
    SuperHero superHero = SuperHero.builder().id(id).name("Batman").build();

    //Mock
    when(superHeroRepository.findById(id)).thenReturn(superHero);

    //Execute
    SuperHero result = superHeroService.findById(id);

    //Verify
    assertEquals(superHero, result);
  }

}
