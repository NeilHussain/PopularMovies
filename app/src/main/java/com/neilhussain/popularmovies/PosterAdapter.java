package com.neilhussain.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by NeilHussain on 7/17/15.
 */
public class PosterAdapter extends ArrayAdapter {

    List<Movie> items;
    private final String BASE_URL = "http://image.tmdb.org/t/p/w185/";

    public PosterAdapter(Context context, int textViewResourceId, List objects) {
        super(context, textViewResourceId, objects);

        this.items = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        Context context = parent.getContext();
        View row = convertView;
        ViewHolder holder;
        final int _position = position;

        final Movie movie = items.get(position);

        if(convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.movie_grid_item, null);
            holder = new ViewHolder();

            holder.poster = (ImageView)row.findViewById(R.id.posterIMG);

            row.setTag(holder);

        }else{

            holder = (ViewHolder)row.getTag();
        }

        holder.poster.setMinimumHeight(750);


        if(movie != null) {

            String avatarURL = BASE_URL + movie.getPosterPath();

            Log.d("Image URL", avatarURL);

            if(avatarURL != null && !avatarURL.equals("")) {
                Picasso.with(context).load(avatarURL).into(holder.poster);
            }else{
                // View circleView = (View)row.findViewById(R.id.c);
                // circleView.setVisibility(View.GONE);
            }

        }
        return row;
    }


    class ViewHolder{

        ImageView poster;

    }

}
