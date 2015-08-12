package com.neilhussain.popularmovies;

import java.util.List;

/**
 * Created by NeilHussain on 8/11/15.
 */
public class TrailerContainer {

    private List<Trailer> results;
    private int id;

    public List<Trailer> getResults() {
        return results;
    }

    public void setResults(List<Trailer> results) {
        this.results = results;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
