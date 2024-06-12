package com.dataguard.dgchallenge.domain;

import com.dataguard.dgchallenge.rest.dto.SuperheroDto;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Superhero {

  @Id
  private String alias;
  private String name;
  private String origin;
  @ElementCollection
  @CollectionTable(name = "superhero_powers", joinColumns = @JoinColumn(name = "alias"))
  private Set<String> powers;
  @ElementCollection
  @CollectionTable(name = "superhero_weapons", joinColumns = @JoinColumn(name = "alias"))
  private Set<String> weapons;
  @ElementCollection
  @CollectionTable(name = "superhero_associations", joinColumns = @JoinColumn(name = "alias"))
  private Set<String> associations;

  public SuperheroDto toDto() {
    return SuperheroDto.builder()
        .alias(alias)
        .name(name)
        .origin(origin)
        .powers(powers)
        .weapons(weapons)
        .associations(associations)
        .build();
  }
}
