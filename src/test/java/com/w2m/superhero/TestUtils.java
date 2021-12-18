package com.w2m.superhero;

import com.w2m.superhero.domain.SuperHero;
import java.time.LocalDateTime;
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
    LocalDateTime updatedDate = LocalDateTime.of(2021, 12, 17, 00, 00, 00);
    LocalDateTime creationDate = LocalDateTime.of(2021, 12, 16, 00, 00, 00);
    return SuperHero.builder().id(1L).name(name).creationDate(creationDate)
        .updateDate(updatedDate).build();
  }

}
