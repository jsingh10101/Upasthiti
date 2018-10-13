package com.example.saurabh.upasthiti;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    String un,pass,db,ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        log =(Button)findViewById(R.id.id_login);
        username=(EditText)findViewById(R.id.edt1);
        pwd=(EditText)findViewById(R.id.edt2);
        un="root";
        pass="root";
        db="GKV2016";
        ip="192.168.43.2:49170";
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ConSql().execute("");
            }
        });

    }
    class ConSql extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... strings) {
            String ConnectionURL;
            try {
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                ConnectionURL="jdbc:jtds:sqlserver://"+ip+";DatabaseName="+db+";user="+un+";password="+pass+";";

                con= DriverManager.getConnection(ConnectionURL);
                if(con==null)
                    throw new Exception();
                String empuser= username.getText().toString();
                String emppass = pwd.getText().toString();
                String query = "select CODE from GKV2016.dbo.EMPMASTER WHERE CODE = "+empuser+" AND PWD = "+emppass+";";

                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);

                if(rs.next())
                {
                    Intent obj = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(obj);
                }
                else
                    {
                        Toast.makeText(LoginActivity.this,"user not found",Toast.LENGTH_LONG).show();
                    }

                    rs.close();
                st.close();
                con.close();

            }catch (Exception e)
            {
                Log.e("Error",""+e.getMessage());
            }
            return null;
        }
    }

}
