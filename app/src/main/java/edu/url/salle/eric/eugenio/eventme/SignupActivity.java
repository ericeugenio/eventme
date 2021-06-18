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
import edu.url.salle.eric.eugenio.eventme.model.Token;
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
                mPassword.setHintTextColor(getColor(R.color.red_300));
            }
            // Only alphanumeric characters
            else if (!password.matches("^[a-zA-Z0-9]*$")) {
                mPassword.setText("");
                mPassword.setHint(R.string.signup_alphanumeric_password);
                mPassword.setHintTextColor(getColor(R.color.red_300));
            }
            // Matching passwords
            else if (!password.equals(repeatedPassword)) {
                mRepeatedPassword.setText("");
                mRepeatedPassword.setHint(R.string.signup_repeated_password);
                mRepeatedPassword.setHintTextColor(getColor(R.color.red_300));
            }
            else {
                User user = new User(name, lastName, email, password, "null");
                ApiAdapter.getInstance().registerUser(user).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            authenticateUser(user);
                        }
                        else {
                            Toast.makeText(SignupActivity.this, R.string.api_signup_failed, Toast.LENGTH_SHORT).show();
                            mEmail.setTextColor(getColor(R.color.red_300));
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

    private void authenticateUser(User user) {
        ApiAdapter.getInstance().authenticateUser(user.getEmail(), user.getPassword()).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()) {
                    user.setToken(response.body());
                    User.getUser().updateUser(user);

                    Intent intent = MainActivity.newIntent(SignupActivity.this);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(SignupActivity.this, R.string.api_login_failed, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(SignupActivity.this, R.string.api_connection_failed, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClickGoToLogin(View view) {
        finish();
    }
}