package com.c09.cinpockema.product.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;

import com.c09.cinpockema.movie.entities.Movie;
import com.c09.cinpockema.cinema.entities.Cinema;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.c09.cinpockema.cinema.entities.Hall;

@Entity
public class Screening {

	@Id
	@NotNull
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	// 电影开始时间，类型为字符串
	@NotNull
	@Column(nullable=false)
	private String startTime;
	
	// 电影片长分钟数，类型为整数
	@NotNull
	@Column(nullable=false)
	private int runningTime;
	
	@ManyToOne(cascade = { CascadeType.MERGE,CascadeType.REFRESH }, optional = false)
	@JoinColumn(name="movie_id")
	private Movie movie;
	
	@ManyToOne(cascade = { CascadeType.MERGE,CascadeType.REFRESH }, optional = false)
	@JoinColumn(name="hall_id")
	private Hall hall;
	
	@ManyToOne(cascade = { CascadeType.MERGE,CascadeType.REFRESH }, optional = false)
	@JoinColumn(name="cinema_id")
	private Cinema cinema;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "screening", fetch = FetchType.LAZY)    
	List<Ticket> tickets = new ArrayList<Ticket>();
	
	public Screening() {}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getStartTime() {
		return startTime;
	}
	
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public int getRunningTime() {
		return runningTime;
	}
	
	public void setRunningTime(int runningTime) {
		this.runningTime = runningTime;
	}
	
	public Movie getMovie() {
		return movie;
	}
	
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	
	public Hall getHall() {
		return hall;
	}
	
	public void setHall(Hall hall) {
		this.hall = hall;
	}
	
	@JsonBackReference
	public List<Ticket> getTickets() {
		return tickets;
	}

	@JsonIgnore
	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}
	
	public void addTicket(Ticket ticket) {
		ticket.setScreening(this);
		tickets.add(ticket);
	}
}
