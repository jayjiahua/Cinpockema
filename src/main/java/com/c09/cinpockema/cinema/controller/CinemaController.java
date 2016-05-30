package com.c09.cinpockema.cinema.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.c09.cinpockema.cinema.entities.Cinema;
import com.c09.cinpockema.cinema.entities.CinemaComment;
import com.c09.cinpockema.cinema.entities.Hall;
import com.c09.cinpockema.cinema.entities.Seat;
import com.c09.cinpockema.cinema.service.CinemaService;
import com.c09.cinpockema.user.entities.User;
import com.c09.cinpockema.user.entities.User.ROLE;
import com.c09.cinpockema.user.service.SessionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="影院模块", description="Cinema、CinemaComment、Hall以及Seat的CURD操作")
@RestController
@RequestMapping("/cinemas")
public class CinemaController {
	@Autowired
	private CinemaService cinemaService;

	@Autowired
	private SessionService sessionService;
	
	@ApiOperation(value="获取所有影院")
	@RequestMapping(value = {""}, method = RequestMethod.GET)
    public Iterable<Cinema> listCinemas() {
		return cinemaService.listCinemas();
	}
	
	@ApiOperation(value="创建一个影院")
	@RequestMapping(value = {""}, method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('admin')")
    public Cinema createCinema(@Valid @RequestBody Cinema cinema) {
		return cinemaService.createCinema(cinema);
	}
	
	@ApiOperation(value="根据城市Id获取当地所有影院")
	@RequestMapping(value = {"/cities/{cityId}"}, method = RequestMethod.GET)
    public List<Cinema> listCinemasByCityId(@PathVariable("cityId") int cityId) {
		return cinemaService.listCinemasByCityId(cityId);
	}
	
	@ApiOperation(value="获取单个影院的信息")
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
    public ResponseEntity<Cinema> getCinemaById(@PathVariable("id") long cinemaId) {
		Cinema cinema = cinemaService.getCinemaById(cinemaId);
		return new ResponseEntity<Cinema>(cinema, cinema != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}
	
	@ApiOperation(value="修改一个影院的信息")
	@RequestMapping(value={"/{id}"}, method=RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("hasAuthority('admin')")
    public void updateCinema(@Valid @RequestBody Cinema cinema, @PathVariable("id") long id) {
		cinema.setId(id);
		cinemaService.updataCinema(cinema);
	}
    
	@ApiOperation(value="删除一个影院")
	@RequestMapping(value={"/{id}"}, method=RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('admin')")
    public void deleteCinemaById(@PathVariable("id") long id) {
		cinemaService.deleteCinemaById(id);
	}
    
	@ApiOperation(value="获取一个影院的所有评论")
	@RequestMapping(value="/{id}/comments", method = RequestMethod.GET)
    public List<CinemaComment> listCommentsByCinemaId(@PathVariable("id") long id) {
		return cinemaService.listCommentsByCinemaId(id);
	}
	
	@ApiOperation(value="创建一个影院评论")
	@RequestMapping(value={"/{id}/comments"}, method=RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('admin', 'user')")
    public CinemaComment createComment(@Valid @RequestBody CinemaComment cinemaComment, @PathVariable("id") long id) {
		User user = sessionService.getCurrentUser();
		Cinema cinema = cinemaService.getCinemaById(id);
		return cinemaService.createComment(cinemaComment, cinema, user);
	}
	
	@ApiOperation(value="获取一个特定的影院评论")
	@RequestMapping(value={"/{cinemaId}/comments/{commentId}"}, method=RequestMethod.GET)
	@PreAuthorize("hasAnyAuthority('admin', 'user')")
	public ResponseEntity<CinemaComment> getCinemaCommentById(@PathVariable("cinemaId") long cinemaId, @PathVariable("commentId") long commentId) {
		User user = sessionService.getCurrentUser();
		CinemaComment cinemaComment = cinemaService.getCommentById(commentId);
		if (user.getRole() == ROLE.admin || user.getId() == cinemaComment.getUser().getId()) {
			if (cinemaComment.getCinema().getId() == cinemaId) {
				return new ResponseEntity<CinemaComment>(cinemaComment, cinemaComment != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<CinemaComment>(cinemaComment = null, HttpStatus.NOT_FOUND);
	}
	
	@ApiOperation(value="删除一个特定的影院评论")
	@RequestMapping(value={"/{cinemaId}/comments/{commentId}"}, method=RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('admin', 'user')")
    public void deleteCommentById(@PathVariable("cinemaId") long cinemaId, @PathVariable("commentId") long commentId) {
    	User user = sessionService.getCurrentUser();
    	CinemaComment cinemaComment = cinemaService.getCommentById(commentId);
    	if (user.getRole() == ROLE.admin || user.getId() == cinemaComment.getUser().getId()) {
    		if (cinemaComment.getCinema().getId() == cinemaId) {
    			cinemaService.deleteComment(cinemaComment);
    		}
    	}
	}
	
	@ApiOperation(value="获取一个影院所有影厅的信息")
	@RequestMapping(value="/{id}/halls", method = RequestMethod.GET)
	public List<Hall> listHallsByCinemaId(@PathVariable("id") long id) {
		return cinemaService.listHallsByCinemaId(id);
	}
	
	@ApiOperation(value="创建一个影厅")
	@RequestMapping(value={"/{id}/halls"}, method=RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('admin')")
    public Hall createHall(@Valid @RequestBody Hall hall, @PathVariable("id") long id) {
		Cinema cinema = cinemaService.getCinemaById(id);
		return cinemaService.createHall(hall, cinema);
	}
	
	@ApiOperation(value="获取一个影厅的信息")
	@RequestMapping(value={"/{cinemaId}/halls/{hallId}"}, method=RequestMethod.GET)
	@PreAuthorize("hasAnyAuthority('admin')")
	public ResponseEntity<Hall> getHallById(@PathVariable("cinemaId") long cinemaId, @PathVariable("hallId") long hallId) {
		Hall hall = cinemaService.getHallById(hallId);
		if (hall.getCinema().getId() == cinemaId) {
			return new ResponseEntity<Hall>(hall, hall != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Hall>(hall = null, HttpStatus.NOT_FOUND);
	}
	
	@ApiOperation(value="删除一个影厅")
	@RequestMapping(value={"/{cinemaId}/halls/{hallId}"}, method=RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('admin')")
    public void deleteHallById(@PathVariable("cinemaId") long cinemaId, @PathVariable("hallId") long hallId) {
    	Hall hall = cinemaService.getHallById(hallId);
    	if (hall.getCinema().getId() == cinemaId) {
    		cinemaService.deleteHall(hall);
    	}
	}
	
	@ApiOperation(value="获取一个影厅的所有座位")
	@RequestMapping(value="/{cinemaId}/halls/{hallId}/seats", method = RequestMethod.GET)
	public List<Seat> listSeatsByHallId(@PathVariable("cinemaId") long cinemaId, @PathVariable("hallId") long hallId) {
		Hall hall = cinemaService.getHallById(hallId);
		if (hall.getCinema().getId() == cinemaId) {
			return cinemaService.listSeatsByHallId(hallId);
		}
		return null;
	}
	
	@ApiOperation(value="创建一个座位")
	@RequestMapping(value={"/{cinemaId}/halls/{hallId}/seats"}, method=RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('admin')")
    public Seat createSeat(@Valid @RequestBody Seat seat, @PathVariable("cinemaId") long cinemaId, @PathVariable("hallId") long hallId) {
		Hall hall = cinemaService.getHallById(hallId);
		if (hall.getCinema().getId() == cinemaId) {
			return cinemaService.creatSeat(seat, hall);
		}
		return null;
	}

	@ApiOperation(value="获取一个座位的信息")
	@RequestMapping(value={"/{cinemaId}/halls/{hallId}/seats/{seatId}"}, method=RequestMethod.GET)
	@PreAuthorize("hasAnyAuthority('admin')")
	public ResponseEntity<Seat> getSeatById(@PathVariable("cinemaId") long cinemaId, @PathVariable("hallId") long hallId, @PathVariable("seatId") long seatId) {
		Hall hall = cinemaService.getHallById(hallId);
		Seat seat = cinemaService.getSeatById(seatId);
		if (hall.getCinema().getId() == cinemaId && seat.getHall().getId() == hallId) {
			return new ResponseEntity<Seat>(seat, seat != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Seat>(seat = null, HttpStatus.NOT_FOUND);
	}
	
	@ApiOperation(value="删除一个座位")
	@RequestMapping(value={"/{cinemaId}/halls/{hallId}/seats/{seatId}"}, method=RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('admin')")
    public void deleteHallById(@PathVariable("cinemaId") long cinemaId, @PathVariable("hallId") long hallId, @PathVariable("seatId") long seatId) {
    	Hall hall = cinemaService.getHallById(hallId);
    	Seat seat = cinemaService.getSeatById(seatId);
    	if (hall.getCinema().getId() == cinemaId && seat.getHall().getId() == hallId) {
    		cinemaService.deleteSeat(seat);
    	}
	}
	
}



