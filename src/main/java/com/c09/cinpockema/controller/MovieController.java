package com.c09.cinpockema.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.c09.cinpockema.entities.Movie;
import com.c09.cinpockema.entities.MovieComment;
import com.c09.cinpockema.entities.User;
import com.c09.cinpockema.service.MovieService;

@RestController
@RequestMapping("/movie")
public class MovieController {
	
	@Autowired
	private MovieService movieService;
	
    @RequestMapping(value={"", "/"}, method = RequestMethod.GET)
    public Iterable<Movie> getAllMovies() {
    	return movieService.findAllMovies();
    }
    
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public Movie getMovieById(@PathVariable("id") long movieId) {
    	return movieService.findMovieById(movieId);
    }
    
    @RequestMapping(value="/{id}/comments", method = RequestMethod.GET)
    public List<MovieComment> getCommentsByMovieId(@PathVariable("id") long movieId) {
    	return movieService.findAllCommentsByMovieId(movieId);
    }
}
