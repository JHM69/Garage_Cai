package com.bitsnbites.garagecai.Activity;

import static com.bitsnbites.garagecai.Activity.MainActivity.uid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.Toast;

import com.bitsnbites.garagecai.R;
import com.bitsnbites.garagecai.adapter.GarageAdapter;
import com.bitsnbites.garagecai.model.Garage;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MyGarages extends AppCompatActivity {
    List<Garage> garageList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_garages);


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        RecyclerView userView;


        userView = findViewById(R.id.rcv);


        db.collection("Medicines").orderBy("name").whereEqualTo("ownerId", uid).get().addOnSuccessListener(documentSnapshots -> {
            if (documentSnapshots.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Medicine: Empty", Toast.LENGTH_SHORT).show();
            } else {
                garageList = documentSnapshots.toObjects(Garage.class);
                GarageAdapter addressAdapter = new GarageAdapter(MyGarages.this, garageList);
                userView.setAdapter(addressAdapter);
                addressAdapter.notifyDataSetChanged();
            }
        });
    }
}