package com.bitsnbites.garagecai.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;

import com.bitsnbites.garagecai.R;
import com.bitsnbites.garagecai.model.Garage;

import java.util.Calendar;

public class BookingActivity extends AppCompatActivity {
    Garage garage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        garage = (Garage) getIntent().getSerializableExtra("garage");


        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = new DatePickerDialog(
                BookingActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
// If you're calling this from a support Fragment
        dpd.show(getFragmentManager(), "Datepickerdialog");
// If you're calling this from an AppCompatActivity
// dpd.show(getSupportFragmentManager(), "Datepickerdialog");








    }


}