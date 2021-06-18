package edu.url.salle.eric.eugenio.eventme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import edu.url.salle.eric.eugenio.eventme.api.ApiAdapter;
import edu.url.salle.eric.eugenio.eventme.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    // Toolbar
    private Toolbar mToolbar;

    private EditText mName, mLastname, mBio, mNewPassword;
    private TextView mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        configureView();

        mToolbar = findViewById(R.id.edit_profile_toolbar);
        setSupportActionBar(mToolbar);
    }

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, EditProfileActivity.class);
    }

    private void configureView() {
        mName = findViewById(R.id.edit_profile_name);
        mLastname = findViewById(R.id.edit_profile_lastNname);
        mEmail = findViewById(R.id.edit_profile_email);
        mBio = findViewById(R.id.edit_profile_bio);
        mNewPassword = findViewById(R.id.edit_profile_new_pass);

        User user = User.getUser();
        mName.setText(user.getName());
        mLastname.setText(user.getLastName());
        mEmail.setText(user.getEmail());
        mBio.setText(user.getBio());
    }

    public void onClickUpdatePhoto(View view) {
        // TODO: add a photo url
        Toast.makeText(this, R.string.toast_to_be_implemented, Toast.LENGTH_SHORT).show();
    }

    public void onClickSaveProfile(View view) {
        User user = User.getUser();

        // Name
        if (!mName.getText().toString().equalsIgnoreCase(user.getName())) {
            user.setName(mName.getText().toString());
        }

        // Last Name
        if (!mLastname.getText().toString().equalsIgnoreCase(user.getLastName())) {
            user.setLastName(mLastname.getText().toString());
        }

        // Bio
        user.setBio(mBio.getText().toString());

        // password
        String password = mNewPassword.getText().toString();
        if (!password.isEmpty()) {
            // Password longer than 7 characters
            if (password.length() < 8) {
                mNewPassword.setText("");
                mNewPassword.setHint(R.string.signup_password_length);
                mNewPassword.setHintTextColor(getColor(R.color.red_300));
            }
            // Only alphanumeric characters
            else if (!password.matches("^[a-zA-Z0-9]*$")) {
                mNewPassword.setText("");
                mNewPassword.setHint(R.string.signup_alphanumeric_password);
                mNewPassword.setHintTextColor(getColor(R.color.red_300));
            }
            else {
                user.setPassword(password);
            }
        }

        User.getUser().updateUser(user);

        String token = user.getToken();
        ApiAdapter.getInstance().updateUser(token, user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    finish();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    // ----------------------------------------------
    // TOOLBAR
    // ----------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_discardProfile) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}