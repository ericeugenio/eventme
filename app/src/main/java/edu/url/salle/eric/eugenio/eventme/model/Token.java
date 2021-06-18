package edu.url.salle.eric.eugenio.eventme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Token {

    @Expose
    @SerializedName("accessToken")
    private String token;

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void extendToken() {
        if (token != null && !token.startsWith("Bearer")) {
            token = "Bearer " + token;
        }
    }
}
