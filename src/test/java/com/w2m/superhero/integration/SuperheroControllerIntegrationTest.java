package com.w2m.superhero.integration;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.w2m.superhero.TestUtils;
import com.w2m.superhero.infrastructure.exception.NotFoundException;
import com.w2m.superhero.domain.model.Superhero;
import com.w2m.superhero.domain.service.SuperheroService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests of superhero controller endpoints
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SuperheroControllerIntegrationTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private SuperheroService superheroService;

  @Test
  @WithMockUser
  public void givenGetRequest_whenGetSuperhero_thenReturnOneSuccess() throws Exception {

    Superhero superhero = TestUtils.createSuperhero();
    String creationDate = superhero.getCreationDate().toString();
    String updateDate = superhero.getUpdateDate().toString();

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
    String message = String.format("Superhero not found by id = %s", invalidId);

    mvc.perform(get("/superheroes/{id}", invalidId)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
        .andExpect(result -> assertEquals(message, result.getResolvedException().getMessage()))
        .andDo(print());
  }

  @Test
  @WithMockUser
  public void givenGetRequest_whenGetSuperheroes_thenReturnAllSuccess() throws Exception {

    mvc.perform(get("/superheroes").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.total").exists())
        .andExpect(jsonPath("$.superheroes").exists())
        .andDo(print());
  }

  @Test
  @WithMockUser
  public void givenNameQueryParam_whenGetSuperheroes_thenReturnAllThanMatched() throws Exception {

    String name = "man";

    mvc.perform(get("/superheroes?name={name}", name)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.total").exists())
        .andExpect(jsonPath("$.superheroes").exists())
        .andDo(print());
  }

  @Test
  @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
  public void givenPutRequest_whenPutSuperhero_thenUpdateSuccess() throws Exception {

    String superheroPutReqBodyJson = "{\"name\":\"Batman\"}";
    Superhero superhero = Superhero.builder().id(4L).name("Batman").build();

    mvc.perform(patch("/superheroes/{id}", superhero.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(superheroPutReqBodyJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(superhero.getId()))
        .andExpect(jsonPath("$.name").value(superhero.getName()))
        .andDo(print());
  }

  @Test
  @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
  public void givenDeleteRequest_whenDeleteSuperhero_thenRemoveSuccess() throws Exception {

    mvc.perform(delete("/superheroes/{id}", 3L)
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
