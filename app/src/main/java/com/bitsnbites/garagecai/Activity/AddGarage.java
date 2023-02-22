package com.bitsnbites.garagecai.Activity;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.io.File;
import java.util.Objects;
import java.util.Random;

import dev.dayaonweb.incrementdecrementbutton.IncrementDecrementButton;

public class AddGarage extends AppCompatActivity {
    Garage garage = new Garage();
    Uri filesUri1 = Uri.fromFile(new File("//android_asset/temp.pdf"));
    Uri filesUri2 = Uri.fromFile(new File("//android_asset/temp.pdf"));
    Uri filesUri3 = Uri.fromFile(new File("//android_asset/temp.pdf"));
    Uri filesUri4 = Uri.fromFile(new File("//android_asset/temp.pdf"));

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
        EditText hourlyRatee = findViewById(R.id.hourly_rate_amount);

        Places.initialize(getApplicationContext(), "AIzaSyCA8w6LtmgAe_wCZTrOhC67NvvAcri0ttI");

        IncrementDecrementButton vehicleNumber = findViewById(R.id.number_of_vehicle);

        Button submit = findViewById(R.id.button3);
        RecyclerView stations_list = findViewById(R.id.stations_list);

        submit.setOnClickListener(v -> {
            ProgressDialog pg = new ProgressDialog(this);
            pg.setCancelable(false);
            pg.setTitle("Adding ....");
            pg.show();

            garage.setAvailability(true);
            garage.setId(getRandomString());
            garage.setVerified(false);
            garage.setRating(3.5);
            garage.setOwnerId(FirebaseAuth.getInstance().getUid());
            garage.setSeq_number(seq.getText().toString().trim());
            try {
                Log.d("absss", "onCreate: " + hourlyRatee.getText().toString());
                String s = hourlyRatee.getText().toString();
                garage.setHourly_rate(Integer.parseInt(s));

            } catch (Exception e) {
                garage.setHourly_rate(100);
            }

            try {
                garage.setNumberOfSlots(Integer.parseInt(String.valueOf(vehicleNumber.getSceneString())));
            } catch (Exception e) {
                garage.setNumberOfSlots(5);
            }


            if (garage.getAddress() == null || garage.getNumberOfSlots() == 0) {
                Toast.makeText(AddGarage.this, "Error Garage Data", Toast.LENGTH_SHORT).show();
                pg.dismiss();
                return;
            }

            if (filesUri1 == null || filesUri2 == null || filesUri3 == null || filesUri4 == null) {
                Toast.makeText(AddGarage.this, "Files are empty", Toast.LENGTH_SHORT).show();
                pg.dismiss();
                return;


            }

            Log.d(TAG, "onCreate: "+ "skdjswwdew" + garage.getName());

            FirebaseFirestore.getInstance().collection("Garage").document(garage.getId())
                    .set(garage).addOnCompleteListener(task -> {
                        pg.dismiss();
                        Toast.makeText(AddGarage.this, "Added", Toast.LENGTH_SHORT).show();
                    });

//
//                StorageReference ref = FirebaseStorage.getInstance().getReference();
//                ref.child("images/" + garage.getId() + "/" + filesUri1.getLastPathSegment())
//                        .putFile(filesUri1).addOnFailureListener(exception -> {
//                            ref.child("images/" + garage.getId() + "/" + filesUri2.getLastPathSegment())
//                                    .putFile(filesUri2).addOnFailureListener(exception2 -> {
//
//                                    }).addOnSuccessListener(taskSnapshot -> {
//                                        ref.child("images/" + garage.getId() + "/" + filesUri3.getLastPathSegment())
//                                                .putFile(filesUri3).addOnFailureListener(exception3 -> {
//
//                                                }).addOnSuccessListener(taskSnapshot3 -> {
//                                                    ref.child("images/" + garage.getId() + "/" + filesUri4.getLastPathSegment())
//                                                            .putFile(filesUri4).addOnFailureListener(exception4 -> {
//
//                                                            }).addOnSuccessListener(taskSnapshot4 -> {
//
//
//                                                            });
//                                                });
//                                    });
//                        }).addOnSuccessListener(taskSnapshot -> {
//
//                        });


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
                        hideKeyboard(AddGarage.this);
                        try {
                            try {
                                Log.d("fedf", "afterTextChanged:" +
                                        new Gson().toJson(place));
                            } catch (Exception n) {
                                Log.d("ldlos", "afterTextChanged: " + n.getLocalizedMessage());
                            }
                            garage.setName(place.getName());
                            garage.setAddress(place.getAddress());
                            nameEt.setText(garage.getAddress());

                            garage.setLatitude((Objects.requireNonNull(place.getLatLng())).latitude);
                            garage.setLongitude(place.getLatLng().longitude);

                            mAutoCompleteAdapter.clear();

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