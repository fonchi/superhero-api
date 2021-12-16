package com.w2m.superhero;

import com.w2m.superhero.domain.SuperHero;
import java.util.Arrays;
import java.util.List;

public class TestUtils {

  public static List<SuperHero> getSuperHeros() {
    SuperHero batman = SuperHero.builder().id(1L).name("Batman").build();
    SuperHero robin = SuperHero.builder().id(2L).name("Robin").build();
    return Arrays.asList(batman, robin);
  }

}
