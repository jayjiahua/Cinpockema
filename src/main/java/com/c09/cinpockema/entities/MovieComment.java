package com.c09.cinpockema.entities;


import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class MovieComment {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@NotNull
	@Min(value=0)
	@Max(value=10)
	@Column(nullable=false)
	private Integer score;

	@NotNull
	@Size(max=1024)
	@Column(nullable=false, length=1024)
	private String content;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	@ManyToOne(cascade = { CascadeType.MERGE,CascadeType.REFRESH }, optional = false)
    @JoinColumn(name="user_id")
	private User user;

	public MovieComment() {
		setCreateTime(new Date(System.currentTimeMillis()));
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@ManyToOne(cascade = { CascadeType.MERGE,CascadeType.REFRESH }, optional = false)
    @JoinColumn(name="movie_id")
	private Movie movie;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@JsonBackReference
	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

}
