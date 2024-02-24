package com.example.velbus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.ViewParent;
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

public class busInfo extends AppCompatActivity {
    private RecyclerView rcv;
    public String givenSource,givenDesti;
    public List<String> available_route=new ArrayList<>();
    public  JSONObject jsonObject;
    TextView sourceC,destnationC;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_info);

        rcv = findViewById(R.id.list_bus_info);
        rcv.setHasFixedSize(true);
        rcv.setLayoutManager(new LinearLayoutManager(this));

        sourceC=findViewById(R.id.sourcePlace);
        destnationC=findViewById(R.id.destiPlace);


//        extract the data
        Bundle extraData=getIntent().getExtras();
        if(extraData!=null){
            givenSource=extraData.getString("sourcePlace");
            givenDesti=extraData.getString("destiPlace");
            sourceC.setText(givenSource);
            destnationC.setText(givenDesti);
        }


        fetchJson();


//        back to the search page........
        findViewById(R.id.back_icon).setOnClickListener(View ->{
            Intent intent=new Intent(busInfo.this,searchpage.class);
            startActivity(intent);
        });
    }



    public void fetchJson(){
        RequestQueue queue= Volley.newRequestQueue(this);//to manage many request
        String url ="https://raw.githubusercontent.com/SathishVBL/Bus_route/main/all_bus_info.json";

        //response->through which the data is fetched
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
//  parse the json file to get the data and generate array for the adapter class
             jsonObject = new JSONObject(response);
            JSONArray routes = jsonObject.optJSONArray("routes");

            if (routes != null) {
                for (int i = 0; i < routes.length(); i++) {
                    JSONObject each = routes.optJSONObject(i);
                    if (each != null) {
                        String name = each.optString("name");
                        String source = each.optString("source");
                        String destination = each.optString("destination");
                        String routeName=each.optString("name");

                        JSONArray stopings = each.optJSONArray("stoping");
                        JSONArray timings = each.optJSONArray("source_time");
                        List<String> stop = new ArrayList<>();
                        if (stopings != null && timings != null) {

                            for (int j = 0; j < stopings.length(); j++) {
                                stop.add(stopings.optString(j));
                            }


                            if (stop.contains(givenSource) && stop.contains(givenDesti)) {
                                available_route.add(routeName);


                                // Do something with the 'name', 'source', 'destination', 'stop', and 'time'
                            }
                        }
                    }
                }
            }



// calling adapter class to bind the data
            BusInfoAdapter myAdapter = new BusInfoAdapter(this,available_route);
            rcv.setAdapter(myAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void onclickViewRoute(View view) {
        // Handle the click event here
        int position = rcv.getChildAdapterPosition(view);

        if (position != RecyclerView.NO_POSITION) {
            String selectedItem = available_route.get(position);

            // Create an Intent to start the DetailsActivity or the desired activity
            Intent intent = new Intent(busInfo.this, see_direction_mapview.class);
            intent.putExtra("selectedItem", selectedItem);

            // Start the new activity
            startActivity(intent);
        }
    }


}
