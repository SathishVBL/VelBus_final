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
                "vellore",
                "thiruvannamala",
                "sankarapuram",
                "kallakurichi",
                "arni",
                "villupuram",
                "vridhachalam",
                "thittakudi",
                "neyveli",
                "kumbakonam",
                "nannilam",
                "tirukoilure",
                "panruti",
                "cuddalore",
                "tindivanam",
                "puducherry",
                "mayiladuthurai",
                "chidambaram",
                "chetpet",
                "gingee",
                "polur",
                "thiruvannamalai",
                "tirukoilur",
                "ulundurpet",
                "perambalur",
                "trichy",
                "vallimalai koot road",
                "uzhavar santhai",
                "katpadi junction",
                "chittor",
                "odai pillaiyar",
                "silk mill",
                "kangeyanallur",
                "viruthampet",
                "New bus stand",
                "National pachaiyappas",
                "CMC hospital",
                "Old bus stand",
                "Raja Theatre stop",
                "Voorhees College",
                "Roundana",
                "Lakshmi mill",
                "Tollgate",
                "Allapuram",
                "Thorapadi",
                "Central jail stop",
                "Female jail stop",
                "Thanthai periyar polytechnic",
                "CMC Hospital",
                "Bagayam",
                "Otteri",
                "Virupakshipuuram",
                "Kuppam",
                "Sainathapuram",
                "DMK College",
                "Sankaranpalayam",
                "Velapadi",
                "Dhinakaran",
                "Eye hospital",
                "Raja theatre",
                "Vellore Old bus stand",
                "National Pachaiyappas",
                "Vellore New bus Stand",
                "Viruthampet",
                "Kangeyanallur",
                "Silk Mill",
                "Odai pillaiyar koil",
                "Chittoor bus stand",
                "Katpadi Junction",
                "Uzhavar sandhai",
                "Vallimalai koot road",
                "sriperumbudur",
                "poonamalle",
                "maduravoyal",
                "koyambedu",
                "kaakhidhapet",
                "perumugai",
                "rathnagiri",
                "melvisharam",
                "arcot",
                "konavattam",
                "poigai",
                "sethuvalai",
                "pallikonda",
                "gudiyatham",
                "nagore",
                "nagapattinam",
                "thanjavur",
                "adukkamparai"
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