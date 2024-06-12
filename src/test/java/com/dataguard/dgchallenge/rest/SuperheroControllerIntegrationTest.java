package com.dataguard.dgchallenge.rest;

import static com.dataguard.dgchallenge.fixture.SuperheroFixture.batman;
import static com.dataguard.dgchallenge.fixture.SuperheroFixture.modifiedSuperman;
import static com.dataguard.dgchallenge.fixture.SuperheroFixture.superman;
import static com.dataguard.dgchallenge.fixture.SuperheroFixture.villain;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.dataguard.dgchallenge.rest.dto.SuperheroDto;
import com.dataguard.dgchallenge.service.SuperheroService;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SuperheroControllerIntegrationTest {
  @LocalServerPort
  private int port;

  @Autowired
  SuperheroService service;

  TestRestTemplate restTemplate = new TestRestTemplate();

  @Test
  void addSuperheroTest() {

    var batmanDto = batman();

    var responseEntity = restTemplate.postForEntity(createURLWithPort("/superhero"),
        batmanDto, SuperheroDto.class);

    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertEquals("Batman", Objects.requireNonNull(responseEntity.getBody()).getAlias());

  }

  @Test
  void addInvalidSuperheroTest() {

    var responseEntity = restTemplate.postForEntity(createURLWithPort("/superhero"),
        villain(), String.class);

    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    assertEquals("Invalid request body", responseEntity.getBody());

  }

  @Test
  void updateInvalidSuperheroTest() {
    var requestEntity = new HttpEntity<>(villain());
    var responseEntity = restTemplate.exchange(createURLWithPort("/superhero"),
        HttpMethod.PUT, requestEntity, String.class);

    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    assertEquals("Invalid request body", responseEntity.getBody());

  }

  @Test
  void updateSuperheroTest() {

    service.addSuperhero(superman());

    var requestEntity = new HttpEntity<>(modifiedSuperman());
    var responseEntity = restTemplate.exchange(createURLWithPort("/superhero"),
        HttpMethod.PUT, requestEntity, SuperheroDto.class);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals("Superman", Objects.requireNonNull(responseEntity.getBody()).getAlias());
    assertEquals(1, Objects.requireNonNull(responseEntity.getBody()).getAssociations().size());
    service.deleteSuperhero("Superman");

  }

  @Test
  void updateNonExistentSuperheroTest() {
    var requestEntity = new HttpEntity<>(superman());
    var responseEntity = restTemplate.exchange(createURLWithPort("/superhero"),
        HttpMethod.PUT, requestEntity, String.class);

    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertEquals("Superhero Superman not found", responseEntity.getBody());
  }

  @Test
  void getSuperheroTest() {
    service.addSuperhero(superman());
    var responseEntity = restTemplate.getForEntity(createURLWithPort("/superhero/Superman"),
        SuperheroDto.class);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    service.deleteSuperhero("Superman");
  }

  @Test
  void getNonExistentSuperheroTest() {
    var responseEntity = restTemplate.getForEntity(createURLWithPort("/superhero/Superman"),
        String.class);
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertEquals("superhero Superman does not exist", responseEntity.getBody());
  }

  @Test
  void getAllSuperheroesTest() {

    var responseEntity = restTemplate.getForEntity(createURLWithPort("/superhero"),
        List.class);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

  }

  @Test
  void deleteSuperheroTest() {

    service.addSuperhero(superman());

    restTemplate.delete(createURLWithPort("/superhero/Superman"));

    assertEquals(0, service.getAllSuperheros().size());
  }

  private String createURLWithPort(String uri) {
    return "http://localhost:" + port + uri;
  }
}
