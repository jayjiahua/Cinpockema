package com.c09.cinpockema.product.entities.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.c09.cinpockema.product.entities.Screening;

public interface ScreeningRepository extends JpaRepository<Screening, Long>{
	List<Screening> findByCinemaId(long id);
}
