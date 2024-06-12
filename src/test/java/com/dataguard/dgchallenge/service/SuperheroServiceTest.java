package com.dataguard.dgchallenge.service;

import static com.dataguard.dgchallenge.fixture.SuperheroFixture.superhero;
import static com.dataguard.dgchallenge.fixture.SuperheroFixture.superheroDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.dataguard.dgchallenge.domain.Superhero;
import com.dataguard.dgchallenge.error.SuperheroException;
import com.dataguard.dgchallenge.repository.SuperheroRepository;
import com.dataguard.dgchallenge.rest.dto.SuperheroDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import java.util.Optional;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SuperheroServiceTest {
  @InjectMocks
  SuperheroService service;

  @Mock
  SuperheroRepository repository;


      @Test
      void testUpdateSuperhero_found() {
          // Prepare data
        var dto = superheroDto();

          // Mock repository
          Mockito.when(repository.findById(dto.getAlias())).thenReturn(Optional.of(superhero()));
          Mockito.when(repository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);

          // Assert whether superhero was updated
        assertSuperheroDtoEquals(dto, service.updateSuperhero(dto));
      }

     @Test
      void testUpdateSuperhero_notFound() {
          // Prepare data
       var dto = superheroDto();

          // Mock repository
          Mockito.when(repository.findById(dto.getAlias())).thenReturn(Optional.empty());


          // Assert whether SuperheroException was thrown
          Assertions.assertThrows(SuperheroException.class, () -> service.updateSuperhero(dto));
      }

      @Test
      void testAddSuperhero_alreadyExists() {
          // Prepare data
        var dto = superheroDto();

        Superhero superhero = superhero();

          // Mock repository
          Mockito.when(repository.findById(dto.getAlias())).thenReturn(Optional.of(superhero));

          // Assert whether SuperheroException was thrown
          Assertions.assertThrows(SuperheroException.class, () -> service.addSuperhero(dto));
      }

      @Test
      void testAddSuperhero_notExists() {
          // Prepare data
        var dto = superheroDto();

          // Mock repository
          Mockito.when(repository.findById(dto.getAlias())).thenReturn(Optional.empty());
          Mockito.when(repository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);


         // Assert whether superhero was added
        assertSuperheroDtoEquals(dto, service.addSuperhero(dto));
     }

  private void assertSuperheroDtoEquals(SuperheroDto expected, SuperheroDto result) {
    assertEquals(expected.getAlias(), result.getAlias());
    assertEquals(expected.getName(), result.getName());
    assertEquals(expected.getOrigin(), result.getOrigin());

    assertTrue(expected.getAssociations().size() == result.getAssociations().size()
               && expected.getAssociations().containsAll(result.getAssociations()));

    assertTrue(expected.getPowers().size() == result.getPowers().size()
               && expected.getPowers().containsAll(result.getPowers()));

    assertTrue(expected.getWeapons().size() == result.getWeapons().size()
               && expected.getWeapons().containsAll(result.getWeapons()));
  }

}
