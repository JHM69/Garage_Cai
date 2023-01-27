package com.bitsnbites.garagecai.Activity;

import static com.bitsnbites.garagecai.Activity.ShowMap.getSaltString;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bitsnbites.garagecai.R;
import com.bitsnbites.garagecai.model.Book;
import com.bitsnbites.garagecai.model.Garage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Map;

import dev.dayaonweb.incrementdecrementbutton.IncrementDecrementButton;
import me.adawoud.bottomsheettimepicker.BottomSheetTimeRangePicker;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        garage = (Garage) getIntent().getSerializableExtra("garage");

        AutoCompleteTextView av = findViewById(R.id.vehicle_type);
        TextView t = findViewById(R.id.textView2);
        t.setText(String.valueOf(garage.getNumberOfSlots()));

        Button startTime = findViewById(R.id.startBtnTime);
        Button endTime = findViewById(R.id.EndBtnTime);







        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(startTime);
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerEnd(endTime);
            }
        });

        Button book = findViewById(R.id.startBtnTime);

        IncrementDecrementButton incrementDecrementButton = findViewById(R.id.number_of_vehicle);


        book.setOnClickListener(v -> {

            ProgressDialog pg = new ProgressDialog(this);
            pg.setCancelable(false);
            pg.setTitle("Booking ....");
            pg.show();

            Book b = new Book();
            String vehicleType = av.getText().toString();
            int occoupaid = Integer.parseInt(String.valueOf( incrementDecrementButton.getSceneString()));
            b.setNumber_of_vehicle(occoupaid);
            b.setHourlyRate(garage.getHourly_rate());
            b.setStartTimestamp(0); // will use dynamic value later
            b.setEndTimestamp(720000); // will use dynamic value later

            b.setId(getSaltString());
            b.setUserId(garage.getOwnerId());
            b.setUserId(garage.getId());

            FirebaseFirestore.getInstance().collection("Book").document(String.valueOf(book.getId())).
                    set(book).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                pg.dismiss();
                                Intent intent = new Intent(BookingActivity.this, PaymentActivity.class);
                                intent.putExtra("book" , b);
                                startActivity(intent);
                            }else{
                                pg.dismiss();

                            }

                        }
                    });

        });



    }

    private void datePicker(Button b){

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {

                        date_time = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        //*************Call Time Picker Here ********************
                        tiemPicker(b);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void tiemPicker(Button b){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        mHour = hourOfDay;
                        mMinute = minute;
                        b.setText(date_time+" "+hourOfDay + ":" + minute);
                    }
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
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {

                        date_timeEnd = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        //*************Call Time Picker Here ********************
                        tiemPicker(b);
                    }
                }, mYearEnd, mMonthEnd, mDayEnd);
        datePickerDialog.show();
    }

    private void tiemPickerEnd(Button b){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHourEnd = c.get(Calendar.HOUR_OF_DAY);
        mMinuteEnd = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        mHourEnd = hourOfDay;
                        mMinuteEnd = minute;
                        b.setText(date_timeEnd+" "+hourOfDay + ":" + minute);
                    }
                }, mHourEnd, mMinuteEnd, false);
        timePickerDialog.show();
    }

}