package com.example.kinopoiskmovies;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie" +
            "?token=ETJBF54-9DAMMTH-QFV40W3-8ZEZHPW" +
            "&field=rating.kp" +
            "&search=5-10" +
            "&sortField=votes.kp" +
            "&sortType=-1" +
            "&limit=100")
    Single<MovieResponse> loadMovies(@Query("page") int page);
}
