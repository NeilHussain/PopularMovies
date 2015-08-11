package com.neilhussain.popularmovies;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailFragment extends Fragment {

    View v;
    Movie movie;

    public MovieDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_movie_detail, container, false);




        return v;
    }


    private void makeComponents() {

        //posterGrid = (GridView) v.findViewById(R.id.moviesGridView);
    }

    private void getIntents() {


        Bundle bundle = getArguments();

        //add first item to the list, the item that trade was clicked on
        //movies = (ArrayList<Movie>) bundle.get("movies");

    }



}
