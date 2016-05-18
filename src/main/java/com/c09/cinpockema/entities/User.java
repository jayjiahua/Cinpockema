package com.c09.cinpockema.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class User implements UserDetails {
	

	public enum ROLE {
		admin,
		user
	}
	
	static public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder(4);
	}
	

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;  
	
	@Enumerated(EnumType.STRING)
    private ROLE role;
	
	@Column(nullable=false, unique=true, length=30)
	private String username;  
	
	@Column(nullable=false)
	private String password;  

	public User() {
		super();
		setRole(User.ROLE.user);
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
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(getRole().name()));
        System.err.println("[" + username + ", " + getRole().name() + "]");
        return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
}
