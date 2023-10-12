package org.example;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RickAndMortyApiService {
    @GET("episode")
    Call<Result> getEpisodes();
}
