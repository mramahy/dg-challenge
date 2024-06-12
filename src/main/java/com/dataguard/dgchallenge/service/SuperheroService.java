package com.dataguard.dgchallenge.service;

import com.dataguard.dgchallenge.domain.Superhero;
import com.dataguard.dgchallenge.error.SuperheroException;
import com.dataguard.dgchallenge.repository.SuperheroRepository;
import com.dataguard.dgchallenge.rest.dto.SuperheroDto;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SuperheroService {
  private final SuperheroRepository repository;

  public SuperheroService(SuperheroRepository repository) {
    this.repository = repository;
  }

  public SuperheroDto addSuperhero(SuperheroDto superhero) {
      validateHeroAddition(superhero);
      return repository.save(superhero.toEntity()).toDto();
  }

  public SuperheroDto updateSuperhero(SuperheroDto superhero) {
    return repository.findById(superhero.getAlias())
        .map(existingSuperhero -> repository.save(superhero.toEntity()))
        .orElseThrow(() -> new SuperheroException(String.format("Superhero %s not found",
            superhero.getAlias()), HttpStatus.NOT_FOUND))
        .toDto();
  }

  public List<SuperheroDto> getAllSuperheros() {
    return repository.findAll().stream().map(Superhero::toDto).toList();
  }

  public void deleteSuperhero(String alias) {
    repository.deleteById(alias);
  }

  private void validateHeroAddition(SuperheroDto superhero) {
    var superHeroOptional = repository.findById(superhero.getAlias());
    superHeroOptional.ifPresent(hero -> {
      throw new SuperheroException(String.format("superhero %s already exists", hero.getAlias()), HttpStatus.BAD_REQUEST);
    });
  }

  public SuperheroDto getSuperhero(String alias) {
    return repository.findById(alias).map(Superhero::toDto)
        .orElseThrow(()-> new SuperheroException(String.format("superhero %s does not exist", alias), HttpStatus.NOT_FOUND));
  }
}
