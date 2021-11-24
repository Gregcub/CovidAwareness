package com.gac5206.covidawareness.maps;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadUrl {

    public String readUrl(String strUrl) throws IOException {
        String data = "";
        InputStream stream = null;
        HttpURLConnection conn = null;

        try{
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            stream = conn.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while((line = reader.readLine()) != null){
               buffer.append(line);
            }

            data = buffer.toString();
            Log.d("downloadUrl", data);

            reader.close();

        }catch(Exception e){
            Log.d("Exception", e.toString());
        }



        if (stream != null) {
            stream.close();
        }
        if (conn != null) {
            conn.disconnect();
        }

        return data;

    }

}
