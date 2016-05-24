package com.c09.cinpockema.movie.service;

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
@CacheConfig(cacheNames = "movies")
public class MovieService {

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private MovieCommentRepository movieCommentRepository;

    static private String DOUBAN_API = "http://api.douban.com/v2/movie/in_theaters?count=100";
    
    @Cacheable
	public Iterable<Movie> listMovies(int what) {
    	RestTemplate restTemplate = new RestTemplate();
    	ResponseEntity<String> responseEntity = restTemplate.getForEntity(DOUBAN_API, String.class);
    	String jsonResponse = responseEntity.getBody();
    	System.err.println(jsonResponse);
    	
    	int total = JsonPath.read(jsonResponse, "$.total");
    	List<Object> ratingList = JsonPath.read(jsonResponse, "$.subjects[*].rating.average");    	
    	List<List<String>> genersList = JsonPath.read(jsonResponse, "$.subjects[*].genres");
    	List<String> titleList = JsonPath.read(jsonResponse, "$.subjects[*].title");
    	List<String> originalTitleList =  JsonPath.read(jsonResponse, "$.subjects[*].original_title");
    	List<String> originalIdList = JsonPath.read(jsonResponse, "$.subjects[*].id");
    	List<String> imageUrlList = JsonPath.read(jsonResponse, "$.subjects[*].images.medium");

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
	        movieRepository.save(movie);
        }
		return movieRepository.findAll();
	}

	public Movie getMovieById(long id) {
		return movieRepository.findOne(id);
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
