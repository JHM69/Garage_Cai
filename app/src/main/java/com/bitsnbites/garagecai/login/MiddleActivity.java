package com.bitsnbites.garagecai.login;

import static com.bitsnbites.garagecai.Activity.MainActivity.uid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bitsnbites.garagecai.R;
import com.bitsnbites.garagecai.model.User;
import com.bitsnbites.garagecai.service.OfflineUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Objects;

public class MiddleActivity extends AppCompatActivity {
    String uuid;
    String type;
    User user = new User();

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        TextView name = findViewById(R.id.name);

        TextView number = findViewById(R.id.number);

        TextView email = findViewById(R.id.email);
        type =  getIntent().getStringExtra("type");

        try{
            Log.d("userId", "onCreate: "+  type);
            number.setText((Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber()));

            User user = (User) getIntent().getSerializableExtra("user");

            name.setText((user.getName()));
            email.setText((user.getEmail()));

        }catch (Exception ignored){

        }


        Button next = findViewById(R.id.button);

        next.setOnClickListener(v -> {
            user.setType(type);
            user.setUuid(uid);
            user.setName( name.getText().toString());
            user.setEmail(email.getText().toString());
            user.setEmailVerified(false);
            user.setBirthCertificateVerified(false);
            user.setTimestamp(System.currentTimeMillis());


            FirebaseFirestore.getInstance().collection("users").document(uid).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(MiddleActivity.this, "Added", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

            finish();
        });





    }
}