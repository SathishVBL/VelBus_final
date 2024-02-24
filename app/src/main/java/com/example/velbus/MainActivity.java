// MainActivity.java
package com.example.velbus;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button searchBtn = findViewById(R.id.startBTN);

        searchBtn.setOnClickListener(v -> {
            // When searchBtn is clicked, start SecondActivity
            Intent intent = new Intent(MainActivity.this, searchpage.class);
            startActivity(intent);

            // Optionally, you can finish() the current activity if you don't want to come back to it
            // finish();
        });


    }
    public void onclickSeeDirection(View view){
        Intent intent=new Intent(MainActivity.this, see_direction_mapview.class);
        startActivity(intent);
    }

    // The rest of your MainActivity code...

}
