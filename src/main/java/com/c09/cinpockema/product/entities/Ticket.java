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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;

import com.c09.cinpockema.cinema.entities.Seat;

@Entity
public class Ticket {

	@Id
	@NotNull
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@ManyToOne(cascade = { CascadeType.MERGE,CascadeType.REFRESH }, optional = false)
	@JoinColumn(name="screening_id")
	private Screening screening;
	
	@OneToOne(cascade = { CascadeType.MERGE,CascadeType.REFRESH }, optional = false)
	@JoinColumn(name="seat_id")
	private Seat seat;
	
	@NotNull
	@Column(nullable=false)
	private double price;
	
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
		this.screening = screening;
	}
	
	public Seat getSeat() {
		return seat;
	}
	
	public void setSeat(Seat seat) {
		this.seat = seat;
	}
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
