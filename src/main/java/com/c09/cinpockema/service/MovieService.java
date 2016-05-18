package com.c09.cinpockema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c09.cinpockema.entities.Movie;
import com.c09.cinpockema.entities.MovieComment;
import com.c09.cinpockema.entities.repositories.MovieCommentRepository;
import com.c09.cinpockema.entities.repositories.MovieRepository;

@Service
public class MovieService {
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private MovieCommentRepository movieCommentRepository;
	
	public Iterable<Movie> findAllMovies() {
		return movieRepository.findAll();
	}
	
	public Movie findMovieById(long movie_id) {
		return movieRepository.findOne(movie_id);
	}
	
	public List<MovieComment> findAllCommentsByMovieId(long movieId) {
		return movieCommentRepository.findByMovieId(movieId);
	}
	
}
