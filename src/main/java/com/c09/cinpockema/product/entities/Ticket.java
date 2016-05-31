package com.c09.cinpockema.product.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;

import com.c09.cinpockema.cinema.entities.Seat;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Ticket {

	@Id
	@NotNull
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@ManyToOne(cascade = { CascadeType.MERGE,CascadeType.REFRESH }, optional = false)
	@JoinColumn(name="screening_id")
	private Screening screening;
	
	@ManyToOne(cascade = { CascadeType.MERGE,CascadeType.REFRESH }, optional = false)
	@JoinColumn(name="seat_id")
	private Seat seat;
	
	@NotNull
	@Column(nullable=false)
	private double price;
	
	@NotNull
	private long tempSeatId;
	
	public Ticket() {}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public Screening getScreening() {
		return screening;
	}
	
	public void setScreening(Screening screening) {
		screening.addTicket(this);
		this.screening = screening;
	}
	
	public Seat getSeat() {
		return seat;
	}
	
	public void setSeat(Seat seat) {
		seat.addTicket(this);
		this.seat = seat;
	}
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	@JsonBackReference
	public long getSeatId() {
		return tempSeatId;
	}
	
	public void setTempSeatId(long tempSeatId) {
		this.tempSeatId = tempSeatId;
	}
}
