package com.c09.cinpockema.helper;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;


import com.c09.cinpockema.cinema.entities.Cinema;
import com.c09.cinpockema.cinema.entities.CinemaComment;
import com.c09.cinpockema.cinema.entities.Hall;
import com.c09.cinpockema.cinema.entities.Seat;
import com.c09.cinpockema.cinema.entities.repositories.CinemaCommentRepository;
import com.c09.cinpockema.cinema.entities.repositories.CinemaRepository;
import com.c09.cinpockema.cinema.entities.repositories.HallRepository;
import com.c09.cinpockema.cinema.entities.repositories.SeatRepository;

import com.c09.cinpockema.movie.entities.Movie;
import com.c09.cinpockema.movie.entities.MovieComment;
import com.c09.cinpockema.movie.entities.repositories.MovieCommentRepository;
import com.c09.cinpockema.movie.entities.repositories.MovieRepository;
import com.c09.cinpockema.movie.service.MovieService;
import com.c09.cinpockema.user.entities.User;
import com.c09.cinpockema.user.entities.repositories.UserRepository;
import com.jayway.jsonpath.JsonPath;


@Component
public class DataInitHelper {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    MovieCommentRepository movieCommentRepository;
    
    @Autowired
    CinemaRepository cinemaRepository;
    
    @Autowired
    CinemaCommentRepository cinemaCommentRepository;
    
    @Autowired
    HallRepository hallRepository;
    
    @Autowired
    SeatRepository seatRepository;

    @Autowired
    MovieService movieService;
    
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
//    	movieService.listMovies();
    	
    }

//    @PostConstruct
//    public void movieDataInit(){
//        User user = new User();
//        user.setUsername("user-for-movie-comment");
//        user.setPassword("user");
//        user.setRole(User.ROLE.user);
//        userRepository.save(user);
//
//        for (int k = 0 ; k < 3 ; k++) {
//	        Movie movie = new Movie();
//	        movie.setTitle("movie-" + k);
//	        movie.setRating(8.8);
//	        movieRepository.save(movie);
//
//	        for (int i = 0 ; i < 3 ; i++) {
//		        MovieComment movieComment = new MovieComment();
//		        movieComment.setScore(i);
//		        movieComment.setContent("movie-" + k + "-comment-" + i);
//
//		        // Warning: Don't do this or you will ...
//		        // user.addMovieComment(movieComment);
//		        // Is it a f**king bug ?
//		        // You should do as follows:
//		        movieComment.setUser(user);
//
//		        movie.addMovieComment(movieComment);
//	        }
//	        movieRepository.save(movie);
//        }
//    }
    
    @PostConstruct
    public void cinemaDataInit() {
    	User user = new User();
        user.setUsername("user-for-cinema-comment");
        user.setPassword("user");
        userRepository.save(user);
        
        Movie movie1 = new Movie();
        movie1.setTitle("movie-1-for-cinema");
        movie1.setRating(8.8);
        movie1.setId(66666);
        Movie movie2 = new Movie();
        movie2.setTitle("movie-2-for-cinema");
        movie2.setRating(8.8);
        movie2.setId(77777);
        movieRepository.save(movie1);
        movieRepository.save(movie2);
        
        for (int k = 0 ; k < 3 ; k++) {
	        Cinema cinema = new Cinema();
	        cinema.setName("cinema-" + k);
	        cinema.setIntroduction("bull shit");
	        cinema.setLongitude(23.333);
	        cinema.setLatitude(23.333);
	        
	        for (int i = 0; i < 3; i++) {
	        	Hall hall = new Hall();
	        	hall.setName("cinema-" + k + "-hall-" + i);
	        	
	        	for (int j = 0; j < 3; j++) {
	        		Seat seat = new Seat();
	        		seat.setCol(j);
	        		seat.setRow(j);;
	        		
	        		hall.addSeat(seat);;
	        	}
	        	
	        	cinema.addHall(hall);
	        }
	        cinemaRepository.save(cinema);
	        
	        cinema.addMovie(movie1);
	        cinema.addMovie(movie2);

	        for (int i = 0 ; i < 3 ; i++) {
		        CinemaComment cinemaComment = new CinemaComment();
		        cinemaComment.setScore(i);
		        cinemaComment.setContent("cinema-" + k + "-comment-" + i);

		        cinemaComment.setUser(user);

		        cinema.addCinemaComment(cinemaComment);
	        }
	        cinemaRepository.save(cinema);
	        
        }
    }

    @PostConstruct
    public void foobarDataInit(){
    	// TODO movie data init
    	System.err.println("It seems work, hehe.");
    }
}
