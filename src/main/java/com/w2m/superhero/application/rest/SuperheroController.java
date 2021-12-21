package com.w2m.superhero.application.rest;

import com.w2m.superhero.application.dto.SuperheroRequestDto;
import com.w2m.superhero.application.dto.SuperheroResponseDto;
import com.w2m.superhero.application.dto.SuperheroesResponseDto;
import com.w2m.superhero.domain.model.Superhero;
import com.w2m.superhero.domain.service.SuperheroService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to handle all rest requests for superheroes resource
 */
@RestController
@RequestMapping("superheroes")
public class SuperheroController {

  @Autowired
  private SuperheroService superheroService;

  /**
   * handler method of GET superhero by id
   * @param id
   * @return
   */
  @GetMapping("/{id}")
  public SuperheroResponseDto getSuperhero(@PathVariable Long id) {
    Superhero superhero = superheroService.getSuperhero(id);
    return SuperheroResponseDto.fromEntity(superhero);
  }

  /**
   * handler method of GET all superheroes or based on name filter
   * @param name
   * @return
   */
  @GetMapping
  public SuperheroesResponseDto getSuperheroes(@RequestParam(required = false) String name) {
    List<Superhero> superheroes = superheroService.getSuperheroes(name);
    return SuperheroesResponseDto.fromEntities(superheroes);
  }

  /**
   * handler method of PATCH superhero by id for resource modification
   * @param id
   * @param requestDto
   * @return
   */
  @PatchMapping("/{id}")
  public SuperheroResponseDto putSuperhero(@PathVariable Long id,
      @RequestBody SuperheroRequestDto requestDto) {
    requestDto.setId(id);
    Superhero superhero = superheroService.updateSuperhero(requestDto.toEntity());
    return SuperheroResponseDto.fromEntity(superhero);
  }

  /**
   * handler method of DELETE superhero by id to remove resource
   * @param id
   * @return
   */
  @DeleteMapping("/{id}")
  public SuperheroResponseDto deleteSuperhero(@PathVariable Long id) {
    Superhero superhero = superheroService.removeSuperhero(id);
    return SuperheroResponseDto.fromEntity(superhero);
  }

}
