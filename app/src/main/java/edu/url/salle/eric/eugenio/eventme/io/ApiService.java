package edu.url.salle.eric.eugenio.eventme.io;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("users/login")
    Call<String> userAuthentication(
            @Field("email") String email,
            @Field("password") String password
    );

}
