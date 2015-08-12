package com.neilhussain.popularmovies;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

        if(v == null) {

            v = inflater.inflate(R.layout.fragment_main_grid, container, false);

            //Do stuff with the fragment!

            makeComponents();
            getIntents();
            fillInGrid();

            return v;

        }else{

            System.out.println("Movie grid fragment isn't null, just returning the saved view.");
            return v;

        }
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

                openFragment(new MovieDetailFragment(), movies.get(position));

            }
        });


    }

    private void openFragment(final Fragment fragment, Movie movie)   {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        Bundle args = new Bundle();
        args.putParcelable("movie", movie);
        fragment.setArguments(args);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        transaction.replace(R.id.main_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

}
