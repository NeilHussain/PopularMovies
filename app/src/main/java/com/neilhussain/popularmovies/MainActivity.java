package com.neilhussain.popularmovies;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    //######################################################
    String APIKey = "ace498aa4c71763241446ba13fb4933e";
    //######################################################

    String lastSort;

    ArrayList<Movie> movies;
    private final String BASE_URL = "http://api.themoviedb.org/3";

    MoviesAPI api = new MoviesAPI(APIKey, BASE_URL);
    GridView posterGrid;
    PosterAdapter adapter;

    String[] sortTypes = {"Most popular", "Highest rated"};

    boolean refresh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {

            movies = savedInstanceState.getParcelableArrayList("movies");
            //fillInGrid();

            if (findViewById(R.id.main_fragment_container) == null) {
                createMainFragment(movies);
            }


        } else {

            if (findViewById(R.id.main_fragment_container) == null) {

                // However, if we're being restored from a previous state,
                // then we don't need to do anything and should return or else
                // we could end up with overlapping fragments.
                if (savedInstanceState != null) {
                    return;
                }else{
                    getMovies("popularity.desc");

                }
            }

        }

        //getPopularMovies();

    }

    public void onResume() {
        super.onResume();
        //refresh list if sort pref changed
        String currentSortPref = getSortPref();
        if (!currentSortPref.equals(lastSort) || movies == null) {
            lastSort = currentSortPref;
            getMovies(lastSort);
        }
    }

    public void createMainFragment(ArrayList<Movie> movies) {


        //clear the backstack when switching sort types
        if(getSupportFragmentManager().getBackStackEntryCount() > 0){

            getSupportFragmentManager().popBackStack();

        }

        // Create a new Fragment to be placed in the activity layout
        MainGridFragment mainGrid = new MainGridFragment();

     //Pass the movies list in to the fragment so it has something to display.
        Bundle args = new Bundle();
        args.putParcelableArrayList("movies", (ArrayList<Movie>)movies);

        mainGrid.setArguments(args);

        // Add the fragment to the 'main_fragment_container' FrameLayout
        getSupportFragmentManager().beginTransaction().add(R.id.main_fragment_container, mainGrid).addToBackStack(null).commit();
    }


    //Returns the correct settings preference.
    private String getSortPref() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        return prefs.getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_default));
    }


    private void fillInGrid() {
        adapter = new PosterAdapter(getBaseContext(), R.layout.movie_grid_item, movies);
        posterGrid.setAdapter(adapter);

        posterGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {

                Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);

                intent.putExtra("movie", movies.get(position));

                startActivity(intent);

            }
        });


    }

    public void refreshGrid() {

        System.out.println("Refresh grid");
        adapter.notifyDataSetChanged();
        //posterGrid.setAdapter(adapter);
        posterGrid.invalidateViews();

    }

    public void getMovies(String sort) {

        api.getMovie(sort, new MoviesCallback() {
            @Override
            public void ready(List<Movie> moviesList) {
                movies = new ArrayList<>(moviesList);
                createMainFragment(movies);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putParcelableArrayList("movies", (ArrayList<Movie>)movies);

        // Call to the superclass to save the full state of the activity
        super.onSaveInstanceState(savedInstanceState);
    }

}
