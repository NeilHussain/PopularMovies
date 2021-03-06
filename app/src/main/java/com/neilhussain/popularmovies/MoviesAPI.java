package com.neilhussain.popularmovies;

import android.util.Log;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by NeilHussain on 7/17/15.
 */
public class MoviesAPI {

    private String apiKey;
    private String BASE_URL;
    //@Inject
    RestAdapter adapter;

    //@Inject
    APIService api;

    public MoviesAPI(String apiKey, String BASE_URL) {
        this.BASE_URL = BASE_URL;
        this.apiKey = apiKey;

        adapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String msg) {
                        Log.d("FULL REQUEST", msg);
                    }
                })
                .build();

        api = adapter.create(APIService.class);

    }

    public List<Movie> getPopularMovies() {

        List<Movie> movies;
        try {
            movies = api.getMovies("popularity.desc", apiKey).results;
        } catch (RetrofitError e) {
            Log.d("RETROFIT ERROR", "Popular movies error");
            return null;
        }

        return movies;

    }

    public List<Movie> getHighestRatedMovies() {

        List<Movie> movies;

        try {
            movies = api.getMovies("vote_average.desc", apiKey).results;

        } catch (RetrofitError e) {

            Log.d("RETROFIT ERROR", "Popular movies error");
            return null;
        }

        return movies;
    }

    public void getMovies(String sort, final MoviesCallback mcb) {

        api.getMoviesCallback(sort, apiKey, new Callback<Page>() {

            @Override
            public void success(Page page, Response response) {
                //code which should update user field
                mcb.ready(page.results);
            }

            @Override
            public void failure(RetrofitError error) {

            }

        });


    }

    //movie/{id}/videos endpoint.
    //To fetch reviews you will want to make a request to the /movie/{id}/reviews

    public void getMovieTrailers(int id, final TrailersCallback slc) {

        api.getMovieTrailers(id, apiKey, new Callback<TrailerContainer>() {

            @Override
            public void success(TrailerContainer trailers, Response response) {
                //code which should update user field
                slc.ready(trailers);
            }

            @Override
            public void failure(RetrofitError error) {

            }

        });
    }


    public void getMovieReviews(int id, final StringListCallback slc) {

        api.getMovieReviews(id, apiKey, new Callback<List<String>>() {

            @Override
            public void success(List<String> trailers, Response response) {
                //code which should update user field
                slc.ready(trailers);
            }

            @Override
            public void failure(RetrofitError error) {

            }

        });
    }

}
