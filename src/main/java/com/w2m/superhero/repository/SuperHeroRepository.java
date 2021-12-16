package com.w2m.superhero.repository;

import com.w2m.superhero.domain.SuperHero;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperHeroRepository extends PagingAndSortingRepository<SuperHero, Long> {

}
