package com.c09.cinpockema.movie.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import com.c09.cinpockema.cinema.entities.Cinema;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Movie {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@NotNull
	@Column(nullable=false)
	private String name;

	@NotNull
	@Column(nullable=false)
	private String description;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "movie", fetch = FetchType.LAZY)    
	List<MovieComment> movieComments = new ArrayList<MovieComment>();

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonBackReference
	public List<MovieComment> getMovieComments() {
		return movieComments;
	}

	public void setMovieComments(List<MovieComment> movieComments) {
		this.movieComments = movieComments;
	}

	public void addMovieComment(MovieComment movieComment) {
		movieComment.setMovie(this);
		movieComments.add(movieComment);
	}
	
	@ManyToMany(mappedBy="movies")
    private List<Cinema> cinemas = new ArrayList<Cinema>();

	@JsonBackReference
	public List<Cinema> getCinemas() {
		return cinemas;
	}

	@JsonIgnore
	public void setCinemas(List<Cinema> cinemas) {
		this.cinemas = cinemas;
	}
	
	public void addCinema(Cinema cinema) {
		cinemas.add(cinema);
	}
	
}
