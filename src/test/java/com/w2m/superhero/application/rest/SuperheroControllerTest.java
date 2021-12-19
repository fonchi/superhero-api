package com.w2m.superhero.application.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.w2m.superhero.TestUtils;
import com.w2m.superhero.domain.exception.NotFoundException;
import com.w2m.superhero.domain.model.Superhero;
import com.w2m.superhero.domain.service.SuperheroService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = SuperheroController.class)
public class SuperheroControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private SuperheroService superheroService;

  @Test
  @WithMockUser
  public void givenGetRequest_whenGetSuperhero_thenReturnOneSuccess() throws Exception {

    Superhero superhero = TestUtils.createSuperhero();
    String creationDate = superhero.getCreationDate().toString();
    String updateDate = superhero.getUpdateDate().toString();

    when(superheroService.getSuperhero(superhero.getId())).thenReturn(superhero);

    mvc.perform(get("/superheroes/{id}", superhero.getId())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(superhero.getId()))
        .andExpect(jsonPath("$.name").value(superhero.getName()))
        .andExpect(jsonPath("$.creation_date").value(creationDate))
        .andExpect(jsonPath("$.update_date").value(updateDate))
        .andDo(print());
  }

  @Test
  @WithMockUser
  public void givenInvalidRequest_whenGetSuperhero_thenReturnNotFoundException() throws Exception {

    Long invalidId = -1L;
    when(superheroService.getSuperhero(invalidId)).thenThrow(new NotFoundException());

    mvc.perform(get("/superheroes/{id}", invalidId)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
        .andExpect(result -> assertEquals("resource not found",
            result.getResolvedException().getMessage()))
        .andDo(print());
  }

  @Test
  @WithMockUser
  public void givenGetRequest_whenGetSuperheroes_thenReturnAllSuccess() throws Exception {

    List<Superhero> superheroes = TestUtils.createSuperheroes();
    superheroes.remove(2);

    when(superheroService.getSuperheroes(null)).thenReturn(superheroes);

    mvc.perform(get("/superheroes").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        //first superhero asserts
        .andExpect(jsonPath("$.superheroes[0].id")
            .value(superheroes.get(0).getId()))
        .andExpect(jsonPath("$.superheroes[0].name")
            .value(superheroes.get(0).getName()))
        .andExpect(jsonPath("$.superheroes[0].creation_date")
            .value(superheroes.get(0).getCreationDate()))
        .andExpect(jsonPath("$.superheroes[0].update_date")
            .value(superheroes.get(0).getUpdateDate()))
        //second superhero asserts
        .andExpect(jsonPath("$.superheroes[1].id")
            .value(superheroes.get(1).getId()))
        .andExpect(jsonPath("$.superheroes[1].name")
            .value(superheroes.get(1).getName()))
        .andExpect(jsonPath("$.superheroes[1].creation_date")
            .value(superheroes.get(1).getCreationDate()))
        .andExpect(jsonPath("$.superheroes[1].update_date")
            .value(superheroes.get(1).getUpdateDate()))
        .andDo(print());
  }

  @Test
  @WithMockUser
  public void givenNameQueryParam_whenGetSuperheroes_thenReturnAllThanMatched() throws Exception {

    List<Superhero> superheroes = TestUtils.createSuperheroes();
    String name = "man";

    when(superheroService.getSuperheroes(name)).thenReturn(superheroes);

    mvc.perform(get("/superheroes?name={name}", name)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.superheroes[0].name")
            .value(superheroes.get(0).getName()))
        .andExpect(jsonPath("$.superheroes[1].name")
            .value(superheroes.get(1).getName()))
        .andExpect(jsonPath("$.superheroes[2].name")
            .value(superheroes.get(2).getName()))
        .andDo(print());
  }

  @Test
  @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
  public void givenPutRequest_whenPutSuperhero_thenUpdateSuccess() throws Exception {

    String superheroPutReqBodyJson = "{\"name\":\"Batman\"}";
    Superhero superhero = Superhero.builder().id(1L).name("Batman").build();
    Superhero updatedSuperhero = TestUtils.createSuperhero();
    String creationDate = updatedSuperhero.getCreationDate().toString();
    String updateDate = updatedSuperhero.getUpdateDate().toString();

    when(superheroService.updateSuperhero(superhero)).thenReturn(updatedSuperhero);

    mvc.perform(patch("/superheroes/{id}", superhero.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(superheroPutReqBodyJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(updatedSuperhero.getId()))
        .andExpect(jsonPath("$.name").value(updatedSuperhero.getName()))
        .andExpect(jsonPath("$.creation_date").value(creationDate))
        .andExpect(jsonPath("$.update_date").value(updateDate));
  }

  @Test
  @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
  public void givenDeleteRequest_whenDeleteSuperhero_thenRemoveSuccess() throws Exception {

    Superhero superhero = TestUtils.createSuperhero();

    when(superheroService.removeSuperhero(1L)).thenReturn(superhero);

    mvc.perform(delete("/superheroes/{id}", superhero.getId())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  public void givenRequestWithoutAuthentication_whenGetSuperhero_thenUnauthorized()
      throws Exception {
    mvc.perform(get("/superheroes/1"))
        .andDo(print())
        .andExpect(status().isUnauthorized());
  }

}
