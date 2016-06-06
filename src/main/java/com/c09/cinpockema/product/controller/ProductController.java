package com.c09.cinpockema.product.controller;

import javax.validation.Valid;
import java.util.List;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.c09.cinpockema.user.service.SessionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.c09.cinpockema.product.service.ProductService;
import com.c09.cinpockema.cinema.service.CinemaService;
import com.c09.cinpockema.movie.service.MovieService;
import com.c09.cinpockema.user.entities.User;
import com.c09.cinpockema.user.entities.User.ROLE;
import com.c09.cinpockema.cinema.entities.Cinema;
import com.c09.cinpockema.cinema.entities.Hall;
import com.c09.cinpockema.movie.entities.Movie;
import com.c09.cinpockema.cinema.entities.Seat;
import com.c09.cinpockema.product.entities.Screening;
import com.c09.cinpockema.product.entities.Ticket;

@Api(value="产品模块", description="Screening、Ticket的CURD操作")
@RestController
@RequestMapping("/cinemas")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@ApiOperation(value="根据影院ID获取所有场次")
	@RequestMapping(value={"/{cinemaId}/screenings"}, method=RequestMethod.GET)
	public List<Screening> listScreeningsByCinameId(@PathVariable("cinemaId") long cinemaId) {
		return productService.listScreeningsByCinameId(cinemaId);
	}
	
	@ApiOperation(value="根据场次ID获取一个场次")
	@RequestMapping(value={"/{cinemaId}/screenings/{screeningId}"}, method=RequestMethod.GET)
	public Screening getScreeningById(@PathVariable("cinemaId") long cinemaId, @PathVariable("screeningId") long screeningId) {
		return productService.getScreeningById(screeningId);
	}
	
	@ApiOperation(value="创建一个场次，场次的hallId、movieId封装在json内，key值为hallId、movieId, startTime类型为String，格式：\"yyyy-MM-dd hh:mm\"")
	@RequestMapping(value = {"/{cinemaId}/screenings"}, method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('admin')")
    public Screening createScreening(@PathVariable("cinemaId") long cinemaId, @Valid @RequestBody String jsonString) {
		JSONObject json = new JSONObject(jsonString);
		Screening screening = new Screening();
		screening.setRunningTime(json.getInt("runningTime"));
		
		String startTimeString = json.getString("startTime");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");  
	    Date startTimeDate;
		try {
			startTimeDate = sdf.parse(startTimeString);
			screening.setStartTime(startTimeDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
				
		long hallId = json.getLong("hallId");
		long movieId = json.getLong("movieId");
		return productService.createScreening(screening, hallId, movieId);
	}
	
	@ApiOperation(value="根据场次ID删除一个场次")
	@RequestMapping(value={"/{cinemaId}/screenings/{screeningId}"}, method=RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('admin')")
    public void deleteScreeningById(@PathVariable("cinemaId") long cinemaId, @PathVariable("screeningId") long screeningId) {
		productService.deleteScreeningById(screeningId);
	}
	
	@ApiOperation(value="根据场次ID获取所有电影票")
	@RequestMapping(value={"/{cinemaId}/screenings/{screeningId}/tickets"}, method=RequestMethod.GET)
	public List<Ticket> listTicketsByScreeningId(@PathVariable("cinemaId") long cinemaId, @PathVariable("screeningId") long screeningId) {
		return productService.listTicketsByScreeningId(screeningId);
	}
	
	@ApiOperation(value="根据电影票ID获取电影票")
	@RequestMapping(value={"/{cinemaId}/screenings/{screeningId}/tickets/{ticketId}"}, method=RequestMethod.GET)
	public ResponseEntity<Ticket> getTicketById(
			@PathVariable("cinemaId") long cinemaId,
			@PathVariable("screeningId") long screeningId,
			@PathVariable("ticketId") long ticketId) {
		Ticket ticket = productService.getTicketById(ticketId);
		return new ResponseEntity<Ticket>(ticket, ticket != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}
	
	@ApiOperation(value="创建一张电影票，电影票的seatId封装在json内，key值为seatId")
	@RequestMapping(value = {"/{cinemaId}/screenings/{screeningId}/tickets"}, method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('admin')")
    public Ticket createTicket(
    		@Valid @RequestBody String jsonString,
    		@PathVariable("cinemaId") long cinemaId,
    		@PathVariable("screeningId") long screeningId) {
		JSONObject json = new JSONObject(jsonString);
		Ticket ticket = new Ticket();
		ticket.setPrice(json.getDouble("price"));
		long seatId = json.getLong("seatId");
		return productService.createTicket(ticket, screeningId, seatId);
	}
	
	@ApiOperation(value="根据电影票ID删除一张电影票")
	@RequestMapping(value={"/{cinemaId}/screenings/{screeningId}/tickets/{ticketId}"}, method=RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('admin')")
    public void deleteTicketById(
    		@PathVariable("cinemaId") long cinemaId,
    		@PathVariable("screeningId") long screeningId,
    		@PathVariable("ticketId") long ticketId) {
		productService.deleteTicketById(ticketId);
	}
}
