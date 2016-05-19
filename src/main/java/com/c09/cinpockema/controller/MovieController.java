package com.c09.cinpockema.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.c09.cinpockema.entities.Movie;
import com.c09.cinpockema.entities.MovieComment;
import com.c09.cinpockema.entities.User;
import com.c09.cinpockema.service.MovieService;
import com.c09.cinpockema.service.SessionService;

@RestController
@RequestMapping("/movies")
public class MovieController {

	@Autowired
	private MovieService movieService;

	@Autowired
	private SessionService sessionService;

    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public Iterable<Movie> listMovies() {
    	return movieService.listMovies();
    }

    @RequestMapping(value = {""}, method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('admin')")
    public void createMovie(@Valid @RequestBody Movie movie) {
    	movieService.createMovie(movie);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public Movie getMovieById(@PathVariable("id") long movieId) {
    	return movieService.getMovieById(movieId);
    }

    @RequestMapping(value={"/{id}"}, method=RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("hasAuthority('admin')")
    public void updateMovie(@Valid @RequestBody Movie movie, @PathVariable("id") long id) {
    	movie.setId(id);
    	movieService.updateMovie(movie);
    }

    @RequestMapping(value={"/{id}"}, method=RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('admin')")
    public void deleteMovieById(@PathVariable("id") long id) {
    	movieService.deleteMovieById(id);
    }

    @RequestMapping(value="/{id}/comments", method = RequestMethod.GET)
    public List<MovieComment> listCommentsByMovieId(@PathVariable("id") long id) {
    	return movieService.listCommentsByMovieId(id);
    }

    // curl localhost:8080/api/movies/4/comments -u admin:admin -H "Content-Type: application/json" -d "{\"score\": 1000, \"content\":\"eat some shit\"}"
    @RequestMapping(value={"/{id}/comments"}, method=RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('admin', 'user')")
    public void createComment(@Valid @RequestBody MovieComment movieComment, @PathVariable("id") long id) {
    	User user = sessionService.getCurrentUser();
    	Movie movie = movieService.getMovieById(id);
    	movieService.createComment(movieComment, movie, user);
    }

    @RequestMapping(value={"/{movieId}/comments/{commentId}"}, method=RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('admin', 'user')")
    public void deleteCommentById(@PathVariable("commentId") long id) {
    	User user = sessionService.getCurrentUser();
    	MovieComment movieComment = movieService.getCommentById(id);
			if (user.getId() == movieComment.getUser().getId()) {
				movieService.deleteComment(movieComment);
			}
    }
}
