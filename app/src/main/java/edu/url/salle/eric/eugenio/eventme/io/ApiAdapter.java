package edu.url.salle.eric.eugenio.eventme.io;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiAdapter {

    private static ApiService apiService;

    public static ApiService getInstance () {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel( HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        String baseUrl = "http://puigmal.salle.url.edu/api/";

        if (apiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
            apiService = retrofit.create(ApiService.class);
        }

        return apiService;
    }

}
