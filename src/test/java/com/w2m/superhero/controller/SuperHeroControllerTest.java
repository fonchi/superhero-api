package com.w2m.superhero.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.w2m.superhero.TestUtils;
import com.w2m.superhero.domain.SuperHero;
import com.w2m.superhero.exception.NotFoundException;
import com.w2m.superhero.service.SuperHeroService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SuperHeroControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private SuperHeroService superHeroService;

  @Test
  public void givenGetRequest_whenGetSuperHero_thenReturnOneSuccess() throws Exception {

    SuperHero superHero = TestUtils.createSuperHero();

    when(superHeroService.findById(superHero.getId())).thenReturn(superHero);

    mvc.perform(get("/superheroes/{id}", superHero.getId()).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(superHero.getId()))
        .andExpect(jsonPath("$.name").value(superHero.getName()))
        .andExpect(jsonPath("$.creation_date").value(superHero.getCreationDate()))
        .andExpect(jsonPath("$.update_date").value(superHero.getUpdatedDate()))
        .andDo(print());
  }

  @Test
  public void givenInvalidRequest_whenGetSuperHero_thenReturnNotFoundException() throws Exception {

    Long invalidId = -1L;
    when(superHeroService.findById(invalidId)).thenThrow(new NotFoundException());

    mvc.perform(get("/superheroes/{id}", invalidId).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
        .andExpect(result -> assertEquals("resource not found",
            result.getResolvedException().getMessage()))
        .andDo(print());
  }

  @Test
  public void givenGetRequest_whenGetSuperHeroes_thenReturnAllSuccess() throws Exception {

    List<SuperHero> superHeroes = TestUtils.getSuperHeroes();
    superHeroes.remove(2);

    when(superHeroService.findAll()).thenReturn(superHeroes);

    mvc.perform(get("/superheroes").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        //first superhero asserts
        .andExpect(jsonPath("$.super_heroes[0].id[0]")
            .value(superHeroes.get(0).getId()))
        .andExpect(jsonPath("$.super_heroes[0].name[0]")
            .value(superHeroes.get(0).getName()))
        .andExpect(jsonPath("$.super_heroes[0].creation_date[0]")
            .value(superHeroes.get(0).getCreationDate()))
        .andExpect(jsonPath("$.super_heroes[0].update_date[0]")
            .value(superHeroes.get(0).getUpdatedDate()))
        //second superhero asserts
        .andExpect(jsonPath("$.super_heroes[1].id[1]")
            .value(superHeroes.get(0).getId()))
        .andExpect(jsonPath("$.super_heroes[1].name[1]")
            .value(superHeroes.get(0).getName()))
        .andExpect(jsonPath("$.super_heroes[1].creation_date[1]")
            .value(superHeroes.get(0).getCreationDate()))
        .andExpect(jsonPath("$.super_heroes[1].update_date[1]")
            .value(superHeroes.get(0).getUpdatedDate()))
        .andDo(print());
  }

  @Test
  public void givenNameQueryParam_whenGetSuperHeroes_thenReturnAllThanMatched() throws Exception {

    List<SuperHero> superHeroes = TestUtils.getSuperHeroes();
    String name = "man";

    when(superHeroService.searchByName(name)).thenReturn(superHeroes);

    mvc.perform(get("/superheroes?name={name}", name).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.super_heroes[0].name[0]")
            .value(superHeroes.get(0).getName()))
        .andExpect(jsonPath("$.super_heroes[1].name[1]")
            .value(superHeroes.get(0).getName()))
        .andExpect(jsonPath("$.super_heroes[3].name[3]")
            .value(superHeroes.get(0).getName()))
        .andDo(print());
  }

  @Test
  public void givenPutRequest_whenPutSuperHero_thenUpdateSuccess() throws Exception {

    String superHeroPutReqBodyJson = "{\"name\":\"Batman\"}";
    SuperHero superHero = TestUtils.createSuperHero();
    superHero.setCreationDate(null);
    SuperHero updatedSuperHero = TestUtils.createSuperHero();

    when(superHeroService.update(superHero)).thenReturn(updatedSuperHero);

    mvc.perform(put("/superheroes/{id}", superHero.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(superHeroPutReqBodyJson))
        .andExpect(status().isOk());
  }

  @Test
  public void givenDeleteRequest_whenDeleteSuperHero_thenRemoveSuccess() throws Exception {

    SuperHero superHero = TestUtils.createSuperHero();

    when(superHeroService.remove(1L)).thenReturn(superHero);

    mvc.perform(delete("/superheroes/{id}", superHero.getId())
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print());
  }

}
