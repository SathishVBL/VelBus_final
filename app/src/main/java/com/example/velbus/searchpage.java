package com.example.velbus;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;


public class searchpage extends AppCompatActivity {

AutoCompleteTextView source,desti;

public  String sourcePlace,destiPlace;
    @SuppressLint({"MissingInflatedId", "CutPasteId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchpage);
// store or get the bus stop names to show auto complete us the apadter
        String[] stop={
                "Vellore",
                "Thorappadi",
                "Female Jail Stop",
                "Chitheri",
                "Palathuvannan Bus Stop",
                "Amrithi",
                "Bagayam",
                "Kannamangalam",
                "Santhavasal",
                "Polur",
                "Kalasapakkam",
                "Tiruvannamalai",
                "Ranipet",
                "Sholinghur",
                "Thiruthani",
                "Pallikonda",
                "Ambur",
                "Vaniyambadi",
                "Yelagiri",
                "Thirupathur",
                "Vellore New Bus Stand",
                "National Pachaiyappas",
                "CMC Hospital",
                "Vellore Old Bus Stand",
                "Raja Theatre Stop",
                "Eye Hospital",
                "Dhinakaran",
                "Velapadi",
                "Sankaranpalayam",
                "DMK College",
                "Sainathapuram",
                "Kuppam",
                "Virupakshipuuram",
                "Otteri",
                "Adukkamparai",
                "Mathanur",
                "MGR Statue Stop",
                "Sripuram Golden Temple",
                "Oosur",
                "Anaicut",
                "Bootherimedu",
                "Sevoor",
                "Arni",
                "Nethapakam",
                "Cheyyar",
                "Arcot",
                "Walajapet",
                "Bagaveli",
                "Panapakkam",
                "Melvisharam",
                "Veppur",
                "Voorhees College",
                "Roundana",
                "Lakshmi Mill",
                "Tollgate",
                "Allapuram",
                "Gururajapalayam",
                "Odugathur",
                "Kaakhidhapet",
                "Perumugai",
                "Rathnagiri",
                "Konavattam",
                "Poigai",
                "Sethuvalai",
                "Gudiyatham",
                "Kanchipuram",
                "Sriperumbudur",
                "Chennai",
                "Tirukoilur",
                "Ulundurpet",
                "Perambalur",
                "Trichy",
                "Chetpet",
                "Gingee",
                "Villupuram",
                "Panruti",
                "Neyveli",
                "Kumbakonam",
                "Thanjavur",
                "Chidambaram",
                "Nagore",
                "Nagapattinam",
                "Mannargudi",
                "Mayiladuthurai",
                "Tindivanam",
                "Puducherry",
                "Cuddalore",
                "Nannilam",
                "Vridhachalam",
                "Thittakudi",
                "Tiruvannamala",
                "Sankarapuram",
                "Kallakurichi"
               };
        ArrayAdapter<String> ar=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,stop);
//get id
        source= findViewById(R.id.editEnterSrc);
        desti=findViewById(R.id.editEnterDest);


// add the auto complete adapter to the view for source and destination
        source.setAdapter(ar);
        desti.setAdapter(ar);


        View searchBtn = findViewById(R.id.searchBtn);
        if (searchBtn != null) {
            searchBtn.setOnClickListener(view -> {
                sourcePlace = source.getText().toString();
                destiPlace = desti.getText().toString();

                // add the intent to call the busInfo page and pass places
                Intent navToBusInfo = new Intent(searchpage.this, busInfo.class);
                navToBusInfo.putExtra("sourcePlace", sourcePlace);
                navToBusInfo.putExtra("destiPlace", destiPlace);
                startActivity(navToBusInfo);
                // your existing click listener code
            });
        }
// set-onclick listener to call the bus info and pass the source and destination place........








    }
}