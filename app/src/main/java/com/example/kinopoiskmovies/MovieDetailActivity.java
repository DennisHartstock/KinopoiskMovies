package com.example.kinopoiskmovies;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String EXTRA_MOVIE = "movie";

    private MovieDetailViewModel viewModel;

    private ImageView ivPoster;
    private ImageView ivStar;
    private TextView tvTitle;
    private TextView tvYear;
    private TextView tvDescription;
    private RecyclerView rvTrailers;
    private RecyclerView rvReviews;

    private TrailersAdapter trailersAdapter;
    private ReviewsAdapter reviewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        viewModel = new ViewModelProvider(this).get(MovieDetailViewModel.class);
        initViews();

        trailersAdapter = new TrailersAdapter();
        rvTrailers.setAdapter(trailersAdapter);

        reviewsAdapter = new ReviewsAdapter();
        rvReviews.setAdapter(reviewsAdapter);

        Movie movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);

        Glide.with(this)
                .load(movie.getPoster().getUrl())
                .into(ivPoster);

        tvTitle.setText(movie.getName());

        tvTitle.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.kinopoisk.gg/film/" + movie.getId() + "/"));
            startActivity(intent);
        });

        tvYear.setText(String.valueOf(movie.getYear()));
        tvDescription.setText(movie.getDescription());

        viewModel.getTrailers().observe(this,
                trailers -> trailersAdapter.setTrailers(trailers));
        viewModel.loadTrailers(movie.getId());

        trailersAdapter.setOnTrailerClickListener(trailer -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(trailer.getUrl()));
            startActivity(intent);
        });

        viewModel.getReviews().observe(this, reviews -> reviewsAdapter.setReviews(reviews));
        viewModel.loadReviews(movie.getId());

        Drawable starOff = ContextCompat.getDrawable(MovieDetailActivity.this,
                android.R.drawable.star_big_off);
        Drawable starOn = ContextCompat.getDrawable(MovieDetailActivity.this,
                android.R.drawable.star_big_on);

        viewModel.getFavouriteMovie(movie.getId()).observe(this, favouriteMovie -> {
            if (favouriteMovie == null) {
                ivStar.setImageDrawable(starOff);
                ivStar.setOnClickListener(view -> viewModel.insertMovie(movie));
            } else {
                ivStar.setImageDrawable(starOn);
                ivStar.setOnClickListener(view -> viewModel.removeMovie(movie.getId()));
            }
        });
    }

    private void initViews() {
        ivPoster = findViewById(R.id.ivPoster);
        ivStar = findViewById(R.id.ivStar);
        tvTitle = findViewById(R.id.tvTitle);
        tvYear = findViewById(R.id.tvYear);
        tvDescription = findViewById(R.id.tvDescription);
        rvTrailers = findViewById(R.id.rvTrailers);
        rvReviews = findViewById(R.id.rvReviews);
    }

    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        return intent;
    }
}