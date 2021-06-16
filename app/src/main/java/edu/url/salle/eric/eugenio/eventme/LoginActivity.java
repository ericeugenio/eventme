package edu.url.salle.eric.eugenio.eventme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

        if (!email.isEmpty() && !password.isEmpty()) {

            // TODO: validate info in API

            Intent intent = MainActivity.newIntent(this);
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(this, R.string.toast_login_fill, Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickGoToSignup(View view) {
        Intent intent = SignupActivity.newIntent(this);
        startActivity(intent);
    }

}