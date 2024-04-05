package com.example.trackervision;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class retrofitInterface {
    private static final String baseURL="https://api.themoviedb.org";
    public static String language="en-us";
    public static String apiKey="a07d8418569b36aba9e3ae4a58107505";
    public String topRatedCategory="top_rated";
    public String popularCategory="popular";
    private static Retrofit retrofit = null;

    public static tmdbInterface getRetrofitClient(){
        if (retrofit==null){
            retrofit = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(tmdbInterface.class);
    }
}
