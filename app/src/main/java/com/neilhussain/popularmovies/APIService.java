package com.neilhussain.popularmovies;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by NeilHussain on 7/17/15.
 */

public interface APIService {

    @Headers("Accept: application/json")
    @GET("/discover/movie")
    Page getMovies(@Query("sort_by") String sort, @Query("api_key") String key);

    @Headers("Accept: application/json")
    @GET("/discover/movie")
    void getMoviesCallback(@Query("sort_by") String sort, @Query("api_key") String key, Callback<Page> callback);

    @Headers("Accept: application/json")
    @GET("/movie/{id}/videos")
    void getMovieTrailers(@Path("id") int id, @Query("api_key") String key, Callback<TrailerContainer> callback);

    @Headers("Accept: application/json")
    @GET("/movie/{id}/reviews")
    void getMovieReviews(@Path("id") int id, @Query("api_key") String key, Callback<List<String>> callback);


}
