package com.bitsnbites.garagecai.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bitsnbites.garagecai.R;
import com.bitsnbites.garagecai.login.MiddleActivity;
import com.bitsnbites.garagecai.model.User;
import com.bitsnbites.garagecai.service.OfflineUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public static String uid;
    public static User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uid = FirebaseAuth.getInstance().getUid();

        DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document(uid);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    user = document.toObject(User.class);
                } else {
                    Log.d("userr", "No such document");
                }
            } else {
                Log.d("userr", "get failed with ", task.getException());
            }
        });




        findViewById(R.id.leaser).setOnClickListener(v -> {
            Intent intent;
            try{
                if(Objects.equals(user.getType(), "none") ||  !Objects.equals(user.getType(), "leaser")){
                    intent = new Intent(getApplicationContext(), MiddleActivity.class).putExtra("type", "leaser");
                }else{
                    intent = new Intent(getApplicationContext(), AddGarage.class);
                }

            }catch (Exception s){
                intent = new Intent(getApplicationContext(), MiddleActivity.class).putExtra("type", "leaser");
            }
            startActivity(intent);
        });

        findViewById(R.id.renter).setOnClickListener(v -> {
            Intent intent;
            try{
                if(Objects.equals(user.getType(), "none") ||  !Objects.equals(user.getType(), "renter")){
                    intent = new Intent(getApplicationContext(), MiddleActivity.class).putExtra("type", "renter");
                }else{
                    intent = new Intent(getApplicationContext(), ShowMap.class);
                }

            }catch (Exception s){
                intent = new Intent(getApplicationContext(), MiddleActivity.class).putExtra("type", "renter");

            }
            startActivity(intent);
        });


        findViewById(R.id.allGarage).setOnClickListener(v -> {
            Intent intent;
            try{
                if(Objects.equals(user.getType(), "none") ||  !Objects.equals(user.getType(), "leaser")){
                    intent = new Intent(getApplicationContext(), MiddleActivity.class).putExtra("type", "renter");
                }else{
                    intent = new Intent(getApplicationContext(), MyGarages.class);
                }

            }catch (Exception s){
                intent = new Intent(getApplicationContext(), MiddleActivity.class).putExtra("type", "renter");

            }
            startActivity(intent);
        });
    }

    void updateProfile(){
        Intent intent = new Intent(getApplicationContext(), MiddleActivity.class);
        startActivity(intent);
    }
}