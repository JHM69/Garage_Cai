package com.bitsnbites.garagecai.Activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Location;
import com.google.android.gms.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CalendarContract;
import android.provider.Settings;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bitsnbites.garagecai.R;
import com.bitsnbites.garagecai.adapter.GarageAdapter;
import com.bitsnbites.garagecai.model.Garage;
import com.directions.route.AbstractRouting;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShowMap extends AppCompatActivity implements RoutingListener, OnMapReadyCallback , ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, GoogleApiClient.ConnectionCallbacks{
    GoogleMap map;
    List<Garage> garageList = new ArrayList<>();
    List<LatLng> allAddresss = new ArrayList<>();
    String userId;
    Marker busLocation;
    //  MarkerOptions options;
    private List<Polyline> polyLines = new ArrayList<>();

    PendingResult<LocationSettingsResult> result;
    final static int REQUEST_LOCATION = 199;
    int gap = 5 ;
    LocationRequest mLocationRequest;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;

    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = android.Manifest.permission.ACCESS_FINE_LOCATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_garage);

        BottomSheetDialog bottomSheerDialog = new BottomSheetDialog(this);
        View parentView = getLayoutInflater().inflate(R.layout.dialog,null);
        bottomSheerDialog.setContentView(parentView);

        RecyclerView rcv = parentView.findViewById(R.id.garages);

        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100,getResources().getDisplayMetrics());
        bottomSheerDialog.show();



        final GarageAdapter[] garageAdapter = {new GarageAdapter(ShowMap.this, garageList)};
        //userView.setAdapter(addressAdapter);

            FirebaseFirestore.getInstance().collection("Garage").addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            assert value != null;
                            if (!value.isEmpty()) {
                                List<DocumentSnapshot> list = value.getDocuments();
                                try {
                                    for (DocumentSnapshot d : list) {
                                        Log.d("userGarage", "onCreate: " + d);
                                        Garage g = d.toObject(Garage.class);

                                        allAddresss.add(new LatLng(Objects.requireNonNull(g).getLatitude(), g.getLongitude()));
                                        garageList.add(g);
                                        garageAdapter[0] = new GarageAdapter(ShowMap.this, garageList);
                                        garageAdapter[0].notifyDataSetChanged();
                                    }



                                    try {
                                        Routing routing = new Routing.Builder()
                                                .travelMode(Routing.TravelMode.DRIVING)
                                                .withListener(ShowMap.this)
                                                .waypoints(allAddresss)
                                                .key("AIzaSyAgddM3SuCy4Qiz0DjM6lE13C5P2rbY3RI")
                                                .build();

                                        routing.execute();


                                        GarageAdapter garageAdapter = new GarageAdapter(getApplicationContext(), garageList);
                                        rcv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                        rcv.setAdapter(garageAdapter);
                                        garageAdapter.notifyDataSetChanged();


                                    } catch (Exception d) {
                                        Toast.makeText(getApplicationContext(), "Null", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception g) {
                                    Log.d("frgre", "onCreate: + ni" + g);
                                }

                            }
                        }
                    });



        // options = new MarkerOptions();
        // options.icon(bitmapDescriptorFromVector(getContext(), R.drawable.ic_icon, R.color.color_theme_2));

        buildGoogleApiClient();
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }


       // fetchLocation();

    }

    public synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getBaseContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            showSettingsAlert();
        } else {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }



    @Override
    public void onRoutingFailure(RouteException e) {
        Log.d("onRoutingFailure", "onRoutingFailure: " + e.getMessage());
    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<com.directions.route.Route> route, int shortestRouteIndex) {


        CameraUpdate center = CameraUpdateFactory.newLatLng(allAddresss.get(0));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(12);

        map.moveCamera(center);
        map.moveCamera(zoom);

        if (polyLines.size() > 0) {
            for (Polyline poly : polyLines) {
                poly.remove();
            }
        }
        polyLines = new ArrayList<>();
        //add route(s) to the map.

        try {
            for (int i = 0; i < route.size(); i++) {
                //In case of more than 5 alternative routes
                PolylineOptions polyOptions = new PolylineOptions();
                polyOptions.color(getResources().getColor(R.color.colorPrimary));
                polyOptions.width(9 + i * 3);
                polyOptions.addAll(route.get(i).getPoints());
                Polyline polyline = map.addPolyline(polyOptions);
                polyLines.add(polyline);
            }
        } catch (Exception ignored) {

        }

        for (int i = 0; i < garageList.size(); i++) {
            if (i == 0) {
                Garage l = garageList.get(i);
                MarkerOptions options = new MarkerOptions();
                options.position(new LatLng(l.getLatitude(), l.getLongitude()));
                options.title(l.getAddress());
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                map.addMarker(options);
            } else if (i == garageList.size() - 1) {
//                Garage l = garageList.get(i);
//                MarkerOptions options = new MarkerOptions();
//                options.position(new LatLng(l.getLat(), l.getLongitude()));
//                options.title(l.getAddress());
//                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
//                map.addMarker(options);
            } else {
                Garage l = garageList.get(i);
                MarkerOptions options = new MarkerOptions();
                options.position(new LatLng(l.getLatitude(), l.getLongitude()));
                options.title(l.getAddress());
                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
                map.addMarker(options);
            }
        }
    }

    @SuppressLint("ResourceType")
    private BitmapDescriptor bitmapDescriptorFromVector(Context context) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.officer_location);
        Bitmap bitmap = Bitmap.createBitmap(Objects.requireNonNull(background).getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onRoutingCancelled() {

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.setTrafficEnabled(false);
        map.setBuildingsEnabled(false);

//        for(Garage g : garageList){
//            LatLng current = new LatLng(Objects.requireNonNull(g).getLatitude(), g.getLongitude());
//            updateCamera(current, busLocation);
//        }
    }

    private void updateCamera(LatLng currentLatLng, Marker marker) {
        MarkerOptions options = new MarkerOptions();
        if (marker == null) {
            options.position(currentLatLng);
            options.icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_location_small));
            options.flat(true);
            options.anchor(0.5f, 0.5f);
            map.addMarker(options);
        } else {
            marker.setPosition(currentLatLng);
        }
        CameraPosition cameraPosition = new CameraPosition.Builder(map.getCameraPosition())
                .target(currentLatLng).zoom(8).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        Objects.requireNonNull(vectorDrawable).setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }






    @SuppressLint("SetTextI18n")
    @Override
    public void onLocationChanged(@NonNull Location loc) {
        updateLocation(loc);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(5 * 1000);
        mLocationRequest.setWaitForAccurateLocation(true);
        mLocationRequest.setSmallestDisplacement(1);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            showSettingsAlert();
        } else {
            if (mGoogleApiClient.isConnected())
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);

        result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

        result.setResultCallback(result -> {
            final Status status = result.getStatus();
            //final LocationSettingsStates state = result.getLocationSettingsStates();
            switch (status.getStatusCode()) {
                case LocationSettingsStatusCodes.SUCCESS:
                    // All location settings are satisfied. The client can initialize location
                    // requests here.
                    //...
                    break;
                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                    // Location settings are not satisfied. But could be fixed by showing the user
                    // a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        status.startResolutionForResult(
                                ShowMap.this,
                                REQUEST_LOCATION);
                    } catch (IntentSender.SendIntentException e) {
                        // Ignore the error.
                    }
                    break;
                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    // Location settings are not satisfied. However, we have no way to fix the
                    // settings so we won't show the dialog.
                    //...
                    break;
            }
        });

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResult()", Integer.toString(resultCode));
        //final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        if (requestCode == REQUEST_LOCATION) {
            switch (resultCode) {
                case Activity.RESULT_OK: {
                    Toast.makeText(ShowMap.this, "Location Enabled. Thank you", Toast.LENGTH_LONG).show();
                    if (mGoogleApiClient.isConnected())
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            showSettingsAlert();
                        }else {
                            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                        }
                    break;
                }
                case Activity.RESULT_CANCELED: {
                    Toast.makeText(this, "Location Not updating", Toast.LENGTH_SHORT).show();
                    break;
                }
                default: {
                    break;
                }
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    void updateLocation(Location loc){
        double latitude = loc.getLatitude();
        double longitude = loc.getLongitude();



        LatLng current = new LatLng(latitude, longitude);
        updateCamera(current, busLocation);


        float[] results = new float[1];
        Location.distanceBetween(latitude, longitude, 0, 0, results);
        float currentDistanceFromPreviousStation = results[0];

        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ShowMap.this, R.style.AlertDialogCustom);

        alertDialog.setTitle("GPS Error");
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Fix",
                (dialog, which) -> {
                    Intent intent = new Intent(
                            Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                });
        alertDialog.setNegativeButton("Cancel",
                (dialog, which) -> {
                    dialog.cancel();
                });

        // Showing Alert Message
        alertDialog.show();
    }
    @Override
    public void onStart(){
        super.onStart();
        try {
            if(this.mGoogleApiClient != null){
                this.mGoogleApiClient.connect();
            }
        }catch (Exception ignored){

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            //stop location updates when Activity is no longer active
            if (mGoogleApiClient != null) {
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            }
        }catch (Exception ignored){

        }
    }

}