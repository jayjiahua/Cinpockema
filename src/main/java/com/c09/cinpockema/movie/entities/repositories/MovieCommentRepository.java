package com.c09.cinpockema.movie.entities.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.c09.cinpockema.movie.entities.Movie;
import com.c09.cinpockema.movie.entities.MovieComment;

import java.util.List;

public interface MovieCommentRepository extends JpaRepository<MovieComment, Long> {
	List<MovieComment> findByMovieId(long id);
}
