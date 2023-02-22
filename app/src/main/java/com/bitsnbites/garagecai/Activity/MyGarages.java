package com.bitsnbites.garagecai.Activity;

import static com.bitsnbites.garagecai.Activity.MainActivity.uid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.bitsnbites.garagecai.R;
import com.bitsnbites.garagecai.adapter.GarageAdapter;
import com.bitsnbites.garagecai.model.Garage;
import com.directions.route.Routing;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyGarages extends AppCompatActivity {
    List<Garage> garageList = new ArrayList<>();
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_garages);

        RecyclerView userView;

        userView = findViewById(R.id.rcv);

        FirebaseFirestore.getInstance().collection("Garage").whereEqualTo("ownerId", uid).addSnapshotListener((value, error) -> {

            if (!value.isEmpty()) {
                List<DocumentSnapshot> list = value.getDocuments();
                try {
                    for (DocumentSnapshot d : list) {
                        Log.d("userGarage", "onCreate: " + d);
                        Garage g = d.toObject(Garage.class);
                        garageList.add(g);
                    }
                    GarageAdapter garageAdapter = new GarageAdapter(getApplicationContext(), garageList);
                    userView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    userView.setAdapter(garageAdapter);
                    garageAdapter.notifyDataSetChanged();
                } catch (Exception g) {
                    Log.d("frgre", "onCreate: + ni" + g);
                }

            }
        });

    }

}