package com.bitsnbites.garagecai.Activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bitsnbites.garagecai.R;
import com.bitsnbites.garagecai.model.Book;
import com.bitsnbites.garagecai.model.Garage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Map;
import java.util.UUID;

import dev.dayaonweb.incrementdecrementbutton.IncrementDecrementButton;


public class BookingActivity extends AppCompatActivity {
    Garage garage;

    String date_time = "";
    int mYear;
    int mMonth;
    int mDay;

    int mHour;
    int mMinute;


    String date_timeEnd = "";
    int mYearEnd;
    int mMonthEnd;
    int mDayEnd;

    int mHourEnd;
    int mMinuteEnd;
    String[] types = {"car", "bike", "bicycle"};
    Book book = new Book();
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        garage = (Garage) getIntent().getSerializableExtra("garage");

        @SuppressLint("CutPasteId") AutoCompleteTextView av = findViewById(R.id.vehicle_type);
        TextView t = findViewById(R.id.textView13);
        TextView et = findViewById(R.id.textView2);
        et.setText( "Slots : " + garage.getNumberOfSlots());
        String s = "Parking Name    : " + garage.getName() + "\n\n";
        s+=        "Available : " + garage.isAvailability() + "\n";
        s+=        "Bike Slots       : " + garage.getBike() + "\n";
        s+=        "Bicycle Slots   : " + garage.getBicycle() + "\n";
        s+=        "Hourly Rate     : " + garage.getHourly_rate() + "\n";

        t.setText(s);

        Button startTime = findViewById(R.id.startBtnTime);
        Button endTime = findViewById(R.id.EndBtnTime);


        final AutoCompleteTextView textView;
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getApplicationContext(), android.R.layout.simple_dropdown_item_1line,
                types);

        textView = findViewById(R.id.vehicle_type);
        textView.setAdapter(arrayAdapter);
        textView.setOnClickListener(arg0 -> textView.showDropDown());


        startTime.setOnClickListener(v -> datePicker(startTime));

        endTime.setOnClickListener(v -> datePickerEnd(endTime));

        Button bookData = findViewById(R.id.button7);


        bookData.setOnClickListener(v -> {



            ProgressDialog pg = new ProgressDialog(this);
            pg.setCancelable(false);
            pg.setTitle("Booking ....");
            pg.show();

                String vehicleType = av.getText().toString();
                book.setVehicleType(vehicleType);
                book.setNumber_of_vehicle(1);
                book.setHourlyRate(garage.getHourly_rate());

                book.setId(UUID.randomUUID().toString());
                book.setUserId(garage.getOwnerId());
                book.setUserId(garage.getId());

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirmation");
            builder.setMessage("Price amount is "+ book.getBookingAmount() + " Taka");
            builder.setPositiveButton("OK", (dialog, which) -> FirebaseFirestore.getInstance().collection("Book").document(book.getId())
                    .set(garage).addOnCompleteListener(task ->{

                        pg.dismiss();
                        Intent intent = new Intent(BookingActivity.this, PaymentActivity.class);
                        intent.putExtra("book" , book);
                        startActivity(intent);
                    } ));
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    pg.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();

        });




    }

    private void datePicker(Button b){

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    date_time = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                    tiemPicker(b);
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void tiemPicker(Button b){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        @SuppressLint("SetTextI18n") TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute) -> {
                    mHour = hourOfDay;
                    mMinute = minute;

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(mYear, mMonth, mDay, mHour, mMinute);
                    book.setStartTimestamp(calendar.getTimeInMillis()); // will use dynamic value later

                    b.setText(date_time+" "+hourOfDay + ":" + minute);
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


    private void datePickerEnd(Button b){

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYearEnd = c.get(Calendar.YEAR);
        mMonthEnd = c.get(Calendar.MONTH);
        mDayEnd = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    date_timeEnd = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                    tiemPickerEnd(b);
                }, mYearEnd, mMonthEnd, mDayEnd);
        datePickerDialog.show();
    }

    private void tiemPickerEnd(Button b){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHourEnd = c.get(Calendar.HOUR_OF_DAY);
        mMinuteEnd = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        @SuppressLint("SetTextI18n") TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute) -> {
                    mHourEnd = hourOfDay;
                    mMinuteEnd = minute;
                    b.setText(date_timeEnd+" "+hourOfDay + ":" + minute);
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(mYearEnd, mMonthEnd, mDayEnd, mHourEnd, mMinuteEnd);
                    book.setEndTimestamp(calendar.getTimeInMillis()); // will use dynamic value later

                }, mHourEnd, mMinuteEnd, false);
        timePickerDialog.show();
    }


}