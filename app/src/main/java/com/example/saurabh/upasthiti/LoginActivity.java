package com.example.saurabh.upasthiti;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutionException;


public class LoginActivity extends AppCompatActivity {

    Button log;
    EditText username, pwd;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sp=getSharedPreferences("attendance", Context.MODE_PRIVATE);
        log = (Button) findViewById(R.id.id_login);
        username = (EditText) findViewById(R.id.edt1);
        pwd = (EditText) findViewById(R.id.edt2);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConSql dd = new ConSql();
                final String empuser = username.getText().toString();
                String emppass = pwd.getText().toString();
                String query = "select NAME from GKV2016.dbo.EMPMASTER WHERE CODE = \'" + empuser + "\' AND PWD = \'" + emppass + "\';";
                dd.execute(query);
                try {
                    final String s=(dd.get())[0];
                    //GKV/183,J1009832
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SharedPreferences.Editor e=sp.edit();
                            e.putString("code",empuser);
                            e.apply();
                            Toast.makeText(getBaseContext(), "Welcome Mr." + s, Toast.LENGTH_SHORT).show();
                        }
                    });
                    Intent obj = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(obj);
                    finish();
                }catch (ArrayIndexOutOfBoundsException e){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), "User Not Found", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

    }



}

