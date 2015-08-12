package com.neilhussain.popularmovies;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailFragment extends Fragment {

    View v;

    Movie movie;
    TextView title;
    ImageView poster;
    TextView summary;
    TextView year;
    TextView rating;
    TextView length;

    private final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185/";
    private final String BASE_URL = "http://api.themoviedb.org/3";

    String APIKey;
    MoviesAPI api;

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        if(v == null) {
            v = inflater.inflate(R.layout.fragment_movie_detail, container, false);

            APIKey = getString(R.string.API_KEY);
            api = new MoviesAPI(APIKey,BASE_URL);

            getIntents();
            makeComponents();
            getMovieDetails();

        }else{

            return v;

        }

        return v;
    }

    private void getMovieDetails() {

        api.getMovieTrailers(movie.getId(), new TrailersCallback() {
            @Override
            public void ready(TrailerContainer trailers) {
                System.out.println(trailers.toString());
            }
        });


    }


    private void makeComponents() {

        title = (TextView) v.findViewById(R.id.movieTitle);
        title.setText(movie.getName());

        poster = (ImageView) v.findViewById(R.id.posterView);

        Picasso.with(getActivity()).load(POSTER_BASE_URL + movie.getPosterPath()).into(poster);

        summary = (TextView) v.findViewById(R.id.summaryTV);
        summary.setText(movie.getSummary());

        year = (TextView) v.findViewById(R.id.year);

        if (movie.getReleaseDate() != null && movie.getReleaseDate().length() >= 4)
            year.setText(movie.getReleaseDate().substring(0, 4));

        rating = (TextView) v.findViewById(R.id.rating);
        rating.setText(movie.getVoteAverage() + "/10.0");

    }


    private void getIntents() {

        //Grab the movie object from the passed in bundle.
        Bundle bundle = getArguments();
        movie = (Movie) bundle.getParcelable("movie");


    }


}
