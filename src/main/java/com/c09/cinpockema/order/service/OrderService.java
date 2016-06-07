package com.c09.cinpockema.order.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c09.cinpockema.order.entities.repositories.OrderRepository;
import com.c09.cinpockema.user.entities.User;
import com.c09.cinpockema.order.entities.Order;
import com.c09.cinpockema.product.entities.Ticket;
import com.c09.cinpockema.product.entities.repositories.TicketRepository;

@Service
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private TicketRepository ticketRepository;
	
	public Iterable<Order> listOrders() {
		return orderRepository.findAll();
	}
	
	public Order createOrder(String jsonString, User user) {
		Order order = new Order();
		JSONObject json = new JSONObject(jsonString);
		
		/*
		 * 解析json的key、value值，并设置相应的order属性
		 */
		order.setUser(user);
		/*
		 * 设置“创建时间”属性
		 */
		String createTimeString = json.getString("createTime");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");  
	    Date createTimeDate;
		try {
			createTimeDate = sdf.parse(createTimeString);
			order.setCreateTime(createTimeDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		/*
		 * 设置Tickets和amount总价属性
		 */
		double amount = 0;
		JSONArray ticketsIdArray = json.getJSONArray("ticketsId");
		for (int i = 0; i < ticketsIdArray.length(); ++i) {
			Ticket ticket = ticketRepository.findOne(ticketsIdArray.getLong(i));
			order.addTicket(ticket);
			amount += ticket.getPrice();
		}
		order.setAmount(amount);
		
		return orderRepository.save(order);
	}
	
	public Order getOrderById(long id) {
		return orderRepository.findOne(id);
	}
	
	public Order updateOrder(Order order) {   
		return orderRepository.save(order);   
	}
	
	public void deleteOrderById(long id) {   
		orderRepository.delete(id);   
	}
}
