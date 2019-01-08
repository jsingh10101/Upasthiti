package com.example.saurabh.upasthiti;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;
import java.util.Arrays;

/**
 * Created by JAPNEET SINGH on 01-Nov-18.
 */ public class ConSql extends AsyncTask<String, String, String[]> {
    private String un, pass, db, ip;

    public ConSql() {
        un = "root";
        pass = "root";
        db = "GKV2016";
        ip = "13.233.225.36:49170";
    }

    @Override
    protected String[] doInBackground(String... strings) {
        String ConnectionURL;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://" + ip + ";DatabaseName=" + db + ";user=" + un + ";password=" + pass + ";";

            Connection con = DriverManager.getConnection(ConnectionURL);

            String query=strings[0];
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(query);
            ResultSetMetaData resultSetMetaData=rs.getMetaData();
            rs.last();
            String[] sm=new String[rs.getRow()];
            rs.beforeFirst();
            for (int i = 0;rs.next() ; i++) {
                sm[i]="";
                for (int j = 1; j <=resultSetMetaData.getColumnCount() ; j++) {
                    //Log.v("Column Type for row "+(i+1)+" column "+resultSetMetaData.getColumnName(j),""+resultSetMetaData.getColumnType(j));
                    if(resultSetMetaData.getColumnType(j)==Types.BLOB)
                    {
                        Blob bs=rs.getBlob(j);
                        byte[] bytes=bs.getBytes(1,(int)bs.length());
                        sm[i]+=(Base64.encodeToString(bytes,Base64.DEFAULT)+"]");
                    }
                    else
                    sm[i]+=(rs.getString(j)+"]");
                }
            }
            /*st.close();
            con.close();*/
            return sm;
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
