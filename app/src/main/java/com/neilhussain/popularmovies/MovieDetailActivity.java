package com.neilhussain.popularmovies;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

//Not used anymore, moved to a fragment.
public class MovieDetailActivity extends ActionBarActivity {

    Movie movie;
    TextView title;
    ImageView poster;
    TextView summary;
    TextView year;
    TextView rating;
    TextView length;

    private final String BASE_URL = "http://image.tmdb.org/t/p/w185/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getIntents();
        makeComponents();
    }

    private void makeComponents() {
        title = (TextView) findViewById(R.id.movieTitle);
        title.setText(movie.getName());

        poster = (ImageView) findViewById(R.id.posterView);

        Picasso.with(getBaseContext()).load(BASE_URL + movie.getPosterPath()).into(poster);

        summary = (TextView) findViewById(R.id.summaryTV);
        summary.setText(movie.getSummary());

        year = (TextView) findViewById(R.id.year);

        if (movie.getReleaseDate() != null && movie.getReleaseDate().length() >= 4)
            year.setText(movie.getReleaseDate().substring(0, 4));

        rating = (TextView) findViewById(R.id.rating);
        rating.setText(movie.getVoteAverage() + "/10.0");


    }

    private void getIntents() {

        Bundle data = getIntent().getExtras();
        movie = (Movie) data.getParcelable("movie");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
