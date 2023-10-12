package org.example;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.json.JsonMapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Retrofit client = new Retrofit
                .Builder()
                .baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(JacksonConverterFactory.create(new JsonMapper()))
                .build();

        RickAndMortyApiService service = client.create(RickAndMortyApiService.class);

        service.getEpisodes().enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    Result result = response.body();
                    if (result != null) {
                        Episode episodeWithMaxCharacters = result.getEpisodes()
                                .stream()
                                .max(Comparator.comparingInt(e -> e.getCharacterUrls().size()))
                                .orElse(null);

                        if (episodeWithMaxCharacters != null) {
                            System.out.println("Эпизод с максимальным количеством персонажей: " + episodeWithMaxCharacters.getName());
                        } else {
                            System.out.println("Нет данных о эпизодах.");
                        }
                    } else {
                        System.out.println("Отсутствуют данные о сериале.");
                    }
                } else {
                    System.out.println("Ошибка при получении данных: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                System.out.println("Ошибка при выполнении запроса: " + t.getMessage());
            }
        });
    }


}