package com.example.saurabh.upasthiti;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;

/**
 * Created by JAPNEET SINGH on 01-Nov-18.
 */ public class ConSql extends AsyncTask<String, String, ResultSet> {
    private String un, pass, db, ip;
    private Connection con;
    private Statement st;

    public ConSql() {
        un = "root";
        pass = "root";
        db = "GKV2016";
        ip = "13.126.230.125:49170";
    }

    @Override
    protected ResultSet doInBackground(String... strings) {
        String ConnectionURL;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://" + ip + ";DatabaseName=" + db + ";user=" + un + ";password=" + pass + ";";

            con = DriverManager.getConnection(ConnectionURL);

            String query=strings[0];
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            /*st.close();
            con.close();*/
            return rs;
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
