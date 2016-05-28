package com.c09.cinpockema.cinema.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.c09.cinpockema.product.entities.Screening;

@Entity
public class Hall {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@NotNull
	@Column(nullable=false)
	private String name;
	
	@ManyToOne(cascade = { CascadeType.MERGE,CascadeType.REFRESH }, optional = false)
    @JoinColumn(name="cinema_id")
	private Cinema cinema;
	
	private String description;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "hall", fetch = FetchType.LAZY)    
	List<Seat> seats = new ArrayList<Seat>();
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonBackReference
	public Cinema getCinema() {
		return cinema;
	}
	
	@JsonIgnore
	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@JsonBackReference
	public List<Seat> getSeats() {
		return seats;
	}
	
	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}
	
	public void addSeat(Seat seat) {
		seat.setHall(this);
		seats.add(seat);
	}
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "hall", fetch = FetchType.LAZY)    
	List<Screening> screenings = new ArrayList<Screening>();
	
	@JsonBackReference
	public List<Screening> getScreenings() {
		return screenings;
	}

	@JsonIgnore
	public void setScreenings(List<Screening> screenings) {
		this.screenings = screenings;
	}
	
	public void addScreening(Screening screening) {
		screening.setHall(this);
		screenings.add(screening);
	}
	
}