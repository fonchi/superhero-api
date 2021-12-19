package com.w2m.superhero;

import com.w2m.superhero.domain.model.Superhero;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestUtils {

  public static List<Superhero> createSuperheroes() {
    Superhero s1 = Superhero.builder().id(1L).name("Spiderman").build();
    Superhero s2 = Superhero.builder().id(2L).name("Superman").build();
    Superhero s3 = Superhero.builder().id(3L).name("Manolito el fuerte").build();
    return new ArrayList(Arrays.asList(s1, s2, s3));
  }

  public static Superhero createSuperhero() {
    return createSuperhero("Spiderman");
  }

  public static Superhero createSuperhero(String name) {
    Instant creationDate = Instant.parse("2021-12-16T00:00:00.000Z");
    Instant updatedDate = Instant.parse("2021-12-17T00:00:00.000Z");
    return Superhero.builder().id(1L).name(name).creationDate(creationDate)
        .updateDate(updatedDate).build();
  }

}
