package com.c09.cinpockema.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c09.cinpockema.movie.entities.Movie;
import com.c09.cinpockema.movie.entities.MovieComment;
import com.c09.cinpockema.movie.entities.repositories.MovieCommentRepository;
import com.c09.cinpockema.movie.entities.repositories.MovieRepository;
import com.c09.cinpockema.user.entities.User;
import com.c09.cinpockema.user.entities.repositories.UserRepository;


@Service
public class DataInitService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    MovieCommentRepository movieCommentRepository;

    @PostConstruct
    public void userDataInit(){
        User admin = new User();
        admin.setPassword("admin");
        admin.setUsername("admin");
        admin.setRole(User.ROLE.admin);
        userRepository.save(admin);

        User user = new User();
        user.setPassword("user");
        user.setUsername("user");
        user.setRole(User.ROLE.user);
        userRepository.save(user);
    }

    @PostConstruct
    public void movieDataInit(){
        User user = new User();
        user.setUsername("user-for-comment");
        user.setPassword("user");
        userRepository.save(user);

        for (int k = 0 ; k < 3 ; k++) {
	        Movie movie = new Movie();
	        movie.setName("movie-" + k);
	        movie.setDescription("Some shit");
	        movieRepository.save(movie);

	        for (int i = 0 ; i < 3 ; i++) {
		        MovieComment movieComment = new MovieComment();
		        movieComment.setScore(i);
		        movieComment.setContent("comment-" + i);

		        // Warning: Don't do this or you will ...
		        // user.addMovieComment(movieComment);
		        // Is it a f**king bug ?
		        // You should do as follows:
		        movieComment.setUser(user);

		        movie.addMovieComment(movieComment);
	        }
	        movieRepository.save(movie);
        }
    }

    @PostConstruct
    public void foobarDataInit(){
    	// TODO movie data init
    	System.err.println("It seems work, hehe.");
    }
}
