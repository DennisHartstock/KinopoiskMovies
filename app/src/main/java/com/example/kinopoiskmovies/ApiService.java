package com.example.kinopoiskmovies;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie" +
            "?token=ETJBF54-9DAMMTH-QFV40W3-8ZEZHPW" +
            "&field=rating.kp" +
            "&search=5-10" +
            "&field=year" +
            "&search=1950-1980" +
            "&sortField=votes.kp" +
            "&sortType=-1" +
            "&limit=100")
    Single<MovieResponse> loadMovies(@Query("page") int page);

    @GET("movie?token=ETJBF54-9DAMMTH-QFV40W3-8ZEZHPW&field=id")
    Single<TrailerResponse> loadTrailers(@Query("search") int id);

    @GET("review?token=ETJBF54-9DAMMTH-QFV40W3-8ZEZHPW&field=movieId")
    Single<ReviewResponse> loadReviews(@Query("search") int id);
}
