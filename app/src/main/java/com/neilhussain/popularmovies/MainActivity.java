package com.neilhussain.popularmovies;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.util.List;


public class MainActivity extends AppCompatActivity {


    //######################################################
    String APIKey = "Api key goes here!";
    //######################################################


    List<Movie> movies;
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


        getMovies("popularity.desc");
        //getPopularMovies();

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
                movies = moviesList;
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



            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
