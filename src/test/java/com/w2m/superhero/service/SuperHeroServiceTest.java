package com.w2m.superhero.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.w2m.superhero.TestUtils;
import com.w2m.superhero.domain.SuperHero;
import com.w2m.superhero.exception.NotFoundException;
import com.w2m.superhero.repository.SuperHeroRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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

    when(superHeroRepository.findById(id)).thenReturn(null);

    assertThrows(NotFoundException.class, () -> superHeroService.findById(id));
  }

  @Test
  public void givenPageZeroAndSizeTen_whenFindAll_thenFirstTenSuperHeros() {

    int page = 0;
    int size = 10;

    List<SuperHero> superHeros = TestUtils.getSuperHeros();
    Pageable pageable = Pageable.ofSize(size);
    Page<SuperHero> superHerosPage = new PageImpl(superHeros);

    when(superHeroRepository.findAll(pageable)).thenReturn(superHerosPage);

    List<SuperHero> result = superHeroService.findAll(page, size);

    assertEquals(superHeros, result);
  }

  @Test
  public void givenValidParamsNonPersistentSuperHeros_whenFindAll_thenReturnEmptyList() {

    int page = 0;
    int size = 10;

    List<SuperHero> superHeros = Arrays.asList();
    Pageable pageable = Pageable.ofSize(size);

    when(superHeroRepository.findAll(pageable)).thenReturn(null);

    List<SuperHero> result = superHeroService.findAll(page, size);

    assertEquals(superHeros, result);
  }

}
