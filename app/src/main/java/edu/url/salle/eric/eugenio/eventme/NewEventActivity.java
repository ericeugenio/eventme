package edu.url.salle.eric.eugenio.eventme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NewEventActivity extends AppCompatActivity {

    private EditText mEventName, mEventLocation, mEventDescription, mEventParticipants;
    private TextView mEventStartDay, mEventStartTime, mEventEndDay, mEventEndTime;

    // Filter chips
    private Button mSelectedChip;

    private static final String DATE_FORMAT = "yyMMddHHmm";
    private static final String DAY_FORMAT = "EEE, d MMM yyyy";
    private static final String TIME_FORMAT = "h:mm a";
    private String startDay, startTime;
    private String endDay, endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

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

    // ----------------------------------------------
    // PHOTOS
    // ----------------------------------------------

    public void onCLickAddPhoto(View view) {
        // TODO: add a photo from either gallery or camera
        Toast.makeText(view.getContext(), "Add an image", Toast.LENGTH_SHORT).show();
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
        }
        else {
            datePicker = new DatePickerDialog(this, R.style.datePicker_style,
                    this::onEndDateSet, year, month, dayOfMonth);
        }

        datePicker.show();
    }

    private void onStartDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        startDay = year + "" + month + "" + dayOfMonth;

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        SimpleDateFormat dateFormat = new SimpleDateFormat(DAY_FORMAT, Locale.ENGLISH);
        String date = dateFormat.format(c.getTime());
        mEventStartDay.setText(date);
    }

    private void onEndDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        endDay = year + "" + month + "" + dayOfMonth;

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
        startTime = hourOfDay + "" + minute;

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);

        SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT, Locale.ENGLISH);
        String time = dateFormat.format(c.getTime());
        mEventStartTime.setText(time);
    }

    private void onEndTimeSet(TimePicker view, int hourOfDay, int minute) {
        endTime = hourOfDay + "" + minute;

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
        // TODO: create event
    }
}