package com.devsuperior.dsmovie.controllers;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.devsuperior.dsmovie.tests.TokenUtil;

import io.restassured.http.ContentType;

public class MovieControllerRA {
	
	private String clientUsername, clientPassword, adminUsername, adminPassword;
	private String adminToken, clientToken, invalidToken;
	private Integer movieId1;
	private String movieTitle1, movieTitle2;
	private Float movieScore1;
	private String movieImage1;
	private Map<String, Object> postMovieInstance;
	JSONObject newMovie;
	
	@BeforeEach
	public void setup() throws JSONException {
		baseURI = "http://localhost:8080";	

		clientUsername = "alex@gmail.com";
		clientPassword = "123456";
		adminUsername = "maria@gmail.com";
		adminPassword = "123456";
		
		clientToken = TokenUtil.obtainAccessToken(clientUsername, clientPassword);
		adminToken = TokenUtil.obtainAccessToken(adminUsername, adminPassword);
		invalidToken = adminToken + "xpto";
		
		movieId1 = 1;
		movieTitle1 = "The Witcher";
		movieScore1 = 4.5F;
		movieImage1 = "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/jBJWaqoSCiARWtfV0GlqHrcdidd.jpg";
		movieTitle2 = "Thor";
		
		postMovieInstance = new HashMap<>();
		postMovieInstance.put("title", "One Piece Film: Red");
		postMovieInstance.put("score", 5.0);
		postMovieInstance.put("image", "https://musicart.xboxlive.com/7/b67d6500-0000-0000-0000-000000000002/504/image.jpg?w=1920&h=1080");
		
		newMovie = new JSONObject(postMovieInstance);
	}
	
	@Test
	public void findAllShouldReturnOkWhenMovieNoArgumentsGiven() {
		given()
		.get("/movies?page=0")
	.then()
		.body("content.title", hasItems(movieTitle1, movieTitle2));
	}
	
	@Test
	public void findAllShouldReturnPagedMoviesWhenMovieTitleParamIsNotEmpty() {	
		given()
		.get("/movies?title={movieName}", movieTitle1)
	.then()
		.statusCode(200)
		.body("content.id[0]", is(movieId1))
		.body("content.title[0]", equalTo(movieTitle1))
		.body("content.score[0]", is(movieScore1))
		.body("content.image[0]", equalTo(movieImage1));
	}
	
	@Test
	public void findByIdShouldReturnMovieWhenIdExists() {		
		given()
			.get("/movies/{id}", movieId1)
		.then()
			.statusCode(200)
			.body("id", is(movieId1))
			.body("title", equalTo(movieTitle1))
			.body("score", is(movieScore1))
			.body("image", equalTo(movieImage1));
	}
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() {	
		given()
			.get("/movies/{id}", 999999999)
		.then()
			.statusCode(404)
			.body("error", equalTo("Recurso não encontrado"))
			.body("status", equalTo(404));
	}
	
	@Test
	public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndBlankTitle() throws JSONException {		
		given()
			.header("Content-type", "application/json")
			.header("Authorization", "Bearer " + adminToken)
			.body(newMovie)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post("/movies")
		.then()
			.statusCode(201)
			.body("title", equalTo("One Piece Film: Red"))
			.body("score", is(5.0f))
			.body("image", equalTo("https://musicart.xboxlive.com/7/b67d6500-0000-0000-0000-000000000002/504/image.jpg?w=1920&h=1080"));
	}
	
	@Test
	public void insertShouldReturnForbiddenWhenClientLogged() throws Exception {		
		given()
			.header("Content-type", "application/json")
			.header("Authorization", "Bearer " + clientToken)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(newMovie)
		.when()
			.post("/movies")
		.then()
			.statusCode(403);
	}
	
	@Test
	public void insertShouldReturnUnauthorizedWhenInvalidToken() throws Exception {		
		given()
			.header("Content-type", "application/json")
			.header("Authorization", "Bearer " + invalidToken)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(newMovie)
		.when()
			.post("/movies")
		.then()
			.statusCode(401);
	}
}
