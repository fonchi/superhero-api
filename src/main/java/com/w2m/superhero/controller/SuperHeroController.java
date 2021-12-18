package com.w2m.superhero.controller;

import com.w2m.superhero.domain.SuperHero;
import com.w2m.superhero.dto.SuperHeroRequestDto;
import com.w2m.superhero.dto.SuperHeroResponseDto;
import com.w2m.superhero.dto.SuperHeroesResponseDto;
import com.w2m.superhero.service.SuperHeroService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("superheroes")
public class SuperHeroController {

  @Autowired
  private SuperHeroService superHeroService;

  @GetMapping("/{id}")
  public SuperHeroResponseDto getSuperHero(@PathVariable Long id) {
    SuperHero superHero = superHeroService.getSuperHero(id);
    return SuperHeroResponseDto.fromEntity(superHero);
  }

  @GetMapping
  public SuperHeroesResponseDto getSuperHeroes(@RequestParam(required = false) String name) {
    List<SuperHero> superHeroes = superHeroService.getSuperHeroes(name);
    return SuperHeroesResponseDto.fromEntities(superHeroes);
  }

  @PutMapping("/{id}")
  public SuperHeroResponseDto putSuperHero(@PathVariable Long id,
      @RequestBody SuperHeroRequestDto requestDto) {
    requestDto.setId(id);
    SuperHero superHero = superHeroService.updateSuperHero(requestDto.toEntity());
    return SuperHeroResponseDto.fromEntity(superHero);
  }

  @DeleteMapping("/{id}")
  public SuperHeroResponseDto deleteSuperHero(@PathVariable Long id) {
    SuperHero superHero = superHeroService.removeSuperHero(id);
    return SuperHeroResponseDto.fromEntity(superHero);
  }

}
