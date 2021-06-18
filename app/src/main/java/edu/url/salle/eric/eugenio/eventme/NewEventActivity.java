package edu.url.salle.eric.eugenio.eventme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import edu.url.salle.eric.eugenio.eventme.api.ApiAdapter;
import edu.url.salle.eric.eugenio.eventme.model.Event;
import edu.url.salle.eric.eugenio.eventme.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewEventActivity extends AppCompatActivity {

    // Toolbar
    private Toolbar mToolbar;

    // Widgets
    private EditText mEventName, mEventLocation, mEventDescription, mEventParticipants;
    private TextView mEventStartDay, mEventStartTime, mEventEndDay, mEventEndTime;

    // Filter chips
    private Button mSelectedChip;

    private static final String DATE_FORMAT = "EEE, d MMM yyyy 'at' h:mm a";
    private static final String DAY_FORMAT = "EEE, d MMM yyyy";
    private static final String TIME_FORMAT = "h:mm a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        mToolbar = findViewById(R.id.newEvent_toolbar);
        setSupportActionBar(mToolbar);

        configureWidgets();
    }

    private void configureWidgets() {
        mEventName = findViewById(R.id.newEvent_name);
        mEventLocation = findViewById(R.id.newEvent_location);
        mEventDescription = findViewById(R.id.newEvent_description);
        mEventParticipants = findViewById(R.id.newEvent_participants);

        mEventStartDay = findViewById(R.id.newEvent_startDay);
        mEventStartTime = findViewById(R.id.newEvent_startTime);
        mEventEndDay = findViewById(R.id.newEvent_endDay);
        mEventEndTime = findViewById(R.id.newEvent_endTime);

        mSelectedChip = findViewById(R.id.chip_music);
        mSelectedChip.setSelected(true);
    }

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, NewEventActivity.class);
    }

    // ----------------------------------------------
    // TOOLBAR
    // ----------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_event, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_cancelNewEvent) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    // ----------------------------------------------
    // PHOTOS
    // ----------------------------------------------

    public void onCLickAddPhoto(View view) {
        // TODO: add a photo url
        Toast.makeText(this, R.string.toast_to_be_implemented, Toast.LENGTH_SHORT).show();
    }

    // ----------------------------------------------
    // DATES
    // ----------------------------------------------

    // --------------- DAY ---------------

    public void onClickAddDate(View view) {
        DatePickerDialog datePicker;
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        if (view.getId() == R.id.newEvent_startDay) {
            datePicker = new DatePickerDialog(this, R.style.datePicker_style,
                    this::onStartDateSet, year, month, dayOfMonth);

            // Limit date
            datePicker.getDatePicker().setMinDate(c.getTimeInMillis());
            if (!mEventEndDay.getText().toString().isEmpty()) {
                try {
                    Date date = new SimpleDateFormat(DAY_FORMAT, Locale.ENGLISH).parse(mEventEndDay.getText().toString());
                    datePicker.getDatePicker().setMaxDate(date.getTime());
                } catch (ParseException ignored) {}
            }

        }
        else {
            datePicker = new DatePickerDialog(this, R.style.datePicker_style,
                    this::onEndDateSet, year, month, dayOfMonth);

            // Limit date
            if (!mEventStartDay.getText().toString().isEmpty()) {
                try {
                    Date date = new SimpleDateFormat(DAY_FORMAT, Locale.ENGLISH).parse(mEventStartDay.getText().toString());
                    datePicker.getDatePicker().setMinDate(date.getTime());
                } catch (ParseException ignored) {}
            }
        }

        datePicker.show();
    }

    private void onStartDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        SimpleDateFormat dateFormat = new SimpleDateFormat(DAY_FORMAT, Locale.ENGLISH);
        String date = dateFormat.format(c.getTime());
        mEventStartDay.setText(date);
    }

    private void onEndDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        SimpleDateFormat dateFormat = new SimpleDateFormat(DAY_FORMAT, Locale.ENGLISH);
        String date = dateFormat.format(c.getTime());
        mEventEndDay.setText(date);
    }

    // --------------- TIME ---------------

    public void onClickAddTime(View view) {
        TimePickerDialog timePickerDialog;
        Calendar c = Calendar.getInstance();
        int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        if (view.getId() == R.id.newEvent_startTime) {
            timePickerDialog = new TimePickerDialog(this, R.style.datePicker_style,
                    this::onStartTimeSet, hourOfDay, minute, false);
        }
        else {
            timePickerDialog = new TimePickerDialog(this, R.style.datePicker_style,
                    this::onEndTimeSet, hourOfDay, minute, false);
        }

        timePickerDialog.show();
    }

    private void onStartTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);

        SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT, Locale.ENGLISH);
        String time = dateFormat.format(c.getTime());
        mEventStartTime.setText(time);
    }

    private void onEndTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);

        SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT, Locale.ENGLISH);
        String time = dateFormat.format(c.getTime());
        mEventEndTime.setText(time);
    }

    // ----------------------------------------------
    // CHIPS
    // ----------------------------------------------

    public void onCLickSelectEventType(View view) {
        // Update filter only if chip selected is different than the current
        if (view.getId() != mSelectedChip.getId()) {
            // Restore previous chip
            mSelectedChip.setSelected(false);

            // Update current chip
            mSelectedChip = view.findViewById(view.getId());
            mSelectedChip.setSelected(true);
        }
    }

    // ----------------------------------------------
    // EVENT
    // ----------------------------------------------

    public void onClickCreateEvent(View view) {
        String image = "noImage";
        String name = mEventName.getText().toString();
        String type = mSelectedChip.getText().toString();
        String description = mEventDescription.getText().toString();
        String location = mEventLocation.getText().toString();
        String participants = mEventParticipants.getText().toString();
        String startDay = mEventStartDay.getText().toString();
        String startTime = mEventStartTime.getText().toString();
        String endDay = mEventEndDay.getText().toString();
        String endTime = mEventEndTime.getText().toString();

        int maxParticipants;
        Date startDate = null;
        Date endDate = null;

        if ( name.isEmpty() || description.isEmpty() || location.isEmpty() || type.isEmpty()
            || participants.isEmpty() || startDay.isEmpty() || startTime.isEmpty()
            || endDay.isEmpty() || endTime.isEmpty()) {

            Toast.makeText(this, R.string.toast_form_empty, Toast.LENGTH_SHORT).show();
        }
        else {
            maxParticipants = Integer.parseInt(participants);

            try {
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
                startDate = sdf.parse(startDay + " at " + startTime);
                endDate = sdf.parse(endDay + " at " + endTime);
            } catch (ParseException ignored) {}

            String token = User.getUser().getToken();
            Event event = new Event(name, type, description, maxParticipants, location, image, startDate, endDate);
            ApiAdapter.getInstance().insertEvent(token, event).enqueue(new Callback<Event>() {
                @Override
                public void onResponse(Call<Event> call, Response<Event> response) {
                    if (response.isSuccessful()) {
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Event> call, Throwable t) {

                }
            });
        }
    }
}