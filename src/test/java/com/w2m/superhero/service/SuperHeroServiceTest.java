package com.w2m.superhero.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.w2m.superhero.TestUtils;
import com.w2m.superhero.domain.SuperHero;
import com.w2m.superhero.exception.NotFoundException;
import com.w2m.superhero.repository.SuperHeroRepository;
import java.time.LocalDateTime;
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

    SuperHero result = superHeroService.findById(id);

    assertEquals(superHero, result);
  }

  @Test
  public void givenInvalidId_whenFindById_thenThrowNotFoundException() {

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

  @Test
  public void givenNewSuperHeroInfo_whenUpdate_thenSuperHeroUpdated() {

    LocalDateTime updatedDate = LocalDateTime.of(2021, 12, 16, 00, 00, 00);
    LocalDateTime creationDate = LocalDateTime.of(2021, 11, 01, 00, 00, 00);
    SuperHero newSH = SuperHero.builder().id(1L).name("Robin").updatedDate(updatedDate).build();
    SuperHero oldSH = SuperHero.builder().id(1L).name("Batman").creationDate(creationDate).build();
    SuperHero updatedSH = SuperHero.builder().id(1L).name("Robin").creationDate(creationDate)
        .updatedDate(updatedDate).build();

    when(superHeroRepository.findById(newSH.getId())).thenReturn(Optional.of(oldSH));
    when(superHeroRepository.save(updatedSH)).thenReturn(updatedSH);

    SuperHero result = superHeroService.update(newSH);

    assertEquals(updatedSH, result);
    verify(superHeroRepository, times(1)).findById(newSH.getId());
    verify(superHeroRepository, times(1)).save(updatedSH);
  }

  @Test
  public void givenIdempotentUpdate_whenUpdate_thenReturnByIdempotency() {

    SuperHero newSH = SuperHero.builder().id(1L).name("Robin").build();
    SuperHero oldSH = SuperHero.builder().id(1L).name("Robin").build();

    when(superHeroRepository.findById(newSH.getId())).thenReturn(Optional.of(oldSH));

    SuperHero result = superHeroService.update(newSH);

    assertEquals(oldSH, result);
    verify(superHeroRepository, only()).findById(newSH.getId());
  }

  @Test
  public void givenAnValidSuperHeroId_whenRemove_thenSuccessDeleted() {

    SuperHero superHero = TestUtils.getSuperHeroes().get(0);
    Long id = superHero.getId();

    when(superHeroRepository.findById(id)).thenReturn(Optional.of(superHero));
    doNothing().when(superHeroRepository).delete(superHero);

    SuperHero result = superHeroService.remove(id);

    assertEquals(superHero, result);
  }

  @Test
  public void givenAnInvalidSuperHeroId_whenRemove_thenThrowNotFoundException() {

    Long id = -1L;

    when(superHeroRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> superHeroService.findById(id));
  }
}
