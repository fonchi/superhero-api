package com.w2m.superhero;

import com.w2m.superhero.domain.SuperHero;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestUtils {

  public static List<SuperHero> createSuperHeroes() {
    SuperHero s1 = SuperHero.builder().id(1L).name("Spiderman").build();
    SuperHero s2 = SuperHero.builder().id(2L).name("Superman").build();
    SuperHero s3 = SuperHero.builder().id(3L).name("Manolito el fuerte").build();
    return new ArrayList(Arrays.asList(s1, s2, s3));
  }

  public static SuperHero createSuperHero() {
    return createSuperHero("Spiderman");
  }

  public static SuperHero createSuperHero(String name) {
    Instant creationDate = Instant.parse("2021-12-16T00:00:00.000Z");
    Instant updatedDate = Instant.parse("2021-12-17T00:00:00.000Z");
    return SuperHero.builder().id(1L).name(name).creationDate(creationDate)
        .updateDate(updatedDate).build();
  }

}
