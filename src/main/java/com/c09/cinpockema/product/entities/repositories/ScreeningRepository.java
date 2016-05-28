package com.c09.cinpockema.product.entities.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.c09.cinpockema.movie.entities.MovieComment;
import com.c09.cinpockema.product.entities.Screening;

public interface ScreeningRepository extends CrudRepository<Screening, Long>{
	List<Screening> findByCinemaId(long id);
}
