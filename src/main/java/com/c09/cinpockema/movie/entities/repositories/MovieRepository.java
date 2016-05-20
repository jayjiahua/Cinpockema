package com.c09.cinpockema.movie.entities.repositories;

import org.springframework.data.repository.CrudRepository;

import com.c09.cinpockema.movie.entities.Movie;


public interface MovieRepository extends CrudRepository<Movie, Long> {

}
