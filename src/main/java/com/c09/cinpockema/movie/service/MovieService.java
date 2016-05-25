package com.c09.cinpockema.movie.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.c09.cinpockema.movie.entities.Movie;
import com.c09.cinpockema.movie.entities.MovieComment;
import com.c09.cinpockema.movie.entities.repositories.MovieCommentRepository;
import com.c09.cinpockema.movie.entities.repositories.MovieRepository;
import com.c09.cinpockema.user.entities.User;
import com.jayway.jsonpath.JsonPath;

@Service
public class MovieService {

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private MovieCommentRepository movieCommentRepository;

    static private String DOUBAN_ON_SHOW_API = "http://api.douban.com/v2/movie/in_theaters?count=100";
    
    static private String DOUBAN_DETAIL_API = "http://api.douban.com/v2/movie/subject/";
    
    @Cacheable(value = "movieList")
	public Iterable<Movie> listMovies() {
    	System.out.println("Cache Miss!");
    	
    	System.out.println("Make all movies off show");
    	List<Movie> movies = movieRepository.findAll();
    	for (Movie movie: movies) {
    		movie.setOnShow(false);
    	}
    	movieRepository.save(movies);
    	
    	System.out.println("Getting on show movies from douban");    	
    	RestTemplate restTemplate = new RestTemplate();
    	ResponseEntity<String> responseEntity = restTemplate.getForEntity(DOUBAN_ON_SHOW_API, String.class);
    	String jsonResponse = responseEntity.getBody();
    	
    	int total = JsonPath.read(jsonResponse, "$.total");
    	List<Object> ratingList = JsonPath.read(jsonResponse, "$.subjects[*].rating.average");    	
    	List<List<String>> genersList = JsonPath.read(jsonResponse, "$.subjects[*].genres");
    	List<String> titleList = JsonPath.read(jsonResponse, "$.subjects[*].title");
    	List<String> originalTitleList =  JsonPath.read(jsonResponse, "$.subjects[*].original_title");
    	List<String> originalIdList = JsonPath.read(jsonResponse, "$.subjects[*].id");
    	List<String> imageUrlList = JsonPath.read(jsonResponse, "$.subjects[*].images.medium");

    	List<Movie> onShowMovies = new ArrayList<Movie>();
    	
        for (int k = 0 ; k < total ; k++) {

	        Movie movie = new Movie();
	        
	        Double rating;
	        try {
	        	rating = (Double) ratingList.get(k);
	        } catch (Exception e) {
	        	Integer temp = (Integer) ratingList.get(k);
	        	rating = temp.doubleValue();
	        }
	        movie.setRating(rating);
	        movie.setGenres(StringUtils.collectionToDelimitedString(genersList.get(k), ","));
	        movie.setTitle(titleList.get(k));
	        movie.setOriginalId(originalIdList.get(k));
	        movie.setOriginalTitle(originalTitleList.get(k));
	        movie.setImageUrl(imageUrlList.get(k));
	        movie.setOnShow(true);
	        
	        for (Movie originalMovie: movies) {
	    		if (originalMovie.getOriginalId().equals(movie.getOriginalId())) {
	    			movie.setId(originalMovie.getId());
	    		}
	    	}
	        
    		onShowMovies.add(movie);
    		
	    }
        movieRepository.save(onShowMovies);
		return onShowMovies;
	}

	@Cacheable(value = "movieInfo")
	public Movie getMovieById(long id) {
		System.out.println("Cache Miss!");
		return movieRepository.findOne(id);
	}
	
	@Cacheable(value = "movieDetails")
	public String getMovieDetails(String originalId) {
    	RestTemplate restTemplate = new RestTemplate();
    	ResponseEntity<String> responseEntity = restTemplate.getForEntity(DOUBAN_DETAIL_API + originalId, String.class);
    	return responseEntity.getBody();
	}

	public List<MovieComment> listCommentsByMovieId(long id) {
		return movieCommentRepository.findByMovieId(id);
	}

	public Movie createMovie(Movie movie) {
		return movieRepository.save(movie);
	}

	public MovieComment getCommentById(long id) {
		return movieCommentRepository.findOne(id);
	}

	public MovieComment createComment(MovieComment movieComment, Movie movie, User user) {
        movieComment.setUser(user);
        movieComment.setMovie(movie);
        return movieCommentRepository.save(movieComment);
	}

	public void deleteComment(MovieComment movieComment) {
		movieComment.setUser(null);
		movieComment.setMovie(null);
		movieCommentRepository.delete(movieComment);
	}

	public Movie updateMovie(Movie movie) {
		return movieRepository.save(movie);
	}

	public void deleteMovieById(long id) {
		movieRepository.delete(id);
	}


}
