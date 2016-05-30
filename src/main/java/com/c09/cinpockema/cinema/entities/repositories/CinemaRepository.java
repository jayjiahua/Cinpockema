package com.c09.cinpockema.cinema.entities.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.c09.cinpockema.cinema.entities.Cinema;
import com.c09.cinpockema.cinema.entities.CinemaComment;

public interface CinemaRepository extends CrudRepository<Cinema, Long> {
	List<Cinema> findByCityId(int cityId);
}