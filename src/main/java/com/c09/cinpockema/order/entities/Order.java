package com.c09.cinpockema.order.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.c09.cinpockema.product.entities.Ticket;
import com.c09.cinpockema.user.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="orders")
public class Order {

	public enum STATE {
		unPaid,
		paid,
		cancelled,
		finished
	}
	
	@Id
	@NotNull
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
    private STATE state;
	
	@ManyToOne(cascade = { CascadeType.MERGE,CascadeType.REFRESH }, optional = false)
    @JoinColumn(name="user_id")
	private User user;
	
	@NotNull
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

	@OneToMany(cascade = { CascadeType.MERGE,CascadeType.REFRESH },fetch=FetchType.LAZY)  //  级联保存、更新、删除、刷新;延迟加载
	List<Ticket> tickets = new ArrayList<Ticket>();
	
	@NotNull
	@Min(value=0)
	@Column(nullable=false)
	private double amount;
	
	public Order() {
		setState(Order.STATE.unPaid);
		setAmount(0);
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public STATE getState() {
		return state;
	}

	public void setState(STATE state) {
		this.state = state;
	}
	
	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}
	
	public void addTicket(Ticket ticket) {
		tickets.add(ticket);
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
