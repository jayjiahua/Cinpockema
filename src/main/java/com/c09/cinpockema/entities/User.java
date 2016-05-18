package com.c09.cinpockema.entities;

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

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
public class User implements UserDetails {
	
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
	
	@Column(nullable=false, unique=true, length=30)
	private String username;  
	
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

	@JsonBackReference
	public List<MovieComment> getMovieComments() {
		return movieComments;
	}


	public void setMovieComments(List<MovieComment> movieComments) {
		this.movieComments = movieComments;
	}


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