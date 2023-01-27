package com.bitsnbites.garagecai.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.bitsnbites.garagecai.R;
import com.bitsnbites.garagecai.model.Book;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        TextView amount = findViewById(R.id.textView14);

        Book b  = (Book) getIntent().getSerializableExtra("book");

        amount.setText("Amount : "+ b.getBookingAmount() + " Taka");

    }
}