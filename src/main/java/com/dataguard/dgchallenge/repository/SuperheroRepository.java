package com.dataguard.dgchallenge.repository;

import com.dataguard.dgchallenge.domain.Superhero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperheroRepository extends JpaRepository<Superhero, String> {

}
