package com.c09.cinpockema.cinema.entities.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.c09.cinpockema.cinema.entities.Seat;

public interface SeatRepository extends CrudRepository<Seat, Long> {
	List<Seat> findByHallId(long id);
}