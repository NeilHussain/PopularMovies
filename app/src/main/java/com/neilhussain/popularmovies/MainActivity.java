package com.neilhussain.popularmovies;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    String APIKey;
    //######################################################

    String lastSort;

    ArrayList<Movie> movies;
    private final String BASE_URL = "http://api.themoviedb.org/3";

    MoviesAPI api;
    GridView posterGrid;
    PosterAdapter adapter;

    String[] sortTypes = {"Most popular", "Highest rated"};

    boolean refresh = false;

    Fragment mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //Create the api after getting the key from resources.
        APIKey = getResources().getString(R.string.API_KEY);
        api = new MoviesAPI(APIKey, BASE_URL);

        Log.d("API Key", APIKey);

        if(savedInstanceState != null){
            mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
        }


        if (mContent == null) {

            Log.d("onCreate", "Getting movies again.");
            getMovies("popularity.desc");

        }else{

            //Just let it do its thing.


        }

    }

    public void onResume() {
        super.onResume();
        //refresh list if sort pref changed
        String currentSortPref = getSortPref();

        if (!currentSortPref.equals(lastSort)) {
            lastSort = currentSortPref;
        }
    }

    public void createMainFragment(ArrayList<Movie> movies) {

        //clear the backstack when switching sort types
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {

            getSupportFragmentManager().popBackStack();

        }

        if (mContent == null) {

            mContent = new MainGridFragment();

            //Pass the movies list in to the fragment so it has something to display.
            Bundle args = new Bundle();
            args.putParcelableArrayList("movies", (ArrayList<Movie>) movies);

            mContent.setArguments(args);
        }

            // Add the fragment to the 'main_fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction().add(R.id.main_fragment_container, mContent).addToBackStack(null).commit();


    }


    //Returns the correct settings preference.
    private String getSortPref() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        return prefs.getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_default));
    }



    public void refreshGrid() {

        System.out.println("Refresh grid");
        adapter.notifyDataSetChanged();
        //posterGrid.setAdapter(adapter);
        posterGrid.invalidateViews();

    }

    public void getMovies(String sort) {

        api.getMovies(sort, new MoviesCallback() {
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
    public void onSaveInstanceState(Bundle outState) {

        // Call to the superclass to save the full state of the activity
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("movies", movies);
        getSupportFragmentManager().putFragment(outState, "mContent", mContent);


    }

    @Override
    public void onRestoreInstanceState(Bundle inState) {

        // Call to the superclass to restore the full state of the activity
        super.onRestoreInstanceState(inState);
        movies = inState.getParcelableArrayList("movies");

    }

}
