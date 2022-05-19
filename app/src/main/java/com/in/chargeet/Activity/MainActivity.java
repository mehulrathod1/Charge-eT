package com.in.chargeet.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.Status;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.in.chargeet.Model.PowerStationDetailModel;
import com.in.chargeet.Model.PowerStationModel;
import com.in.chargeet.R;
import com.in.chargeet.Retrofit.Api;
import com.in.chargeet.Retrofit.RetrofitClient;
import com.in.chargeet.Utils.Glob;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    BottomNavigationView bottom_navigation;
    TextView myVehicles, myBooking, setting, wallet, bookNow, StationName, stationPower, power, stationRate;
    ImageView location, menuImage, zoomOut, zoomIn, goToCurrentLocation, connector3, connector2, connector1;
    BottomSheetDialog bottomSheetDialog;
    AlertDialog alert;
    AlertDialog.Builder alertDialog;
    LinearLayout menuLayout, menuButton;
    GoogleMap mMap;

    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    Place places;

    ArrayList<PowerStationModel.PowerStation> markersArray = new ArrayList<>();


//    LatLng sydney = new LatLng(-34, 151);
//    LatLng TamWorth = new LatLng(-31.083332, 150.916672);
//    LatLng NewCastle = new LatLng(-32.916668, 151.750000);
//    LatLng Brisbane = new LatLng(-27.470125, 153.021072);

    // creating array list for adding all our locations.
    public ArrayList<LatLng> locationArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLocation();

//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);


        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyCBZ1E4AGu6xP_VV4GWr_qjnOte9sFmh0A");
        }

// Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.

                places = place;
                Log.e("TAG", "Place: " + place.getName() + ", " + place.getId());
                Log.e("TAG", "latlong: " + place.getLatLng());

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.e("TAG", "An error occurred: " + status);
            }


        });


        // on below line we are adding our
        // locations in our array list.
//        locationArrayList.add(sydney);
//        locationArrayList.add(TamWorth);
//        locationArrayList.add(NewCastle);
//        locationArrayList.add(Brisbane);
//
        init();

    }


//    @Override
//    public void onMapReady(@NonNull GoogleMap googleMap) {
//
//        mMap = googleMap;
//
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(23.0225, 72.5714);
//        mMap.addMarker(new MarkerOptions()
//                .position(sydney)
//                .title("Ahmedabad"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(sydney, 15);
//        mMap.moveCamera(update);
//
//        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(@NonNull Marker marker) {
//
//                bottomSheetDialog.show();
//                return false;
//
//
//            }
//        });
//
//    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        getPowerStation(Glob.token);


        mMap = googleMap;

//        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
//        LatLng latLng2 = new LatLng(23.053262798651392, 72.51482667850078);
//        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("");
//        MarkerOptions markerOptions2 = new MarkerOptions().position(latLng2).title("");
//        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
//        googleMap.addMarker(markerOptions);
//        googleMap.addMarker(markerOptions2);

        Log.e("index", "onResponse: " + locationArrayList.toString());


//
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {

                getPowerStationDetail(Glob.token, "2");
                bottomSheetDialog.show();

                return false;

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLocation();
                }
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    public void init() {

        Glob.progressDialog(this);
        bottom_navigation = findViewById(R.id.bottom_navigation);
        myVehicles = findViewById(R.id.myVehicles);
        myBooking = findViewById(R.id.myBooking);
        setting = findViewById(R.id.setting);
        location = findViewById(R.id.location);
        menuLayout = findViewById(R.id.menuLayout);
        menuButton = findViewById(R.id.menuButton);
        menuImage = findViewById(R.id.menuImage);
        menuImage.setImageResource(R.drawable.ic_baseline_menu_24);
        zoomIn = findViewById(R.id.zoomIn);
        zoomOut = findViewById(R.id.zoomOut);
        goToCurrentLocation = findViewById(R.id.goToCurrentLocation);
        bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
        bottomSheetDialog.setContentView(R.layout.select_charging_point_popup);

        bookNow = bottomSheetDialog.findViewById(R.id.bookNow);
        wallet = bottomSheetDialog.findViewById(R.id.wallet);
        StationName = bottomSheetDialog.findViewById(R.id.StationName);
        stationPower = bottomSheetDialog.findViewById(R.id.stationPower);
        power = bottomSheetDialog.findViewById(R.id.power);
        stationRate = bottomSheetDialog.findViewById(R.id.stationRate);
        connector1 = bottomSheetDialog.findViewById(R.id.connector1);
        connector2 = bottomSheetDialog.findViewById(R.id.connector2);
        connector3 = bottomSheetDialog.findViewById(R.id.connector3);


        alertDialog = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.payment_option_layout, null);
        alertDialog.setView(dialogLayout);
        alert = alertDialog.create();


        bottom_navigation.getMenu().findItem(R.id.location).setChecked(true);
        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;

                switch (item.getItemId()) {

                    case R.id.location:
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.order:
                        intent = new Intent(getApplicationContext(), DirectionActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.filter:
                        intent = new Intent(getApplicationContext(), FilterActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.account:
                        intent = new Intent(getApplicationContext(), MyAccountActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                }

                return false;
            }
        });

        myVehicles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), MyVehicleActivity.class);
                startActivity(intent);

            }
        });
        myBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyBookingActivity.class);
                startActivity(intent);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bottomSheetDialog.show();

            }

        });

        bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChargingActivity.class);
                startActivity(intent);
            }
        });

        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.show();
            }
        });

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (menuLayout.getVisibility() == view.VISIBLE) {
                    menuLayout.setVisibility(View.GONE);
                    menuImage.setImageResource(R.drawable.ic_baseline_menu_24);

                } else {
                    menuLayout.setVisibility(View.VISIBLE);
                    menuImage.setImageResource(R.drawable.ic_baseline_close_24);
                }

            }
        });

        zoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mMap.animateCamera(CameraUpdateFactory.zoomOut());


            }
        });

        zoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.animateCamera(CameraUpdateFactory.zoomIn());

            }
        });

        goToCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LatLng pos = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(pos, 15);
                mMap.moveCamera(update);
            }
        });

    }

    public void getPowerStation(String token) {

        Api call = RetrofitClient.getClient(Glob.baseUrl).create(Api.class);
        Glob.dialog.show();


        call.getPowerStation(token).enqueue(new Callback<PowerStationModel>() {
            @Override
            public void onResponse(Call<PowerStationModel> call, Response<PowerStationModel> response) {

                PowerStationModel powerStationModel = response.body();
                List<PowerStationModel.PowerStation> dataList = powerStationModel.getDataList();

                for (int i = 0; i < dataList.size(); i++) {

                    PowerStationModel.PowerStation model = dataList.get(i);
                    PowerStationModel.PowerStation data = new PowerStationModel.PowerStation(
                            model.getId(), model.getName(), model.getLatitude(), model.getLongitude(), model.getIcon()

                    );
                    markersArray.add(data);


                    Log.e("data", "onResponse: " + model.getId());
                }
                for (int j = 0; j < markersArray.size(); j++) {
                    locationArrayList.add(new LatLng(Double.parseDouble(markersArray.get(j).getLatitude()), Double.parseDouble(markersArray.get(j).getLongitude())));

                    Log.e("index", "onResponse: " + locationArrayList.get(j).toString());
                }
                for (int i = 0; i < locationArrayList.size(); i++) {
                    Log.e("index", "onResponse: " + locationArrayList.get(i).toString());

                    // below line is use to add marker to each location of our array list.
                    mMap.addMarker(new MarkerOptions().position(locationArrayList.get(i)));

                    // below lin is use to zoom our camera on map.
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));

                    // below line is use to move our camera to the specific location.
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(i)));


                }

                Glob.dialog.dismiss();

            }

            @Override
            public void onFailure(Call<PowerStationModel> call, Throwable t) {
                Glob.dialog.dismiss();

            }
        });

    }

    public void getPowerStationDetail(String token, String powerStationId) {

        Api call = RetrofitClient.getClient(Glob.baseUrl).create(Api.class);
        Glob.dialog.show();


        call.getPowerStationDetail(token, powerStationId).enqueue(new Callback<PowerStationDetailModel>() {
            @Override
            public void onResponse(Call<PowerStationDetailModel> call, Response<PowerStationDetailModel> response) {

                PowerStationDetailModel powerStationDetailModel = response.body();
                PowerStationDetailModel.PowerStationData powerStationData = powerStationDetailModel.getData();

                StationName.setText(powerStationData.getName());
                stationPower.setText(powerStationData.getPower());
                power.setText(powerStationData.getPower());
                stationRate.setText(powerStationData.getRate());


                List<PowerStationDetailModel.PowerStationData.Connectors> connectorsList = powerStationData.getConnectors();

                Glide.with(getApplicationContext()).load(connectorsList.get(0).getImage()).into(connector1);
                Glide.with(getApplicationContext()).load(connectorsList.get(1).getImage()).into(connector2);
                Glide.with(getApplicationContext()).load(connectorsList.get(2).getImage()).into(connector3);

                for (int i = 0; i < connectorsList.size(); i++) {

                }

                Glob.dialog.dismiss();
            }

            @Override
            public void onFailure(Call<PowerStationDetailModel> call, Throwable t) {
                Glob.dialog.dismiss();
                Log.e("myError", "onFailure: " + t.getMessage());
            }
        });

    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
//                    Toast.makeText(getApplicationContext(), currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    assert supportMapFragment != null;
                    supportMapFragment.getMapAsync(MainActivity.this);
                }
            }
        });
    }

    protected Marker createMarker(double latitude, double longitude, String title) {

        return mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .title(title)

        );
    }

}