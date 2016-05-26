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
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Movie {

	@Id
	@NotNull
//	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@NotNull
	@Column(nullable=false)
	private String title;

	private String originalTitle;

	@NotNull
	@Column(nullable=false)
	private double rating;

	private String genres;

	private String imageUrl;
	
	
	private boolean onShow;


	
	public boolean isOnShow() {
		return onShow;
	}

	public void setOnShow(boolean onShow) {
		this.onShow = onShow;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getGenres() {
		return genres;
	}

	public void setGenres(String genres) {
		this.genres = genres;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	@OneToMany(cascade = CascadeType.ALL, mappedBy = "movie", fetch = FetchType.LAZY)    
	List<MovieComment> movieComments = new ArrayList<MovieComment>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

}
