package com.example.snapdf;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.snapdf.AboutFragment.deptText;
import static com.example.snapdf.AboutFragment.nameText;
import static com.example.snapdf.AboutFragment.uvText;

//import static com.example.SnaPdf.MainActivity.textView;

public class JSONData extends AsyncTask<Void, Void, Void> {
    String line = "";
    String data = "";
    String nameData = "";
    String uvData = "";
    String deptData = "";

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        

        nameText.setText(nameData);//setting data on text view
        uvText.setText(uvData);
        deptText.setText(deptData);

    }
    @Override
    protected Void doInBackground(Void... voids) {

        try {
            URL url = new URL("https://api.myjson.com/bins/14mhc8");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            while(line != null){
                line = bufferedReader.readLine();
                data += line;
            }

            JSONArray ja = new JSONArray(data);
            for(int i = 0; i < ja.length(); i++){

                JSONObject jo = (JSONObject) ja.get(i);

                nameData = "Name: " + jo.get("name");
                deptData = "Department: " + jo.get("dept");
                uvData = "Studying at: " + jo.get("university");

            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}
