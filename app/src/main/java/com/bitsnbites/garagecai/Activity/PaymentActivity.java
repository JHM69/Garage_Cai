package com.bitsnbites.garagecai.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bitsnbites.garagecai.R;
import com.bitsnbites.garagecai.model.Book;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        TextView amount = findViewById(R.id.textView14);

        Book b  = (Book) getIntent().getSerializableExtra("book");

        amount.setText("Amount : "+ b.getBookingAmount() + " Taka");

        Button submit = findViewById(R.id.button6);

        submit.setOnClickListener(v -> FirebaseFirestore.getInstance().collection("Garage").document(b.getGarageId()).update("step_counter" , FieldValue.increment(-b.getNumber_of_vehicle())).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                FirebaseFirestore.getInstance().collection("Book").document(b.getId()).update("status", "rented").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        finish();;
                    }
                });

            }
        }));


    }
}