package com.c09.cinpockema.entities.repositories;

import org.springframework.data.repository.CrudRepository;

import com.c09.cinpockema.entities.MovieComment;
import com.c09.cinpockema.entities.Movie;
import java.util.List;

public interface MovieCommentRepository extends CrudRepository<MovieComment, Long> {
	List<MovieComment> findByMovieId(long id);
//	void deleteByUserIdAndId(Long userId, long id);
}
