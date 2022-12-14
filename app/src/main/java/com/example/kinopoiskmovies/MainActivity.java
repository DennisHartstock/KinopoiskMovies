package com.example.kinopoiskmovies;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;

    private RecyclerView rvMovies;
    private ProgressBar pbLoading;
    private MoviesAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        moviesAdapter = new MoviesAdapter();
        rvMovies.setAdapter(moviesAdapter);
        rvMovies.setLayoutManager(new GridLayoutManager(this, 2));
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading) {
                pbLoading.setVisibility(View.VISIBLE);
            } else {
                pbLoading.setVisibility(View.GONE);
            }
        });
        viewModel.getMovies().observe(this, movies -> moviesAdapter.setMovies(movies));
        moviesAdapter.setOnReachEndListener(() -> viewModel.loadMovies());
        moviesAdapter.setOnMovieClickListener(movie -> startActivity(
                MovieDetailActivity.newIntent(MainActivity.this, movie))
        );
    }

    private void initViews() {
        rvMovies = findViewById(R.id.rvMovies);
        pbLoading = findViewById(R.id.pbLoading);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemFavourite) {
            startActivity(FavouriteMoviesActivity.newIntent(this));
        }
        return super.onOptionsItemSelected(item);
    }
}