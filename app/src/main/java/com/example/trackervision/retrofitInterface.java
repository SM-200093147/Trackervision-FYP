package com.example.trackervision;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class retrofitInterface {
    private static final String baseURL="https://api.themoviedb.org";
    private static Retrofit retrofit = null;

    public static tmdbInterface getRetrofitClient(){
        if (retrofit==null){
            retrofit = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(tmdbInterface.class);
    }
}
