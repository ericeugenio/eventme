package edu.url.salle.eric.eugenio.eventme.api;

import java.util.List;

import edu.url.salle.eric.eugenio.eventme.model.Event;
import edu.url.salle.eric.eugenio.eventme.model.Token;
import edu.url.salle.eric.eugenio.eventme.model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    // ----------------------------------------------
    // USER
    // ----------------------------------------------

    @FormUrlEncoded
    @POST("users/login")
    Call<Token> authenticateUser(@Field("email") String email, @Field("password") String password);

    @POST("users")
    Call<User> registerUser(@Body User user);

    @GET("users/{ID}")
    Call<User> getUser(@Header("Authorization") String token, @Path("ID") long userId);

    // ----------------------------------------------
    // EVENTS
    // ----------------------------------------------

    @GET("events")
    Call<List<Event>> getAllEvents(@Query("t") String type);

    @POST("events")
    Call<Event> insertEvent(@Header("Authorization") String token, @Body Event event);

    @DELETE("events/{ID}")
    Call<ResponseBody> removeEvent(@Header("Authorization") String token, @Path("ID") long eventId);

    // ----------------------------------------------
    // FRIENDS
    // ----------------------------------------------

    // ----------------------------------------------
    // MESSAGES
    // ----------------------------------------------

}
