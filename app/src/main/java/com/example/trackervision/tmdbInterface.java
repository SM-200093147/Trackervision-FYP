package com.example.trackervision;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface tmdbInterface {
    @GET("3/tv/{category}")
    Call<TVShowResults> getTopRatedShows(
            @Path("category") String category,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("3/search/tv")
    Call<TVShowResults> searchTVShows(
            @Query("query") String showName,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("3/trending/tv/{time_window}")
    Call<TVShowResults> showTrending(
            @Path("time_window") String time_window,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );
}
