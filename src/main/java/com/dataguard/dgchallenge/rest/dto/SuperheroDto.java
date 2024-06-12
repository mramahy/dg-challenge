package com.dataguard.dgchallenge.rest.dto;

import com.dataguard.dgchallenge.domain.Superhero;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Getter
public class SuperheroDto {

  @NotBlank
  @NotNull
  private String alias;
  @NotBlank
  @NotNull
  private String name;
  private String origin;
  @NotNull
  private Set<String> powers;
  private Set<String> weapons;
  private Set<String> associations;

  public Superhero toEntity() {
    return Superhero.builder().alias(alias)
        .name(name)
        .origin(origin)
        .associations(associations)
        .powers(powers)
        .weapons(weapons)
        .build();
  }
}
