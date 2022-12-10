package com.example.kinopoiskmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FavouriteMoviesActivity extends AppCompatActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, FavouriteMoviesActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_movies);

        RecyclerView rvFavouriteMovies = findViewById(R.id.rvFavouriteMovies);
        MoviesAdapter moviesAdapter = new MoviesAdapter();
        rvFavouriteMovies.setLayoutManager(new GridLayoutManager(this, 2));
        rvFavouriteMovies.setAdapter(moviesAdapter);

        moviesAdapter.setOnMovieClickListener(movie -> startActivity(
                MovieDetailActivity.newIntent(FavouriteMoviesActivity.this, movie))
        );

        FavouriteMoviesViewModel viewModel = new ViewModelProvider(this).get(
                FavouriteMoviesViewModel.class
        );
        viewModel.getMovies().observe(this, moviesAdapter::setMovies);
    }
}