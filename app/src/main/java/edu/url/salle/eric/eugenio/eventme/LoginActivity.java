package edu.url.salle.eric.eugenio.eventme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import edu.url.salle.eric.eugenio.eventme.api.ApiAdapter;
import edu.url.salle.eric.eugenio.eventme.api.ApiService;
import edu.url.salle.eric.eugenio.eventme.model.Token;
import edu.url.salle.eric.eugenio.eventme.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    // Widgets
    EditText mEmail, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.login_email);
        mPassword = findViewById(R.id.login_password);
    }

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, LoginActivity.class);
    }

    public void onClickLogin(View view) {
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, R.string.toast_form_empty, Toast.LENGTH_SHORT).show();
        }
        else {

            ApiAdapter.getInstance().authenticateUser(email, password).enqueue(new Callback<Token>() {
                @Override
                public void onResponse(Call<Token> call, Response<Token> response) {
                    if (response.isSuccessful()) {
                        getUser(response.body(), email);
                    }
                    else {
                        Toast.makeText(LoginActivity.this, R.string.api_login_failed, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Token> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, R.string.api_connection_failed, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void getUser(Token token, String email) {
        ApiAdapter.getInstance().searchUser("Bearer " + token.getToken(), email).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    User user = response.body().get(0);
                    user.setToken(token);

                    User.getUser().updateUser(user);

                    Intent intent = MainActivity.newIntent(LoginActivity.this);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }

    public void onClickGoToSignup(View view) {
        Intent intent = SignupActivity.newIntent(this);
        startActivity(intent);
    }

}