package com.bitsnbites.garagecai.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bitsnbites.garagecai.R;
import com.bitsnbites.garagecai.model.User;
import com.bitsnbites.garagecai.service.OfflineUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;

public class MiddleActivity extends AppCompatActivity {
    String uuid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        TextView name = findViewById(R.id.name);

        TextView number = findViewById(R.id.number);

        TextView email = findViewById(R.id.email);

        try{

            User user = (User) getIntent().getSerializableExtra("user");
            uuid =  user.getUuid();

            name.setText((user.getName()));
            number.setText((user.getNumber()));
            email.setText((user.getEmail()));

        }catch (Exception e){
             uuid = getIntent().getStringExtra("uuid");
        }



        Button next = findViewById(R.id.button);

        next.setOnClickListener(v -> {
            DocumentReference userRef =  FirebaseFirestore.getInstance().collection("Users").document(uuid);
            userRef.update("name", name.getText().toString());
            userRef.update("email", email.getText().toString().trim());
            userRef.update("createdAt", System.currentTimeMillis());

            User user = new User();

            user.setUuid(uuid);
            user.setName( name.getText().toString());
            user.setEmail(email.getText().toString().trim());

            startActivity( new Intent().putExtra("uuid", user.getUuid()));
            finish();
        });





    }
}