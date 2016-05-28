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
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.c09.cinpockema.movie.entities.Movie;
import com.c09.cinpockema.product.entities.Screening;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Cinema {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@NotNull
	@Column(nullable=false)
	private String name;
	
	@NotNull
	@Column(nullable=false)
	private String introduction;
	
	private String phone;
	
	@Min(value=0)
	@Max(value=10)
	private Double score;
	
	@NotNull
	@Min(value=-180)
	@Max(value=180)
	@Column(nullable=false)
	private Double longitude;
	
	@NotNull
	@Min(value=-90)
	@Max(value=90)
	@Column(nullable=false)
	private Double latitude;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cinema", fetch = FetchType.LAZY)
	List<CinemaComment> cinemaComments = new ArrayList<CinemaComment>();

	@ManyToMany(cascade = { CascadeType.MERGE,CascadeType.REFRESH })
    @JoinTable(name="cinema_movie",
    	joinColumns={@JoinColumn(name="cinema_id")},
    	inverseJoinColumns={@JoinColumn(name="movie_id")})
    private List<Movie> movies = new ArrayList<Movie>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cinema", fetch = FetchType.LAZY)    
	List<Hall> halls = new ArrayList<Hall>();
	
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
	
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	
	public String getIntroduction() {
		return introduction;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setScore(Double score) {
		this.score = score;
	}
	
	public Double getScore() {
		return score;
	}
	
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public Double getLongitude() {
		return longitude;
	}
	
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	public Double getLatitude() {
		return latitude;
	}
	
	@JsonBackReference
	public List<CinemaComment> getCinemaComments() {
		return cinemaComments;
	}

	public void setCinemaComments(List<CinemaComment> cinemaComments) {
		this.cinemaComments = cinemaComments;
	}

	public void addCinemaComment(CinemaComment cinemaComment) {
		cinemaComment.setCinema(this);
		cinemaComments.add(cinemaComment);
	}
	
	@JsonBackReference
	public List<Movie> getMovies() {
		return movies;
	}
	
	@JsonIgnore
	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}
	
	public void addMovie(Movie movie) {
		movies.add(movie);
	}
	
	@JsonBackReference
	public List<Hall> getHallls() {
		return halls;
	}
	
	@JsonIgnore
	public void setHalls(List<Hall> halls) {
		this.halls = halls;
	}
	
	public void addHall(Hall hall) {
		hall.setCinema(this);
		halls.add(hall);
	}
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cinema", fetch = FetchType.LAZY)    
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
		screenings.add(screening);
	}
}