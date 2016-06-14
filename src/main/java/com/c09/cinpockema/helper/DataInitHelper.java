package com.c09.cinpockema.helper;

import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;


import com.c09.cinpockema.cinema.entities.Cinema;
import com.c09.cinpockema.cinema.entities.CinemaComment;
import com.c09.cinpockema.cinema.entities.Hall;
import com.c09.cinpockema.cinema.entities.Seat;
import com.c09.cinpockema.order.entities.Order;
import com.c09.cinpockema.cinema.entities.repositories.CinemaCommentRepository;
import com.c09.cinpockema.cinema.entities.repositories.CinemaRepository;
import com.c09.cinpockema.cinema.entities.repositories.HallRepository;
import com.c09.cinpockema.cinema.entities.repositories.SeatRepository;

import com.c09.cinpockema.movie.entities.Movie;
import com.c09.cinpockema.movie.entities.MovieComment;
import com.c09.cinpockema.movie.entities.repositories.MovieCommentRepository;
import com.c09.cinpockema.movie.entities.repositories.MovieRepository;
import com.c09.cinpockema.movie.service.MovieService;
import com.c09.cinpockema.order.entities.repositories.OrderRepository;
import com.c09.cinpockema.user.entities.User;
import com.c09.cinpockema.user.entities.repositories.UserRepository;

import com.c09.cinpockema.product.entities.Screening;
import com.c09.cinpockema.product.entities.Ticket;
import com.c09.cinpockema.product.entities.repositories.ScreeningRepository;
import com.c09.cinpockema.product.entities.repositories.TicketRepository;
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
    
    @Autowired
    ScreeningRepository screeningRepository;

    @Autowired
    TicketRepository ticketRepository;
    
    @Autowired
	OrderRepository orderRepository;
    
    
    @PostConstruct
    public void dateInit() {
    	// 必须按顺序
    	userDataInit();
    	movieDataInit();
    	cinemaDataInit();
    	productDataInit();
    }
    
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


    public void movieDataInit(){
    	movieService.listMovies();    	
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
    

    public void cinemaDataInit() {
    	User user = new User();
        user.setUsername("user-for-cinema-comment");
        user.setPassword("user");
        userRepository.save(user);
        
//        Movie movie1 = new Movie();
//        movie1.setTitle("movie-1-for-cinema");
//        movie1.setRating(8.8);
//        movie1.setId(66666);
//        Movie movie2 = new Movie();
//        movie2.setTitle("movie-2-for-cinema");
//        movie2.setRating(8.8);
//        movie2.setId(77777);
//        movieRepository.save(movie1);
//        movieRepository.save(movie2);
        
        List<Movie> movies = movieRepository.findAll();
        
        for (int k = 0 ; k < 6 ; k++) {
	        Cinema cinema = new Cinema();
	        cinema.setName("cinema-" + k);
	        cinema.setIntroduction("bull shit");
	        cinema.setLongitude(23.333);
	        cinema.setLatitude(23.333);
	        cinema.setCityId(453);
	        cinema.setAddress("SYSU 231");
	        
	        for (int i = 0; i < 3; i++) {
	        	Hall hall = new Hall();
	        	hall.setName("cinema-" + k + "-hall-" + i);
	        	
	        	for (int j = 0; j < 3; j++) {
	        		Seat seat = new Seat();
	        		seat.setCol(j);
	        		seat.setRow(j);
	        		seat.setCoordinateX(j);
	        		seat.setCoordinateY(j);
	        		
	        		hall.addSeat(seat);;
	        	}
	        	
	        	cinema.addHall(hall);
	        }
	        cinemaRepository.save(cinema);
	        
	        for (int i = 0 ; i < movies.size() ; i++) {
	        	if (Math.random() > 0.5) {
	        		cinema.addMovie(movies.get(i));
	        	}
	        }

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
    
    public void productDataInit() {
    	/*
    	 * 新建影院、影厅及座位
    	 */
    	Cinema cinema = new Cinema();
        cinema.setName("cinema-for-Screening-and-Ticket");
        cinema.setIntroduction("holy shit");
        cinema.setLongitude(88.88);
        cinema.setLatitude(88.88);
        cinema.setCityId(231);
        cinema.setAddress("shantou");
        
        for (int i = 0; i < 3; i++) {
        	Hall hall = new Hall();
        	hall.setName("hall-for-Screening-and-Ticket-" + i);
        	
        	for (int j = 0; j < 3; j++) {
        		Seat seat = new Seat();
        		seat.setCol(j);
        		seat.setRow(j);
        		seat.setCoordinateX(j);
        		seat.setCoordinateY(j);
        		
        		hall.addSeat(seat);;
        	}
        	
        	cinema.addHall(hall);
        }
        cinemaRepository.save(cinema);
        
        /*
         * 新建电影
         */
        Movie movie = new Movie();
        movie.setTitle("movie-1-for-Screening");
        movie.setRating(8.8);
        movie.setId(110);
        movieRepository.save(movie);
        cinema.addMovie(movie);
//
//    	Cinema cinema = cinemaRepository.findOne(1L);
//    	Movie movie = movieRepository.findAll().get(0);
//    	Cinema cinema = movieService.listCinemasByMovieId(movie.getId()).get(0);
    	
    	
        /*
         * 新建场次和电影票
         */
        List<Hall> hallList = cinema.getHallls();
        for (Hall hall : hallList) {
        	Screening screening = new Screening();
    		screening.setCinema(cinema);
    		screening.setHall(hall);
    		screening.setMovie(movie);
    		screening.setRunningTime(120);
    		
    		try {
    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
				Date startTimeDate = sdf.parse("2016-06-01 09:30");
				screening.setStartTime(startTimeDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
    		
    		screeningRepository.save(screening);
    		
    		List<Seat> seatList = hall.getSeats();
    		for (Seat seat : seatList) {
    			Ticket ticket = new Ticket();
        		ticket.setPrice(40);
        		ticket.setScreening(screening);
        		ticket.setSeat(seat);
        		ticketRepository.save(ticket);
    		}
        }
    }
    
//    @PostConstruct
//    public void orderDataInit(){
//    	/*
//    	 * 新建用户
//    	 */
//    	User user = new User();
//        user.setUsername("user-for-Order");
//        user.setPassword("user");
//        userRepository.save(user);
//    	
//    	/*
//    	 * 新建影院、影厅及座位
//    	 */
//    	Cinema cinema = new Cinema();
//        cinema.setName("cinema-for-Order");
//        cinema.setIntroduction("holy shit");
//        cinema.setLongitude(88.88);
//        cinema.setLatitude(88.88);
//        cinema.setCityId(231);
//        cinema.setAddress("shantou");
//        
//        for (int i = 0; i < 3; i++) {
//        	Hall hall = new Hall();
//        	hall.setName("hall-for-Order-" + i);
//        	
//        	for (int j = 0; j < 3; j++) {
//        		Seat seat = new Seat();
//        		seat.setCol(j);
//        		seat.setRow(j);
//        		seat.setCoordinateX(j);
//        		seat.setCoordinateY(j);
//        		
//        		hall.addSeat(seat);;
//        	}
//        	
//        	cinema.addHall(hall);
//        }
//        cinemaRepository.save(cinema);
//        
//        /*
//         * 新建电影
//         */
//        Movie movie = new Movie();
//        movie.setTitle("movie-1-for-Order");
//        movie.setRating(8.8);
//        movie.setId(120);
//        movieRepository.save(movie);
//        cinema.addMovie(movie);
//        cinemaRepository.save(cinema);
//        
//        /*
//         * 新建场次、电影票和订单
//         */
//        List<Hall> hallList = cinema.getHallls();
//        for (Hall hall : hallList) {
//        	Screening screening = new Screening();
//    		screening.setCinema(cinema);
//    		screening.setHall(hall);
//    		screening.setMovie(movie);
//    		screening.setRunningTime(150);
//    		
//    		Order order = new Order();
//        	order.setUser(user);
//    		
//    		try {
//    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
//				Date startTimeDate = sdf.parse("2016-06-01 15:30");
//				Date createTimeDate = sdf.parse("2016-06-01 08:30");
//				screening.setStartTime(startTimeDate);
//				order.setCreateTime(createTimeDate);
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//    		
//    		screeningRepository.save(screening);
//    		
//    		List<Seat> seatList = hall.getSeats();
//    		for (Seat seat : seatList) {
//    			Ticket ticket = new Ticket();
//        		ticket.setPrice(50);
//        		ticket.setScreening(screening);
//        		ticket.setSeat(seat);
//        		ticketRepository.save(ticket);
//    		}
//    		
//    		List<Ticket> ticketList = screening.getTickets();
//    		order.setTickets(ticketList);
//    		
//    		orderRepository.save(order);
//        }
//    }

    @PostConstruct
    public void foobarDataInit(){
    	// TODO movie data init
    	System.err.println("It seems work, hehe.");
    }
}
