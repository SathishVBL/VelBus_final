package com.example.velbus;


import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.internal.PolylineEncoding;


import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;

import java.util.List;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class see_direction_mapview extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    //create custom marker for bbus  stop
    public Polyline mypolyline;
    public String encodeRoute;
    public String encodeStopings;
    public  String route_name;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_direction_mapview);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);


//        for  firebase references
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("drivers");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Check if the mapFragment is null to avoid potential issues
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.map, mapFragment)
                    .commit();
        }

        mapFragment.getMapAsync(this);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            route_name = b.getString("particular_route");
            // Now you can use the routeName variable as needed
        } else {
            // Handle the case where the Bundle is null
        }



        fetchJson();

    }


    public  void drawRoute(String encodeRoute){
        if(encodeRoute!=null) {
            Log.d("enoded route >>>>>>>>>>",encodeRoute);

            List<com.google.maps.model.LatLng> myoints = PolylineEncoding.decode(encodeRoute);
            List<com.google.android.gms.maps.model.LatLng> points = new ArrayList<>();
            for (com.google.maps.model.LatLng x : myoints) {
                LatLng temp = new LatLng(x.lat, x.lng);
                points.add(temp);
            }
            PolylineOptions polylineOptions = new PolylineOptions();
            polylineOptions.addAll(points);
            polylineOptions.color(Color.RED);
            polylineOptions.width(12);

            // Add polyline to the map
            if (polylineOptions != null) {
                // Initialize the Polyline here
                mypolyline = mMap.addPolyline(polylineOptions);

                int n=points.size()-1;
                BitmapDescriptor busStopIcon = BitmapDescriptorFactory.fromResource(R.drawable.bus_stop_icon);
                mMap.addMarker(new MarkerOptions().position(points.get(0)).title("sathish v").icon(busStopIcon));
                mMap.addMarker(new MarkerOptions().position(points.get(n)).title("sathish v").icon(busStopIcon));


            }

        }
        else {
            Toast.makeText(this, "Route details couldn't be fetched", Toast.LENGTH_SHORT).show();
            Log.d("=>=>=>=>=>=>=>=>=>=>=>","route data is not still  ------------------------------------------");

        }
    }



//fetch json object .............
public void fetchJson(){
    RequestQueue queue= Volley.newRequestQueue(this);//to manage many request
    String url ="https://raw.githubusercontent.com/SathishVBL/Bus_route/main/bus%20route%20details";

    StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            parseJson(response);
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
        }
    });
    queue.add(stringRequest);
}

    public void parseJson(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);

            // Replace "katpadi to bagayam" with the actual route name
            JSONObject routeData = jsonObject.getJSONObject(route_name);

            // Retrieve the encodedRouteJson and encodedStopingJson values
            encodeRoute = routeData.optString("encodedRouteJson");
            encodeStopings = routeData.optString("endodedStopingJson");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    //=============================================================================================================================================================

    private void fetchLocationPeriodically() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                databaseReference.child("driver2").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Double latitude = dataSnapshot.child("lat").getValue(Double.class);
                        Double longitude = dataSnapshot.child("lan").getValue(Double.class);

                        if (latitude != null && longitude != null && mMap != null) {
                            LatLng newPosition = new LatLng(latitude, longitude);
                            mMap.clear(); // Clear existing markers
                            BitmapDescriptor livetrack = BitmapDescriptorFactory.fromResource(R.drawable.live_track_icon);

                            mMap.addMarker(new MarkerOptions().position(newPosition).title("New Location").icon(livetrack));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newPosition, 15)); // Zoom to new position
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle error
                    }
                });
                // Fetch data every 10 seconds
                handler.postDelayed(this, 10 * 1000);
            }
        }, 0); // Initial delay of 0 ms
    }

    // Handle permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchLocationPeriodically();
            }
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        LatLng vellore = new LatLng(12.934968 , 79.146881);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(vellore, 12));

        drawRoute(encodeRoute);
        fetchLocationPeriodically();

    }
}
