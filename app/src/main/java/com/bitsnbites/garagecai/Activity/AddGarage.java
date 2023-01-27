package com.bitsnbites.garagecai.Activity;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bitsnbites.garagecai.R;
import com.bitsnbites.garagecai.adapter.PlacesAutoCompleteAdapter;
import com.bitsnbites.garagecai.model.Garage;
import com.google.android.libraries.places.api.Places;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.io.File;
import java.util.Objects;
import java.util.Random;

import dev.dayaonweb.incrementdecrementbutton.IncrementDecrementButton;

public class AddGarage extends AppCompatActivity {
    Garage garage = new Garage();
    Uri filesUri1, filesUri2, filesUri3, filesUri4 = Uri.fromFile(new File("//android_asset/temp.pdf"));;

    TextView file1, file2, file3, file4;
    Button selectFile1, selectFile2, selectFile3, selectFile4;

    int code1, code2, code3, code4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_garage);


        selectFile1 = findViewById(R.id.nid_viewB);
        selectFile2 = findViewById(R.id.birth_certificate_viewB);
        selectFile3 = findViewById(R.id.button5);
        selectFile4 = findViewById(R.id.poperty_documentB);


        selectFile1.setOnClickListener(view -> startPickFiles(code1));
        selectFile2.setOnClickListener(view -> startPickFiles(code2));
        selectFile3.setOnClickListener(view -> startPickFiles(code3));
        selectFile4.setOnClickListener(view -> startPickFiles(code4));


        EditText nameEt = findViewById(R.id.station_name);
        EditText seq = findViewById(R.id.seq_number);

        Places.initialize(getApplicationContext(), "AIzaSyAgddM3SuCy4Qiz0DjM6lE13C5P2rbY3RI");

        IncrementDecrementButton vehicleNumber = findViewById(R.id.number_of_vehicle);
        EditText hourlyRate = findViewById(R.id.hourly_rate);

        Button submit = findViewById(R.id.button3);
        RecyclerView stations_list = findViewById(R.id.stations_list);

        submit.setOnClickListener(v -> {
            garage.setAvailability(true);
            garage.setId(getRandomString());
            garage.setSeq_number(seq.getText().toString().trim());
            garage.setHourly_rate(Integer.parseInt(hourlyRate.getText().toString()));
            garage.setSpacePerCar(Integer.parseInt(vehicleNumber.getSceneString()));

            if (garage.getAddress() == null || garage.getSpacePerCar() == 0) {
                Toast.makeText(AddGarage.this, "Error Garage Data", Toast.LENGTH_SHORT).show();
                return;
            }

            if(filesUri1==null || filesUri2==null ||filesUri3==null || filesUri4==null){
                Toast.makeText(AddGarage.this, "Files are empty", Toast.LENGTH_SHORT).show();
                return;
            }

            StorageReference ref = FirebaseStorage.getInstance().getReference();
            ref.child("images/" + garage.getId() + "/" + filesUri1.getLastPathSegment())
                    .putFile(filesUri1).addOnFailureListener(exception -> {
                        ref.child("images/" + garage.getId() + "/" + filesUri2.getLastPathSegment())
                                .putFile(filesUri2).addOnFailureListener(exception2 -> {

                                }).addOnSuccessListener(taskSnapshot -> {
                                    ref.child("images/" + garage.getId() + "/" + filesUri3.getLastPathSegment())
                                            .putFile(filesUri3).addOnFailureListener(exception3 -> {

                                            }).addOnSuccessListener(taskSnapshot3 -> {
                                                ref.child("images/" + garage.getId() + "/" + filesUri4.getLastPathSegment())
                                                        .putFile(filesUri4).addOnFailureListener(exception4 -> {

                                                        }).addOnSuccessListener(taskSnapshot4 -> {
                                                            FirebaseFirestore.getInstance().collection("Garage").document(garage.getId())
                                                                    .set(garage).addOnCompleteListener(task -> Toast.makeText(AddGarage.this, "Added", Toast.LENGTH_SHORT).show());

                                                        });
                                            });
                                });
                    }).addOnSuccessListener(taskSnapshot -> {

                    });


        });


        PlacesAutoCompleteAdapter mAutoCompleteAdapter = new PlacesAutoCompleteAdapter(AddGarage.this);

        stations_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        stations_list.setAdapter(mAutoCompleteAdapter);
        mAutoCompleteAdapter.notifyDataSetChanged();

        nameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    mAutoCompleteAdapter.getFilter().filter(s.toString());
                    mAutoCompleteAdapter.notifyDataSetChanged();
                    mAutoCompleteAdapter.setClickListener(place -> {
                        hideKeyboard((Activity) getApplicationContext());
                        try {
                            try {
                                Log.d("fedf", "afterTextChanged:" +
                                        new Gson().toJson(place));
                            } catch (Exception n) {
                                Log.d("ldlos", "afterTextChanged: " + n.getLocalizedMessage());
                            }
                            garage.setAddress(place.getAddress());
                            nameEt.setText(garage.getAddress());
                            garage.setLatitude((long) (Objects.requireNonNull(place.getLatLng())).latitude);
                            garage.setLongitude((long) place.getLatLng().longitude);

                        } catch (Exception y) {
                            Log.d(TAG, "afterTextChanged: " + y.getLocalizedMessage());
                        }
                    });
                }
            }

        });

    }


    private void startPickFiles(int code) {
        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
        chooseFile.setType("file/*");
        startActivityForResult(Intent.createChooser(chooseFile, "Choose a file"), code);
    }

    protected String getRandomString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }

    public static void hideKeyboard(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == code1) {
                if (data != null) {

                    filesUri1 = data.getData();
                    selectFile1.setText("Change");
                    file1.setText(new File(data.getData().getPath()).getName());
                }
            } else if (requestCode == code2) {
                if (data != null) {

                    filesUri2 = data.getData();
                    selectFile2.setText("Change");
                    file2.setText(new File(data.getData().getPath()).getName());
                }
            } else if (requestCode == code3) {
                if (data != null) {

                    filesUri3 = data.getData();
                    selectFile3.setText("Change");
                    file3.setText(new File(data.getData().getPath()).getName());
                }
            } else if (requestCode == code4) {
                if (data != null) {

                    filesUri4 = data.getData();
                    selectFile4.setText("Change");
                    file4.setText(new File(data.getData().getPath()).getName());
                }
            }

        }
    }


}