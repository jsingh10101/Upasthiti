package com.example.saurabh.upasthiti;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class LoginActivity extends AppCompatActivity {

    Button log;
    EditText username, pwd;
    Connection con;
    String un, pass, db, ip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        log = (Button) findViewById(R.id.id_login);
        username = (EditText) findViewById(R.id.edt1);
        pwd = (EditText) findViewById(R.id.edt2);
        un = "root";
        pass = "root";
        db = "GKV2016";
        ip = "13.127.144.24:49170";
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConSql dd = new ConSql();
                dd.execute("");
            }
        });

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
                String empuser = username.getText().toString();
                String emppass = pwd.getText().toString();
                String query = "select NAME from GKV2016.dbo.EMPMASTER WHERE CODE = \'" + empuser + "\' AND PWD = \'" + emppass + "\';";

                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                if (rs.next()) {
                    final String name = rs.getString(1);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), "Welcome Mr." + name, Toast.LENGTH_SHORT).show();
                        }
                    });
                    Intent obj = new Intent(LoginActivity.this, MainActivity.class);
                    obj.putExtra("code", empuser);
                    startActivity(obj);
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

