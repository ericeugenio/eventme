package edu.url.salle.eric.eugenio.eventme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SignupActivity extends AppCompatActivity {

    // Widgets
    EditText mUsername, mEmail, mPassword, mRepeatedPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mUsername = findViewById(R.id.signup_username);
        mEmail = findViewById(R.id.signup_email);
        mPassword = findViewById(R.id.signup_password);
        mRepeatedPassword = findViewById(R.id.signup_repeat_password);
    }

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, SignupActivity.class);
    }

    public void onClickSignup(View view) {
        // TODO: validate info in API
    }

    public void onClickGoToLogin(View view) {
        finish();
    }
}