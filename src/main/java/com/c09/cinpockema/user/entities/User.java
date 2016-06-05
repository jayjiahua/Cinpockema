package com.c09.cinpockema.user.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.c09.cinpockema.movie.entities.MovieComment;
import com.c09.cinpockema.cinema.entities.CinemaComment;
import com.c09.cinpockema.order.entities.Order;
import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
public class User implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6972959064945918099L;

	public enum ROLE {
		admin,
		user
	}

	public enum GENDER {
		unknow,
		male,
		female
	}

	static public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder(4);
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
    private ROLE role;

	@NotNull
	@Column(nullable=false, unique=true, length=30)
	private String username;

	@NotNull
	@Column(nullable=false)
	private String password;

	@Column(length=30)
	private String nickname;

	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private GENDER gender;

	@Column(length=128)
	private String avatarUrl;

	private String signature;

	@OneToMany(cascade = CascadeType.ALL, mappedBy ="user", fetch=FetchType.LAZY)
	List<MovieComment> movieComments = new ArrayList<MovieComment>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy ="user", fetch=FetchType.LAZY)
	List<CinemaComment> cinemaComments = new ArrayList<CinemaComment>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy ="user", fetch=FetchType.LAZY)
	List<Order> orders = new ArrayList<Order>();

	public String getNickname() {
		return nickname;
	}


	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	public GENDER getGender() {
		return gender;
	}


	public void setGender(GENDER gender) {
		this.gender = gender;
	}


	public String getAvatarUrl() {
		return avatarUrl;
	}


	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}


	public String getSignature() {
		return signature;
	}


	public void setSignature(String signature) {
		this.signature = signature;
	}


	public User() {
		super();
		setRole(User.ROLE.user);
		setGender(User.GENDER.unknow);
		setNickname("新用户");
	}


	public void setId(long id) {
		this.id = id;
	}


	public long getId() {
		return id;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonBackReference
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = getPasswordEncoder().encode(password);
	}


	public ROLE getRole() {
		return role;
	}

	public void setRole(ROLE role) {
		this.role = role;
	}

	public void addMovieComment(MovieComment movieComment) {
		movieComment.setUser(this);
		movieComments.add(movieComment);
	}
	
	public void addCinemaComment(CinemaComment cinemaComment) {
		cinemaComment.setUser(this);
		cinemaComments.add(cinemaComment);
	}
	
	public void addOrder(Order order) {
		orders.add(order);
	}

	@Override
	@JsonBackReference
	public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(getRole().name()));
        System.err.println("[" + username + ", " + nickname + ", " + getRole().name() + "]");
        return authorities;
	}

	@Override
	@JsonBackReference
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	@JsonBackReference
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	@JsonBackReference
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@JsonBackReference
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
