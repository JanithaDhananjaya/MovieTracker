package lk.iit.mobile.cw.moviestracker.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import lk.iit.mobile.cw.moviestracker.R;

public class FindInIMDBActivity extends AppCompatActivity {

    private TextView lbl_imdb_rating;
    private TextView lbl_movie_title;
    private TextView lbl_year;
    private ImageView movie_image;

    private String BASE_URL = "https://imdb-api.com/en/API/";

    private String url = null;

    private  InputStream is =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_in_i_m_d_b);

        lbl_imdb_rating = findViewById(R.id.lbl_imdb_rating);
        lbl_movie_title = findViewById(R.id.lbl_movie_title);
        lbl_year = findViewById(R.id.lbl_year);
        movie_image = findViewById(R.id.movie_image);

        Intent i = getIntent();
        final String selectedMovie = i.getStringExtra("selectedMovie");
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    String movieId = searchUsingMovieTitle(selectedMovie);
                    if (!movieId.isEmpty()){
                        getMovieRatingUsingID(movieId);
                    }
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();

    }

    public String searchUsingMovieTitle(String title) {

        Uri builtURI = Uri.parse(BASE_URL + "SearchTitle/k_6uikueg6/" + title).buildUpon()
                .build();
        HttpURLConnection conn = null;
        try {
            URL requestURL = new URL(builtURI.toString());
            conn = (HttpURLConnection) requestURL.openConnection();

            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            conn.connect();

             is = new BufferedInputStream(conn.getInputStream());
            String contentAsString = convertIsToString(is);


            JSONObject jsonObject = new JSONObject(contentAsString);
            JSONArray listOfMovies = (JSONArray) jsonObject.get("results");

            for (int i = 0; i < listOfMovies.length(); i++) {
                String movieTitle = ((JSONObject) listOfMovies.get(i)).get("title").toString();
                if (title.equals(movieTitle)) {
                    return ((JSONObject) listOfMovies.get(i)).get("id").toString();
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                conn.disconnect();
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        return null;
    }

    public void getMovieRatingUsingID(String id) {

        Uri builtURI = Uri.parse(BASE_URL + "Ratings/k_6uikueg6/" + id).buildUpon()
                .build();
        HttpURLConnection conn = null;
        try {
            URL requestURL = new URL(builtURI.toString());
            conn = (HttpURLConnection) requestURL.openConnection();

            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            conn.connect();

            InputStream is = new BufferedInputStream(conn.getInputStream());
            String contentAsString = convertIsToString(is);

            JSONObject jsonObject = new JSONObject(contentAsString);
            String ratings = jsonObject.get("imDb").toString();
            String year = jsonObject.get("year").toString();
            String title = jsonObject.get("title").toString();
            lbl_imdb_rating.setText(ratings);
            lbl_movie_title.setText(title);
            lbl_year.setText(year);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                conn.disconnect();
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    public String convertIsToString(InputStream inputStream)
            throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line + "\n");
        }
        if (builder.length() == 0) {
            return null;
        }
        return builder.toString();
    }

}
