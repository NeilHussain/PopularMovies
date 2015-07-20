package com.neilhussain.popularmovies;

import java.util.List;

/**
 * Created by NeilHussain on 7/19/15.
 */
public interface MoviesCallback {

    void ready(List<Movie> movies);

}
