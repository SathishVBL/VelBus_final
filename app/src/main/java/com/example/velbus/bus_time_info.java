package com.example.velbus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class bus_time_info extends AppCompatActivity {
    public static String route_name;
    JSONArray sourec_time;
    RecyclerView rcv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_time_info);

         rcv=findViewById(R.id.bus_time_recycle);
        rcv.setHasFixedSize(true);
        rcv.setLayoutManager(new LinearLayoutManager(this));



        Bundle extra=getIntent().getExtras();
        assert extra != null;
        route_name=extra.getString("route_name");
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView text=findViewById(R.id.route_name);
        text.setText(route_name);
        text.setElevation(100);

        fetchJson();






    }

    public void fetchJson(){
        RequestQueue queue= Volley.newRequestQueue(this);//to manage many request
        String url ="https://raw.githubusercontent.com/SathishVBL/Bus_route/main/all_bus_info.json";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    parseJson(response);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
    }

    public void parseJson(String response) throws JSONException {
        List<String> times=new ArrayList<>();

//  parse the json file to get the data and generate array for the adapter class

        JSONObject jsonObject = new JSONObject(response);
        JSONArray routes = jsonObject.optJSONArray("routes");

        if (routes != null) {
            for (int i = 0; i < routes.length(); i++) {
                JSONObject each = routes.optJSONObject(i);
                String sampleroute=each.optString("name");
                if (each != null && sampleroute.equals(route_name)) {

                   sourec_time=each.optJSONArray("source_time");
                   for(int j = 0; j< Objects.requireNonNull(sourec_time).length(); j++){
                        times.add(sourec_time.optString(j));
                   }
                }
            }
        }

        timeInfoAdapter ad=new timeInfoAdapter(this,times);
        rcv.setAdapter(ad);

    }

}