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

public class EditProfileActivity extends AppCompatActivity {

    // Toolbar
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        mToolbar = findViewById(R.id.edit_profile_toolbar);
        setSupportActionBar(mToolbar);
    }

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, EditProfileActivity.class);
    }

    public void onClickUpdatePhoto(View view) {
    }

    public void onClickValidatePassword(View view) {
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
        else if (item.getItemId() == R.id.action_saveProfile) {
            // TODO: save new changes
        }

        return super.onOptionsItemSelected(item);
    }

}