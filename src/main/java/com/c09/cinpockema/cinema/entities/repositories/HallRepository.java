package com.c09.cinpockema.cinema.entities.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.c09.cinpockema.cinema.entities.Hall;

public interface HallRepository extends JpaRepository<Hall, Long> {
	List<Hall> findByCinemaId(long id);
}