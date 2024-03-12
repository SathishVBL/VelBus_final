package com.example.velbus;


import android.graphics.Color;
import android.os.Bundle;
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

public class see_direction_mapview extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    //create custom marker for bbus  stop
    public Polyline mypolyline;
    public String encodeRoute;
    public String encodeStopings;
    public  String route_name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_direction_mapview);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        // Check if the mapFragment is null to avoid potential issues
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();

            // Begin the transaction to add the mapFragment to the FragmentContainerView
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.map, mapFragment) // Replace R.id.map with your actual layout ID
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

//
//    public void pointMarker(String encodedStoping){
//        if(encodedStoping!=null) {
//            Log.d("enoded stoping >>>>>>>>",encodeStopings);
//
//            BitmapDescriptor busStopIcon = BitmapDescriptorFactory.fromResource(R.drawable.bus_stop_icon);
//
//            List<com.google.maps.model.LatLng> decodedStopings = PolylineEncoding.decode(encodedStoping);
//            for (com.google.maps.model.LatLng x : decodedStopings) {
//                LatLng temp = new LatLng(x.lat, x.lng);
//                mMap.addMarker(new MarkerOptions().position(temp).title("sathish v").icon(busStopIcon));
//            }
//
//
//
//        }
//        else {
//            Toast.makeText(this, "stopings details couldn't be fetched", Toast.LENGTH_SHORT).show();
//        Log.d("=>=>=>=>=>=>=>=>=>=>=>","data is not still  ------------------------------------------");
//        }
//    }

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
                BitmapDescriptor busStopIcon = BitmapDescriptorFactory.fromResource(R.drawable.bus_stop_icon);
                int n=points.size()-1;
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


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        LatLng vellore = new LatLng(12.934968 , 79.146881);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(vellore, 12));



//encodeStopings="aqrmAq}~aN|A|ZfAt_@e\\nL_LaDs_@f@od@eg@uPyG{SuBwXk@oo@wC{W~@";
//encodeRoute="mcfkAo}yaNgfAxv@qnAnr@mjAzKsy@fCou@wXi\\en@kGk_Ah\\yv@vd@{i@td@}i@|Sia@cX}i@cm@a]am@{i@i\\en@lNmz@|z@u]`bAiCry@?ty@lTlu@ja@|h@dn@lu@vv@|h@xv@n`@nr@zh@nr@am@dn@ou@nr@_bAb{@gfAzi@";

//        i}mAyl_bNM_JpEOjCd@vA`@bARv@n@h@B@vAnIbOv@|@|@ZfBd@nE~@~Bd@rBJfCGxJUvAdAS`KpA@pF{ArOsAtUmAvGh@dCT^{@H}@Ra@rXMnBg@fEcBfJuATq@GyA@wCjFCbBDVf@nTfAnELfJAhE@zDs@`FOvMg@jMMjLSrKEpEXrQbC|ItAvLvAtNv@lMZlTxAzm@hClV{AfK}BtNeGvJiDxCo@lLc@lIMrAH~C`A|Bt@
        drawRoute(encodeRoute);
//        drawRoute("oh}mAem_bNEmIjJj@vBBjAvAVtBfAhCtIvNxH|BzYf@?xJZdAvLyClR_BnQw@nJb@`AwBtVQhCa@fJmC|Eu@XcHlH?D~@zCTnPz@fV_@nl@gCrV^zMjA|i@fHviAlEpe@V|YwNpc@mEh_@lNhZ|QjmAiHfhA}Dvz@aJlx@cEpaAzCfFkFhC{Fv}B}rBzVePxEsLtTcNlU_MbO{MbGgFzFkK`B{GjHsLlIsKzXg\\dj@sm@v_@oh@bkAq~Ars@ycA|m@cgAtYywAx^s`CvMgt@jCcOhDgS~@_FyVwH");
//        pointMarker(encodeStopings);






    }
}
