package com.w2m.superhero.service;

import com.w2m.superhero.domain.SuperHero;
import org.springframework.stereotype.Service;

@Service
public interface SuperHeroService {

  SuperHero findById(String id);
}
