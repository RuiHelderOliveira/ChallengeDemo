package me.demo.services;

import me.demo.BuildConfig;
import me.demo.classes.Data;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by Rui Oliveira on 10/01/18.
 */

public interface ApiServices {

    String user_agent = "User-Agent: Android Demo" + BuildConfig.VERSION_NAME;

    @Headers({
            user_agent
    })
    @GET("?")
    Call<Data> getUsers(@Query("page") int page, @Query("results") int results, @Query("seed") String seed);
}
