package com.example.saurabh.upasthiti;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
    Connection con;
    TextView name,post,facl,dept;
    ImageView lim,him;
    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        name=findViewById(R.id.tname);
        post=findViewById(R.id.tpost);
        facl=findViewById(R.id.tfacl);
        dept=findViewById(R.id.tdept);
        setSupportActionBar(toolbar);
        lim=findViewById(R.id.limage);
        him=findViewById(R.id.himage);
        code=getIntent().getStringExtra("code");
        new ConSql().execute("");
        mDrawerLayout =(DrawerLayout)findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.open,R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

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
    private class ConSql extends AsyncTask<String, String, Connection> {
        private String un, pass, db, ip;


        public ConSql() {
            un = "root";
            pass = "root";
            db = "GKV2016";
            ip = "13.127.144.24:49170";
        }

        @Override
        protected Connection doInBackground(String... strings) {
            String ConnectionURL;
            try {
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                ConnectionURL = "jdbc:jtds:sqlserver://" + ip + ";DatabaseName=" + db + ";user=" + un + ";password=" + pass + ";";

                con = DriverManager.getConnection(ConnectionURL);

                String query = "SELECT NAME,POST,FACULTY,DEPT,PHOTO FROM GKV2016.dbo.EMPMASTER WHERE CODE=\'"+code+"\';";
                Statement st = con.createStatement();
                final ResultSet rs = st.executeQuery(query);
                if (rs.next()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                name.setText(rs.getString("NAME"));
                                post.setText(rs.getString("POST"));
                                facl.setText(rs.getString("FACULTY"));
                                dept.setText(rs.getString("DEPT"));
                                Blob bs=rs.getBlob("PHOTO");
                                if(bs!=null){
                                byte[] bytes=bs.getBytes(1,(int)bs.length());
                                bs.free();
                                Bitmap ss= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                                lim.setImageBitmap(ss);
                                him.setImageBitmap(ss);}
                            }
                            catch (Exception e) {
                                Log.e("Error", e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    });


                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), "user not found", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                rs.close();
                st.close();
                con.close();
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
    }

}
