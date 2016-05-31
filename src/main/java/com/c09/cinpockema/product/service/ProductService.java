package com.c09.cinpockema.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c09.cinpockema.product.entities.Screening;
import com.c09.cinpockema.product.entities.Ticket;
import com.c09.cinpockema.product.entities.repositories.ScreeningRepository;
import com.c09.cinpockema.product.entities.repositories.TicketRepository;
import com.c09.cinpockema.cinema.entities.Hall;
import com.c09.cinpockema.movie.entities.Movie;
import com.c09.cinpockema.movie.service.MovieService;
import com.c09.cinpockema.cinema.entities.Seat;
import com.c09.cinpockema.cinema.service.CinemaService;

@Service
public class ProductService {
	@Autowired
	private ScreeningRepository screeningRepository;
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private CinemaService cinemaService;
	
	@Autowired
	private MovieService movieService;
	
	public Screening createScreening(Screening screening) {
		Hall hall = cinemaService.getHallById(screening.getHallId());
		Movie movie = movieService.getMovieById(screening.getMovieId());
		screening.setHall(hall);
		screening.setMovie(movie);
		screening.setCinema(hall.getCinema());
		return screeningRepository.save(screening);
	}
	
	public Screening getScreeningById(long id) {
		return screeningRepository.findOne(id);
	}
	
	public Screening updateScreening(Screening screening) {
		return screeningRepository.save(screening);
	}
	
	public void deleteScreeningById(long id) {
		screeningRepository.delete(id);
	}
	
	public Ticket createTicket(Ticket ticket, Screening screening) {
		ticket.setScreening(screening);
		ticket.setSeat(cinemaService.getSeatById(ticket.getSeatId()));
		System.out.println(ticket.getSeatId());
		return ticketRepository.save(ticket);
	}
	
	public Ticket getTicketById(long id) {
		return ticketRepository.findOne(id);
	}
	
	public Ticket updateTicket(Ticket ticket) {
		return ticketRepository.save(ticket);
	}
	
	public void deleteTicketById(long id) {
		ticketRepository.delete(id);
	}
	
	public List<Screening> listScreeningsByCinameId(long id) {
		return screeningRepository.findByCinemaId(id);
	}
	
	public List<Ticket> listTicketsByScreeningId(long id) {
		return ticketRepository.findByScreeningId(id);
	}
}
