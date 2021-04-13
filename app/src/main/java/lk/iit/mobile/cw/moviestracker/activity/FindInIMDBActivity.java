package lk.iit.mobile.cw.moviestracker.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

import lk.iit.mobile.cw.moviestracker.R;

public class FindInIMDBActivity extends AppCompatActivity {

    private String urlDomain = "https://imdb-api.com/en/";
    private String apiVersion = "API/";
    private String routeName = "SearchTitle/";
    private String key = "k_6uikueg6";

    private String url = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_in_i_m_d_b);

        Intent i = getIntent();
        String selectedMovie = i.getStringExtra("selectedMovie");
        searchUsingMovieTitle(selectedMovie);
    }

    public void searchUsingMovieTitle(String title) {
        final String[] info = {""};
//        url = urlDomain + apiVersion + routeName + key + "/" + title;
        url = "https://imdb-api.com/en/API/SearchTitle/k_6uikueg6/Godzila vs. Kong";
        getIMDBInfo task = new getIMDBInfo();
        task.execute();
    }

    protected class getIMDBInfo extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String path = url;

            StringBuffer response = new StringBuffer();

            try {
                URL url = new URL(path);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setReadTimeout(10000 /* milliseconds */);
//                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-Type", "application/json");

                int responseCode = urlConnection.getResponseCode();

                Log.i("responseCode ", String.valueOf(responseCode));

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    Log.i("fkajhjfkasfhkjshfjkashf", " Inside if");
                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    response = new StringBuffer();
                    String output;
                    while ((output = in.readLine()) != null) {
                        response.append(output);
                    }
                    in.close();
//                    return response.toString();
                }else{
                    Log.i("klkl", "HTTP OK IS NO");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            return null;

            String responseText = response.toString();
            Log.d("dsada ", responseText);
            try {
                JSONArray jsonarray = new JSONArray(responseText);
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
//                    int id = jsonobject.getInt("id");
//                    String country = jsonobject.getString("countryName");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return responseText;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                Log.i("json", s);
//                JSONObject main = jsonObject.getJSONObject("main");
//                String temp = main.getString("temp");
//                String feels_like = main.getString("feels_like");
//                current_temp.setText(temp);
//                feel_like_temp.setText(feels_like);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}