package com.example.saurabh.upasthiti;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle ;
    SharedPreferences sp;
    Connection con;
    TextView name,post,facl,dept,phone;
    ImageView lim,him;
    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp=getSharedPreferences("attendance", Context.MODE_PRIVATE);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        name=findViewById(R.id.tname);
        post=findViewById(R.id.tpost);
        facl=findViewById(R.id.tfacl);
        dept=findViewById(R.id.tdept);
        setSupportActionBar(toolbar);
        phone=findViewById(R.id.tphone);
        lim=findViewById(R.id.limage);
        him=findViewById(R.id.himage);
        mDrawerLayout =(DrawerLayout)findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.open,R.string.close);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        ConSql s=new ConSql();
        code=sp.getString("code",null);
        String query = "SELECT NAME,POST,MOBILE,FACULTY,DEPT,PHOTO FROM GKV2016.dbo.EMPMASTER WHERE CODE=\'"+code+"\';";
        s.execute(query);
        try {
            final String[] sd=(s.get())[0].split("]");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            name.setText(sd[0]);
                            post.setText(sd[1]);
                            phone.setText(sd[2]);
                            facl.setText(sd[3]);
                            dept.setText(sd[4]);
                            if(sd[5]!=null){
                                byte[] bytes=Base64.decode(sd[5],Base64.DEFAULT);
//                                bs.free();
                                Bitmap ss= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                                lim.setImageBitmap(ss);
                                //him.setImageBitmap(ss);
                            }
                        }
                        catch (Exception e) {
                            Log.e("Error", e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });

        }catch (ArrayIndexOutOfBoundsException e){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getBaseContext(), "User Not Found", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        NavigationView navigationView= (NavigationView) findViewById(R.id.id_navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        });

//        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
