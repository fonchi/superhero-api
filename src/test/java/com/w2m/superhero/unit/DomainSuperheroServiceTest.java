package com.w2m.superhero.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.w2m.superhero.TestUtils;
import com.w2m.superhero.domain.exception.NotFoundException;
import com.w2m.superhero.domain.model.Superhero;
import com.w2m.superhero.domain.repository.SuperheroRepository;
import com.w2m.superhero.domain.service.SuperheroService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class DomainSuperheroServiceTest {

  @Autowired
  SuperheroService superheroService;
  @MockBean
  SuperheroRepository superheroRepository;

  @Test
  public void givenValidId_whenFindById_thenReturnSuperhero() {

    Long id = 1L;
    Superhero superhero = TestUtils.createSuperhero();

    when(superheroRepository.findById(id)).thenReturn(Optional.of(superhero));

    Superhero result = superheroService.getSuperhero(id);

    assertEquals(superhero, result);
  }

  @Test
  public void givenInvalidId_whenFindById_thenThrowNotFoundException() {

    Long id = -1L;

    when(superheroRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> superheroService.getSuperhero(id));
  }

  @Test
  public void whenFindAll_thenListAllSuperheroes() {

    List<Superhero> superheroes = TestUtils.createSuperheroes();

    when(superheroRepository.findAll()).thenReturn(superheroes);

    List<Superhero> result = superheroService.getSuperheroes(null);

    assertEquals(superheroes, result);
  }

  @Test
  public void givenNameParam_whenSearchByName_thenListAllSuperheroesThanMatched() {

    String name = "man";
    List<Superhero> superheroes = TestUtils.createSuperheroes();

    when(superheroRepository.findByFilters(name)).thenReturn(superheroes);

    List<Superhero> result = superheroService.getSuperheroes(name);

    assertEquals(superheroes, result);
  }

  @Test
  public void givenNewSuperheroInfo_whenUpdate_thenSuperheroUpdated() {

    Superhero newSH = TestUtils.createSuperhero("Robin");
    newSH.setCreationDate(null);
    newSH.setUpdateDate(null);
    Superhero oldSH = TestUtils.createSuperhero("Batman");
    Superhero updatedSH = TestUtils.createSuperhero("Robin");

    when(superheroRepository.findById(newSH.getId())).thenReturn(Optional.of(oldSH));
    when(superheroRepository.save(updatedSH)).thenReturn(updatedSH);

    Superhero result = superheroService.updateSuperhero(newSH);

    assertEquals(updatedSH, result);
    verify(superheroRepository, times(1)).findById(newSH.getId());
    verify(superheroRepository, times(1)).save(updatedSH);
  }

  @Test
  public void givenIdempotentUpdate_whenUpdate_thenReturnByIdempotency() {

    Superhero newSH = TestUtils.createSuperhero("Robin");
    Superhero oldSH = TestUtils.createSuperhero("Robin");

    when(superheroRepository.findById(newSH.getId())).thenReturn(Optional.of(oldSH));

    Superhero result = superheroService.updateSuperhero(newSH);

    assertEquals(oldSH, result);
    verify(superheroRepository, only()).findById(newSH.getId());
  }

  @Test
  public void givenAnValidSuperheroId_whenRemove_thenSuccessDeleted() {

    Superhero superhero = TestUtils.createSuperheroes().get(0);
    Long id = superhero.getId();

    when(superheroRepository.findById(id)).thenReturn(Optional.of(superhero));
    doNothing().when(superheroRepository).delete(superhero);

    Superhero result = superheroService.removeSuperhero(id);

    assertEquals(superhero, result);
  }

  @Test
  public void givenAnInvalidSuperheroId_whenRemove_thenThrowNotFoundException() {

    Long id = -1L;

    when(superheroRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> superheroService.getSuperhero(id));
  }
}
