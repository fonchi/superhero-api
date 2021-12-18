package com.w2m.superhero.controller;

import com.w2m.superhero.dto.SuperHeroRequestDto;
import com.w2m.superhero.dto.SuperHeroResponseDto;
import com.w2m.superhero.dto.SuperHeroesResponseDto;
import com.w2m.superhero.service.SuperHeroService;
import java.util.List;
import java.util.Optional;
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
@RequestMapping("/superheroes")
public class SuperHeroController {

  @Autowired
  private SuperHeroService superHeroService;

  @GetMapping("/{id}")
  public Optional<SuperHeroResponseDto> getSuperHero(@PathVariable Long id) {
    return null;
  }

  @GetMapping
  public List<SuperHeroesResponseDto> getSuperHeroes(@RequestParam String name) {
    return null;
  }

  @PutMapping("/{id}")
  public Optional<SuperHeroResponseDto> putSuperHero(@PathVariable Long id,
      @RequestBody SuperHeroRequestDto requestDto) {
    return null;
  }

  @DeleteMapping("/{id}")
  public Optional<SuperHeroResponseDto> deleteSuperHero(@PathVariable Long id) {
    return null;
  }

}
