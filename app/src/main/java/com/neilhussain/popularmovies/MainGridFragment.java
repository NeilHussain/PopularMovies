package com.neilhussain.popularmovies;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainGridFragment extends Fragment {

    View v;
    ArrayList<Movie> movies;
    PosterAdapter adapter;
    GridView posterGrid;

    public MainGridFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_main_grid, container, false);

        //Do stuff with the fragment!

        makeComponents();
        getIntents();

        fillInGrid();

        return v;
    }

    private void makeComponents() {

        posterGrid = (GridView) v.findViewById(R.id.moviesGridView);
    }

    private void getIntents() {


        Bundle bundle = getArguments();

        //add first item to the list, the item that trade was clicked on
        movies = (ArrayList<Movie>) bundle.get("movies");


    }

    private void fillInGrid() {
        adapter = new PosterAdapter(getActivity(), R.layout.movie_grid_item, movies);
        posterGrid.setAdapter(adapter);

        posterGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {

                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);

                intent.putExtra("movie", movies.get(position));

                startActivity(intent);

            }
        });


    }

}
