package com.dataguard.dgchallenge.fixture;

import com.dataguard.dgchallenge.domain.Superhero;
import com.dataguard.dgchallenge.rest.dto.SuperheroDto;
import java.util.HashSet;
import java.util.Set;

public class SuperheroFixture {

  public static SuperheroDto batman(){
    return SuperheroDto.builder()
        .alias("Batman")
        .name("Bruce Wayne")
        .powers(Set.of("tactics", "martial arts"))
        .weapons(Set.of("bat mobile", "bat chopper"))
        .associations(Set.of("Justice League"))
        .build();
  }

  public static SuperheroDto villain(){
    return SuperheroDto.builder()
        .name("Harvey Dent")
        .associations(Set.of("syndicate"))
        .build();
  }

  public static SuperheroDto superman(){
    return SuperheroDto.builder()
        .alias("Superman")
        .name("Clark Kent")
        .powers(Set.of("super strength", "fly"))
        .weapons(Set.of())
        .associations(new HashSet<>())
        .build();
  }

  public static SuperheroDto modifiedSuperman(){
    return SuperheroDto.builder()
        .alias("Superman")
        .name("Clark Kent")
        .powers(Set.of("super strength", "fly"))
        .weapons(Set.of())
        .associations(Set.of("Justice League"))
        .build();
  }


  public static SuperheroDto superheroDto() {
    return SuperheroDto.builder()
        .alias("alias")
        .name("name")
        .powers(new HashSet<>())
        .weapons(new HashSet<>())
        .associations(new HashSet<>())
        .build();
  }
  public static Superhero superhero(){
    return superheroDto().toEntity();
  }

}
