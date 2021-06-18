package edu.url.salle.eric.eugenio.eventme.api;

import java.util.List;

import edu.url.salle.eric.eugenio.eventme.model.Attendance;
import edu.url.salle.eric.eugenio.eventme.model.Event;
import edu.url.salle.eric.eugenio.eventme.model.Friend;
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

    @GET("users/search/")
    Call<List<User>> searchUser(@Header("Authorization") String token, @Query("s") String name);

    @GET("users/{ID}")
    Call<List<User>> getUser(@Header("Authorization") String token, @Path("ID") long userId);

    // ----------------------------------------------
    // EVENTS
    // ----------------------------------------------

    // --CRUD----------------------------------------

    @GET("events")
    Call<List<Event>> getAllEvents(@Query("t") String type);

    @POST("events")
    Call<Event> insertEvent(@Header("Authorization") String token, @Body Event event);

    @DELETE("events/{ID}")
    Call<ResponseBody> removeEvent(@Header("Authorization") String token, @Path("ID") long eventId);

    // --Events joined by user-----------------------

    @GET("users/{ID}/assistances/")
    Call<List<Event>> getJoinedEvents(@Header("Authorization") String token, @Path("ID") long userId);

    @GET("users/{ID}/assistances/future")
    Call<List<Event>> getJoinedFutureEvents(@Header("Authorization") String token, @Path("ID") long userId);

    @GET("users/{ID}/assistances/finished")
    Call<List<Event>> getJoinedPastEvents(@Header("Authorization") String token, @Path("ID") long userId);

    // --Events created by user----------------------

    @GET("users/{ID}/events")
    Call<List<Event>> getCreatedEvents(@Header("Authorization") String token, @Path("ID") long userId);

    @GET("users/{ID}/events/future")
    Call<List<Event>> getCreatedFutureEvents(@Header("Authorization") String token, @Path("ID") long userId);

    @GET("users/{ID}/events/finished")
    Call<List<Event>> getCreatedPastEvents(@Header("Authorization") String token, @Path("ID") long userId);

    @GET("users/{ID}/events/current")
    Call<List<Event>> getCreatedCurrentEvents(@Header("Authorization") String token, @Path("ID") long userId);


    // --Events participants-------------------------

    @FormUrlEncoded
    @POST("events/{ID}/assistances")
    Call<Attendance> confirmAttendance(@Header("Authorization") String token,
                                       @Path("ID") long eventId,
                                       @Field("puntuation") int rating,
                                       @Field("comentary") String comment);

    @GET("events/{ID}/assistances")
    Call<List<Attendance>> getEventAttendances(@Header("Authorization") String token, @Path("ID") long eventId);

    @DELETE("events/{ID}/assistances")
    Call<ResponseBody> cancelAttendance(@Header("Authorization") String token);

    // ----------------------------------------------
    // FRIENDS
    // ----------------------------------------------

    @GET("messages/users")
    Call<List<Friend>> getFriends(@Header("Authorization") String token);

    // ----------------------------------------------
    // MESSAGES
    // ----------------------------------------------

}
