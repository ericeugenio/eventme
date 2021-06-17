package edu.url.salle.eric.eugenio.eventme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import edu.url.salle.eric.eugenio.eventme.api.ApiAdapter;
import edu.url.salle.eric.eugenio.eventme.api.ApiService;
import edu.url.salle.eric.eugenio.eventme.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    // Widgets
    EditText mName, mLastName, mEmail, mPassword, mRepeatedPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mName = findViewById(R.id.signup_name);
        mLastName = findViewById(R.id.signup_last_name);
        mEmail = findViewById(R.id.signup_email);
        mPassword = findViewById(R.id.signup_password);
        mRepeatedPassword = findViewById(R.id.signup_repeat_password);
    }

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, SignupActivity.class);
    }

    public void onClickSignup(View view) {
        String name = mName.getText().toString();
        String lastName = mLastName.getText().toString();
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String repeatedPassword = mRepeatedPassword.getText().toString();

        // No empty fields
        if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || repeatedPassword.isEmpty()) {
            Toast.makeText(this, R.string.toast_form_empty, Toast.LENGTH_SHORT).show();
        }
        else {
            // Password longer than 7 characters
            if (password.length() < 8) {
                mPassword.setText("");
                mPassword.setHint(R.string.signup_password_length);
                mPassword.setHintTextColor(getResources().getColor(R.color.red_300));
            }
            // Only alphanumeric characters
            else if (!isAlphaNumeric(password)) {
                mPassword.setText("");
                mPassword.setHint(R.string.signup_alphanumeric_password);
                mPassword.setHintTextColor(getResources().getColor(R.color.red_300));
            }
            // Matching passwords
            else if (!password.equals(repeatedPassword)) {
                mRepeatedPassword.setText("");
                mRepeatedPassword.setHint(R.string.signup_repeated_password);
                mRepeatedPassword.setHintTextColor(getResources().getColor(R.color.red_300));
            }
            else {
                User user = new User(name, lastName, email, password, null);
                ApiAdapter.getInstance().registerUser(user).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(SignupActivity.this, R.string.api_connection_failed, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    public void onClickGoToLogin(View view) {
        finish();
    }

    // ----------------------------------------------
    // VALIDATIONS
    // ----------------------------------------------

    public boolean isAlphaNumeric(String s) {
        return s.matches("^[a-zA-Z0-9]*$");
    }
}