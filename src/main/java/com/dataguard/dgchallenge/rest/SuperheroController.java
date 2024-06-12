package com.dataguard.dgchallenge.rest;

import com.dataguard.dgchallenge.error.SuperheroException;
import com.dataguard.dgchallenge.rest.dto.SuperheroDto;
import com.dataguard.dgchallenge.service.SuperheroService;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/superhero")
public class SuperheroController {

  private final SuperheroService service;

  public SuperheroController(SuperheroService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<SuperheroDto> addSuperhero(@Validated @RequestBody SuperheroDto superhero,
      BindingResult errors) {
    validateRequest(errors);
    var savedSuperhero = service.addSuperhero(superhero);
    return new ResponseEntity<>(savedSuperhero, HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<SuperheroDto> updateSuperhero(@Validated @RequestBody SuperheroDto superhero,
      BindingResult errors) {
    validateRequest(errors);
    var updatedSuperhero = service.updateSuperhero(superhero);
    return new ResponseEntity<>(updatedSuperhero, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<SuperheroDto>> getAllSuperheroes() {
    var superheroList = service.getAllSuperheros();
    return Optional.ofNullable(superheroList)
        .filter(list -> !list.isEmpty())
        .map(list -> ResponseEntity.ok().body(list))
        .orElse(ResponseEntity.noContent().build());
  }

  @GetMapping("/{alias}")
  public ResponseEntity<SuperheroDto> getSuperhero(@PathVariable("alias") String alias) {
    var superhero = service.getSuperhero(alias);
    return ResponseEntity.ok(superhero);
  }

  @DeleteMapping("/{alias}")
  public ResponseEntity<Void> getAllSuperheroes(@PathVariable("alias") String alias) {
    service.deleteSuperhero(alias);
    return ResponseEntity.noContent().build();
  }

  private void validateRequest(BindingResult errors) {
    if (errors.hasErrors()) {
      throw new SuperheroException("Invalid request body", HttpStatus.BAD_REQUEST);
    }
  }

}
