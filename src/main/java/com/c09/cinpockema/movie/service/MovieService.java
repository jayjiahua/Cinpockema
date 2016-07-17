package com.c09.cinpockema.movie.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.neo4j.cypher.internal.compiler.v2_2.commands.indexQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.c09.cinpockema.cinema.entities.Cinema;
import com.c09.cinpockema.movie.entities.Movie;
import com.c09.cinpockema.movie.entities.MovieComment;
import com.c09.cinpockema.movie.entities.repositories.MovieCommentRepository;
import com.c09.cinpockema.movie.entities.repositories.MovieRepository;
import com.c09.cinpockema.user.entities.User;
import com.c09.cinpockema.user.entities.repositories.UserRepository;
import com.jayway.jsonpath.JsonPath;

@Service
@CacheConfig(cacheNames="movieService")
public class MovieService {

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private MovieCommentRepository movieCommentRepository;

    static private String DOUBAN_ON_SHOW_API = "http://api.douban.com/v2/movie/in_theaters?count=100";
    
    static private String DOUBAN_DETAIL_API = "http://api.douban.com/v2/movie/subject/";
    
    /**
     * 获取电影列表
     * 
     * @return Movie实体列表
     */
    @Cacheable
	public Iterable<Movie> listMovies() {

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
	        movie.setId(Long.parseLong(originalIdList.get(k)));
	        movie.setOriginalTitle(originalTitleList.get(k));
	        movie.setImageUrl(imageUrlList.get(k));
	        movie.setOnShow(true);
	        
    		onShowMovies.add(movie);
    		
	    }
        
        movieRepository.save(onShowMovies);
		return onShowMovies;
	}

    
    /**
     * 获取单部电影
     * 
     * @param id 电影id
     * @return 单个Movie实体
     */
	@Cacheable(condition = "#result != null")
	public Movie getMovieById(long id) {
		return movieRepository.findOne(id);
	}
	
	/**
	 * 获取单部电影详情
	 * 
	 * @param originalId 电影id
	 * @return	豆瓣电影API返回的json字符串
	 */
	@Cacheable
	public Map<String, Object> getMovieDetails(String originalId) {
    	RestTemplate restTemplate = new RestTemplate();
    	ResponseEntity<String> responseEntity = restTemplate.getForEntity(DOUBAN_DETAIL_API + originalId, String.class);
    	String detailString = responseEntity.getBody();
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("summary", JsonPath.read(detailString, "$.summary"));
    	map.put("wishCount", JsonPath.read(detailString, "$.wish_count"));
    	
    	List<Map<String, String>> casts = new ArrayList<Map<String, String>>();
    	List<String> castNameList = JsonPath.read(detailString, "$.casts[*].name");
    	List<String> castAvatarUrlList = JsonPath.read(detailString, "$.casts[*].avatars.small");    	
    	for (int i = 0 ; i < castNameList.size() ; i++) {
    		Map<String, String> m = new HashMap<String, String>();
    		m.put("name", castNameList.get(i));
    		m.put("avatarUrl", castAvatarUrlList.get(i));
    		casts.add(m);
    	}
    	// map.put("casts", casts);
    	
    	List<Map<String, String>> directors = new ArrayList<Map<String, String>>();
    	List<String> directorNameList = JsonPath.read(detailString, "$.casts[*].name");
    	List<String> directorAvatarUrlList = JsonPath.read(detailString, "$.casts[*].avatars.small");    	
    	for (int i = 0 ; i < directorNameList.size() ; i++) {
    		Map<String, String> m = new HashMap<String, String>();
    		m.put("name", directorNameList.get(i));
    		m.put("avatarUrl", directorAvatarUrlList.get(i));
    		directors.add(m);
    	}
    	// map.put("directors", directors);

    	return map;
	}

	/**
	 * 按照电影id获取评论列表
	 * 
	 * @param id  电影id
	 * @return	MovieComments的实体列表
	 */
	public List<MovieComment> listCommentsByMovieId(long id) {
		return movieCommentRepository.findByMovieId(id);
	}

	/**
	 * （已废弃） 创建电影
	 * 
	 * @param movie Movie实体
	 * @return Movie实体
	 */
	@Deprecated
	public Movie createMovie(Movie movie) {
		return movieRepository.save(movie);
	}

	/**
	 * 获取某个id的评论
	 * 
	 * @param id 评论id
	 * @return	MovieComment实体
	 */
	public MovieComment getCommentById(long id) {
		return movieCommentRepository.findOne(id);
	}

	/**
	 * 创建评论
	 * 
	 * @param movieComment 评论实体
	 * @param movie 电影实体
	 * @param user 用户实体
	 * @return  创建成功后的评论实体
	 */
	public MovieComment createComment(MovieComment movieComment, Movie movie, User user) {
        movieComment.setUser(user);
        movieComment.setMovie(movie);
        return movieCommentRepository.save(movieComment);
	}

	/**
	 * 删除评论
	 * 
	 * @param movieComment 评论实体
	 */
	public void deleteComment(MovieComment movieComment) {
		movieComment.setUser(null);
		movieComment.setMovie(null);
		movieCommentRepository.delete(movieComment);
	}

	/**
	 * （已废弃） 更新电影信息
	 * 
	 * @param movie 电影实体
	 * @return
	 */
	@Deprecated
	public Movie updateMovie(Movie movie) {
		return movieRepository.save(movie);
	}

	/**
	 * 删除电影
	 * 
	 * @param id 电影id
	 */
	public void deleteMovieById(long id) {
		movieRepository.delete(id);
	}


	public List<Cinema> listCinemasByMovieId(long id) {
		return movieRepository.findOne(id).getCinemas();
	}
	
//	public List<User> listCollectorByMovieId(long id) {
//		return movieRepository.findOne(id).getUsers();
//	}
	
	public void collectMovie(long movieId, User user) {
		movieRepository.findOne(movieId).addUser(user);
		userRepository.save(user);
	}
	
	public void cancelCollectMovie(long movieId, User user) {
		movieRepository.findOne(movieId).removeUser(user);
		userRepository.save(user);
	}
	
	@Autowired
	private UserRepository userRepository;
	
	public List<Movie> listUserMovieCollections(User user) {
		return userRepository.findOne(user.getId()).getMovies();
	}
}
