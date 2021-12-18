package com.w2m.superhero.application.rest;

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
import com.w2m.superhero.application.rest.SuperHeroController;
import com.w2m.superhero.domain.model.SuperHero;
import com.w2m.superhero.domain.exception.NotFoundException;
import com.w2m.superhero.domain.service.SuperHeroService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = SuperHeroController.class)
public class SuperHeroControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private SuperHeroService superHeroService;

  @Test
  public void givenGetRequest_whenGetSuperHero_thenReturnOneSuccess() throws Exception {

    SuperHero superHero = TestUtils.createSuperHero();
    String creationDate = superHero.getCreationDate().toString();
    String updateDate = superHero.getUpdateDate().toString();

    when(superHeroService.getSuperHero(superHero.getId())).thenReturn(superHero);

    mvc.perform(get("/superheroes/{id}", superHero.getId())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(superHero.getId()))
        .andExpect(jsonPath("$.name").value(superHero.getName()))
        .andExpect(jsonPath("$.creation_date").value(creationDate))
        .andExpect(jsonPath("$.update_date").value(updateDate))
        .andDo(print());
  }

  @Test
  public void givenInvalidRequest_whenGetSuperHero_thenReturnNotFoundException() throws Exception {

    Long invalidId = -1L;
    when(superHeroService.getSuperHero(invalidId)).thenThrow(new NotFoundException());

    mvc.perform(get("/superheroes/{id}", invalidId)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
        .andExpect(result -> assertEquals("resource not found",
            result.getResolvedException().getMessage()))
        .andDo(print());
  }

  @Test
  public void givenGetRequest_whenGetSuperHeroes_thenReturnAllSuccess() throws Exception {

    List<SuperHero> superHeroes = TestUtils.createSuperHeroes();
    superHeroes.remove(2);

    when(superHeroService.getSuperHeroes(null)).thenReturn(superHeroes);

    mvc.perform(get("/superheroes").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        //first superhero asserts
        .andExpect(jsonPath("$.super_heroes[0].id")
            .value(superHeroes.get(0).getId()))
        .andExpect(jsonPath("$.super_heroes[0].name")
            .value(superHeroes.get(0).getName()))
        .andExpect(jsonPath("$.super_heroes[0].creation_date")
            .value(superHeroes.get(0).getCreationDate()))
        .andExpect(jsonPath("$.super_heroes[0].update_date")
            .value(superHeroes.get(0).getUpdateDate()))
        //second superhero asserts
        .andExpect(jsonPath("$.super_heroes[1].id")
            .value(superHeroes.get(1).getId()))
        .andExpect(jsonPath("$.super_heroes[1].name")
            .value(superHeroes.get(1).getName()))
        .andExpect(jsonPath("$.super_heroes[1].creation_date")
            .value(superHeroes.get(1).getCreationDate()))
        .andExpect(jsonPath("$.super_heroes[1].update_date")
            .value(superHeroes.get(1).getUpdateDate()))
        .andDo(print());
  }

  @Test
  public void givenNameQueryParam_whenGetSuperHeroes_thenReturnAllThanMatched() throws Exception {

    List<SuperHero> superHeroes = TestUtils.createSuperHeroes();
    String name = "man";

    when(superHeroService.getSuperHeroes(name)).thenReturn(superHeroes);

    mvc.perform(get("/superheroes?name={name}", name)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.super_heroes[0].name")
            .value(superHeroes.get(0).getName()))
        .andExpect(jsonPath("$.super_heroes[1].name")
            .value(superHeroes.get(1).getName()))
        .andExpect(jsonPath("$.super_heroes[2].name")
            .value(superHeroes.get(2).getName()))
        .andDo(print());
  }

  @Test
  public void givenPutRequest_whenPutSuperHero_thenUpdateSuccess() throws Exception {

    String superHeroPutReqBodyJson = "{\"name\":\"Batman\"}";
    SuperHero superHero = SuperHero.builder().id(1L).name("Batman").build();
    SuperHero updatedSuperHero = TestUtils.createSuperHero();
    String creationDate = updatedSuperHero.getCreationDate().toString();
    String updateDate = updatedSuperHero.getUpdateDate().toString();

    when(superHeroService.updateSuperHero(superHero)).thenReturn(updatedSuperHero);

    mvc.perform(put("/superheroes/{id}", superHero.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(superHeroPutReqBodyJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(updatedSuperHero.getId()))
        .andExpect(jsonPath("$.name").value(updatedSuperHero.getName()))
        .andExpect(jsonPath("$.creation_date").value(creationDate))
        .andExpect(jsonPath("$.update_date").value(updateDate));
  }

  @Test
  public void givenDeleteRequest_whenDeleteSuperHero_thenRemoveSuccess() throws Exception {

    SuperHero superHero = TestUtils.createSuperHero();

    when(superHeroService.removeSuperHero(1L)).thenReturn(superHero);

    mvc.perform(delete("/superheroes/{id}", superHero.getId())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print());
  }

}
