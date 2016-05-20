package com.c09.cinpockema.movie.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c09.cinpockema.movie.entities.Movie;
import com.c09.cinpockema.movie.entities.MovieComment;
import com.c09.cinpockema.movie.entities.repositories.MovieCommentRepository;
import com.c09.cinpockema.movie.entities.repositories.MovieRepository;
import com.c09.cinpockema.user.entities.User;

@Service
public class MovieService {

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private MovieCommentRepository movieCommentRepository;

	public Iterable<Movie> listMovies() {
		return movieRepository.findAll();
	}

	public Movie getMovieById(long id) {
		return movieRepository.findOne(id);
	}

	public List<MovieComment> listCommentsByMovieId(long id) {
		return movieCommentRepository.findByMovieId(id);
	}

	public Movie createMovie(Movie movie) {
		return movieRepository.save(movie);
	}

	public MovieComment getCommentById(long id) {
		return movieCommentRepository.findOne(id);
	}

	public MovieComment createComment(MovieComment movieComment, Movie movie, User user) {
        movieComment.setUser(user);
        movieComment.setMovie(movie);
        return movieCommentRepository.save(movieComment);
	}

	public void deleteComment(MovieComment movieComment) {
		movieComment.setUser(null);
		movieComment.setMovie(null);
		movieCommentRepository.delete(movieComment);
	}

	public Movie updateMovie(Movie movie) {
		return movieRepository.save(movie);
	}

	public void deleteMovieById(long id) {
		movieRepository.delete(id);
	}


}
