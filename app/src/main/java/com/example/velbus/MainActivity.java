// MainActivity.java
package com.example.velbus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // MainActivity.java
        MotionLayout motionLayout = findViewById(R.id.mainlayout);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.textView1).startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.text2));
                findViewById(R.id.textView2).startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.text1));

//                findViewById(R.id.textView1).startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.text1));
//                findViewById(R.id.textView2).startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.text2));

                motionLayout.transitionToEnd();



                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(MainActivity.this, searchpage.class);
                        startActivity(intent);
                        finish();
                    }
                }, 4000); // 4 seconds delay to run the intent after the exectuin of motionLayout.transitionToEnd();
            }
        }, 400); // 400 initial delay to run the motionLayout.transitionToEnd(); and inner hanlder.






    }
//    public void onclickSeeDirection(View view){
//        Intent intent=new Intent(MainActivity.this, see_direction_mapview.class);
//        startActivity(intent);
//        finish();
//    }

    // The rest of your MainActivity code...

}
