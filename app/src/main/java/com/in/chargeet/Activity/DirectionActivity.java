package com.in.chargeet.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.in.chargeet.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DirectionActivity extends AppCompatActivity implements OnMapReadyCallback, RoutingListener {

    BottomNavigationView bottom_navigation;
    TextView myVehicles, myBooking, setting, wallet, bookNow;
    ImageView location, menuImage, zoomOut, zoomIn, goToCurrentLocation;
    BottomSheetDialog bottomSheetDialog;
    AlertDialog alert;
    AlertDialog.Builder alertDialog;
    LinearLayout menuLayout, menuButton;
    GoogleMap mMap;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    private List<Polyline> polylines = null;
    LatLng start;
    //    LatLng waypoint = new LatLng(23.034722, 72.546778);
    LatLng end;

    String TAG = "DirectionActivity";

    String latitude, longitude;


    List<LatLng> allMark = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);


        allMark.add(new LatLng(Double.parseDouble("21.085211"), Double.parseDouble("71.762057")));
        allMark.add(new LatLng(Double.parseDouble("21.353628"), Double.parseDouble("72.032716")));
        allMark.add(new LatLng(Double.parseDouble("21.757772"), Double.parseDouble("72.156965")));
        allMark.add(new LatLng(Double.parseDouble("22.493525 "), Double.parseDouble("72.657416")));


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        statusCheck();
        fetchLocation();


        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyCBZ1E4AGu6xP_VV4GWr_qjnOte9sFmh0A");
        }

// Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setHint("Your location");


        AutocompleteSupportFragment autocomplete = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete);
        autocomplete.setHint("Choose destination");


        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
        autocomplete.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));


        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("TAG", "Place: " + place.getName() + ", " + place.getId());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("TAG", "An error occurred: " + status);
            }
        });

        autocomplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("TAG", "Place: " + place.getName() + ", " + place.getId());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("TAG", "An error occurred: " + status);
            }
        });
        Intent intent = getIntent();

        latitude = intent.getStringExtra("Latitude");
        longitude = intent.getStringExtra("Longitude");


        Log.e(TAG, "onCreate: " + latitude + "sdfghhgf" + longitude);

//        Log.e(TAG, "onCreate: "+ currentLocation.getLongitude() );


        init();


    }

    public void init() {

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
        bottomSheetDialog = new BottomSheetDialog(DirectionActivity.this);
        bottomSheetDialog.setContentView(R.layout.select_charging_point_popup);
        bookNow = bottomSheetDialog.findViewById(R.id.bookNow);
        wallet = bottomSheetDialog.findViewById(R.id.wallet);


        alertDialog = new AlertDialog.Builder(DirectionActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.payment_option_layout, null);
        alertDialog.setView(dialogLayout);
        alert = alertDialog.create();


        bottom_navigation.getMenu().findItem(R.id.order).setChecked(true);
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


                if (currentLocation != null) {

                    LatLng pos = new LatLng(start.latitude, start.longitude);
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(pos));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 15));
                }
            }
        });

    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap = googleMap;

//        start = new LatLng(22.3038945, 70.80215989999999);
        start = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        end = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));


        if (currentLocation != null) {

            LatLng pos = new LatLng(start.latitude, start.longitude);
            mMap.animateCamera(CameraUpdateFactory.newLatLng(pos));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 10));
        }

//        Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
//                        .clickable(true)
//                        .add(
//                                new LatLng(22.3038945, 70.8021598),
//                                new LatLng(23.0476079, 72.5155059)
//
//                        )
//        );
//        polyline1.setTag("A");
//        // Position the map's camera near Alice Springs in the center of Australia,
//        // and set the zoom factor so most of Australia shows on the screen.
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(22.3038945, 70.8021598), 15));
//
//        googleMap.setOnPolylineClickListener(this);
//        googleMap.setOnPolygonClickListener(this);


        Log.e("TAG", "onMapReady: " + "onMapReady");
        Routing routing = new Routing.Builder()
                .key("AIzaSyCBZ1E4AGu6xP_VV4GWr_qjnOte9sFmh0A")
                .travelMode(Routing.TravelMode.WALKING)
                .withListener(this)
                .waypoints(start, end)
                .build();
        routing.execute();


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void onBackPressed() {

        Intent intent;
        intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
        super.onBackPressed();

    }


    @Override
    public void onRoutingFailure(RouteException e) {

        Log.e("TAG", "onMapReady: " + e.getMessage());
    }

    @Override
    public void onRoutingStart() {
        Log.e("TAG", "onMapReady: " + "onRoutingStart");

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {

        Log.e("TAG", "onMapReady: " + route + "-----" + shortestRouteIndex);

        CameraUpdate center = CameraUpdateFactory.newLatLng(start);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
        if (polylines != null) {
            polylines.clear();
        }
        PolylineOptions polyOptions = new PolylineOptions();
        LatLng polylineStartLatLng = null;
        LatLng polylineEndLatLng = null;


        polylines = new ArrayList<>();
        //add route(s) to the map using polyline
        for (int i = 0; i < route.size(); i++) {

            if (i == shortestRouteIndex) {
                polyOptions.color(getResources().getColor(R.color.green));
                polyOptions.width(7);
                polyOptions.addAll(route.get(shortestRouteIndex).getPoints());
                Polyline polyline = mMap.addPolyline(polyOptions);
                polylineStartLatLng = polyline.getPoints().get(0);
                int k = polyline.getPoints().size();
                polylineEndLatLng = polyline.getPoints().get(k - 1);
                polylines.add(polyline);

            } else {

            }

        }

        //Add Marker on route starting position
        MarkerOptions startMarker = new MarkerOptions();
        startMarker.position(polylineStartLatLng);
        startMarker.title("My Location");
        mMap.addMarker(startMarker);

        //Add Marker on route ending position
        MarkerOptions endMarker = new MarkerOptions();
        endMarker.position(polylineEndLatLng);
        endMarker.title("Destination");
        mMap.addMarker(endMarker);


        for (int i = 0; i < allMark.size(); i++) {
            MarkerOptions markerOptions = new MarkerOptions().position(allMark.get(i));
            mMap.addMarker(markerOptions.position(allMark.get(i)));
        }

    }

    @Override
    public void onRoutingCancelled() {

        Log.e("TAG", "onMapReady: " + "onRoutingCancelled");

    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void fetchLocation() {


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    Toast.makeText(getApplicationContext(), currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onSuccess: " + currentLocation.getLatitude() + "" + currentLocation.getLongitude());
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    assert supportMapFragment != null;
                    supportMapFragment.getMapAsync(DirectionActivity.this);


                }
            }
        });
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

}