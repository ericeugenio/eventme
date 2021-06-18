package edu.url.salle.eric.eugenio.eventme.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiAdapter {

    private static final String BASE_URL = "http://puigmal.salle.url.edu/api/";

    private static ApiService apiService;

    public static ApiService getInstance() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        if (apiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(new OkHttpClient.Builder().addInterceptor(logging).build())
                    .build();

            apiService = retrofit.create(ApiService.class);
        }

        return apiService;
    }

}
