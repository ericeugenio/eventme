package edu.url.salle.eric.eugenio.eventme.api;

import java.util.List;

import edu.url.salle.eric.eugenio.eventme.model.Event;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("users/login")
    Call<String> userAuthentication(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("events")
    Call<List<Event>> getAllEvents();
}
