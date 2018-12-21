package com.example.saurabh.upasthiti;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class SplashActivity extends AppCompatActivity {

    ImageView imageView;
    SharedPreferences sp;
    String code;


    private Handler handler=new Handler();
    private static int SPLASH_TIME_OUT=3000;
    //Duration of wait 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sp=getSharedPreferences("attendance", Context.MODE_PRIVATE);
        code=sp.getString("code",null);

        imageView=(ImageView)findViewById(R.id.image);

        //Adding a dependency in Module:app for Picasso
        //Making a java class CircleTransform for transforming rectangular image to circular.
        Picasso.with(getApplicationContext()).load(R.drawable.appicon2)
                .error(R.drawable.appicon2)
                .transform(new CircleTransform()).into(imageView);
        //Picasso will show if the error() part it fails.

        //Handler to start the Main-Activity and close this Splash-Screen after 3 seconds.
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Class cls;
                if(code==null)
                    cls=LoginActivity.class;
                else
                    cls=MainActivity.class;
                Intent i = new Intent(SplashActivity.this, cls);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }
}
