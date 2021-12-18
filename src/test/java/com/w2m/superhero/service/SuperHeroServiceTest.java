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
    SuperHero superHero = TestUtils.createSuperHero();

    when(superHeroRepository.findById(id)).thenReturn(Optional.of(superHero));

    SuperHero result = superHeroService.getSuperHero(id);

    assertEquals(superHero, result);
  }

  @Test
  public void givenInvalidId_whenFindById_thenThrowNotFoundException() {

    Long id = -1L;

    when(superHeroRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> superHeroService.getSuperHero(id));
  }

  @Test
  public void whenFindAll_thenListAllSuperHeroes() {

    List<SuperHero> superHeroes = TestUtils.createSuperHeroes();

    when(superHeroRepository.findAll()).thenReturn(superHeroes);

    List<SuperHero> result = superHeroService.getSuperHeroes(null);

    assertEquals(superHeroes, result);
  }

  @Test
  public void givenNameParam_whenSearchByName_thenListAllSuperHeroesThanMatched() {

    String name = "man";
    List<SuperHero> superHeroes = TestUtils.createSuperHeroes();

    when(superHeroRepository.findByNameIgnoreCaseContaining(name)).thenReturn(superHeroes);

    List<SuperHero> result = superHeroService.getSuperHeroes(name);

    assertEquals(superHeroes, result);
  }

  @Test
  public void givenNewSuperHeroInfo_whenUpdate_thenSuperHeroUpdated() {

    SuperHero newSH = TestUtils.createSuperHero("Robin");
    newSH.setCreationDate(null);
    newSH.setUpdateDate(null);
    SuperHero oldSH = TestUtils.createSuperHero("Batman");
    SuperHero updatedSH = TestUtils.createSuperHero("Robin");

    when(superHeroRepository.findById(newSH.getId())).thenReturn(Optional.of(oldSH));
    when(superHeroRepository.save(updatedSH)).thenReturn(updatedSH);

    SuperHero result = superHeroService.updateSuperHero(newSH);

    assertEquals(updatedSH, result);
    verify(superHeroRepository, times(1)).findById(newSH.getId());
    verify(superHeroRepository, times(1)).save(updatedSH);
  }

  @Test
  public void givenIdempotentUpdate_whenUpdate_thenReturnByIdempotency() {

    SuperHero newSH = TestUtils.createSuperHero("Robin");
    SuperHero oldSH = TestUtils.createSuperHero("Robin");

    when(superHeroRepository.findById(newSH.getId())).thenReturn(Optional.of(oldSH));

    SuperHero result = superHeroService.updateSuperHero(newSH);

    assertEquals(oldSH, result);
    verify(superHeroRepository, only()).findById(newSH.getId());
  }

  @Test
  public void givenAnValidSuperHeroId_whenRemove_thenSuccessDeleted() {

    SuperHero superHero = TestUtils.createSuperHeroes().get(0);
    Long id = superHero.getId();

    when(superHeroRepository.findById(id)).thenReturn(Optional.of(superHero));
    doNothing().when(superHeroRepository).delete(superHero);

    SuperHero result = superHeroService.removeSuperHero(id);

    assertEquals(superHero, result);
  }

  @Test
  public void givenAnInvalidSuperHeroId_whenRemove_thenThrowNotFoundException() {

    Long id = -1L;

    when(superHeroRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> superHeroService.getSuperHero(id));
  }
}
