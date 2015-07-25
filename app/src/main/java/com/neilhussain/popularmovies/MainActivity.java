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
    String APIKey = "";
    //######################################################

    String lastSort;

    ArrayList<Movie> movies;
    private final String BASE_URL = "http://api.themoviedb.org/3";

    MoviesAPI api = new MoviesAPI(APIKey, BASE_URL);
    GridView posterGrid;
    PosterAdapter adapter;

    String[] sortTypes = {"Most popular","Highest rated"};

    boolean refresh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        posterGrid = (GridView)findViewById(R.id.moviesGridView);

        if(savedInstanceState != null){
            movies = savedInstanceState.getParcelableArrayList("movies");
            fillInGrid();
        }else{
            getMovies("popularity.desc");
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

    public void refreshGrid(){

        System.out.println("Refresh grid");
        adapter.notifyDataSetChanged();
        //posterGrid.setAdapter(adapter);
        posterGrid.invalidateViews();

    }

    public void getMovies(String sort){

        api.getMovie(sort, new MoviesCallback() {
            @Override
            public void ready(List<Movie> moviesList) {
                movies = new ArrayList<>(moviesList);
                fillInGrid();
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

            //Not used at the recommendation of the Udacity coach
            /*
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Sort Movies");
            builder.setItems(sortTypes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    // Do something with the selection

                    switch (item) {

                        case 0:
                            getMovies("popularity.desc");
                            break;
                        case 1:
                            getMovies("vote_average.desc");
                            break;
                    }


                }
            });
            AlertDialog alert = builder.create();
            alert.show();
            */


            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putParcelableArrayList("movies", movies);

        // Call to the superclass to save the full state of the activity
        super.onSaveInstanceState(savedInstanceState);
    }

}
