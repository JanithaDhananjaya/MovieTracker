package lk.iit.mobile.cw.moviestracker.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import lk.iit.mobile.cw.moviestracker.R;
import lk.iit.mobile.cw.moviestracker.data.MovieData;
import lk.iit.mobile.cw.moviestracker.model.Movie;

public class RegisterMovie extends AppCompatActivity {

    private EditText txt_title;
    private EditText txt_year;
    private EditText txt_director;
    private EditText txt_casts;
    private EditText txt_rating;
    private EditText txt_review;
    private Button btn_register;

    private MovieData movieData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_movie);

        txt_title = findViewById(R.id.txt_title);
        txt_year = findViewById(R.id.txt_year);
        txt_director = findViewById(R.id.txt_director);
        txt_casts = findViewById(R.id.txt_casts);
        txt_rating = findViewById(R.id.txt_rating);
        txt_review = findViewById(R.id.txt_review);
        btn_register = findViewById(R.id.btn_register);

        movieData = new MovieData(this);
    }

    public void saveMovie(View view) {
        String title = txt_title.getText().toString();
        String year = txt_year.getText().toString();
        String director = txt_director.getText().toString();
        String casts = txt_casts.getText().toString();
        String rating = txt_rating.getText().toString();
        String review = txt_review.getText().toString();

        boolean result = movieData.registerMovie(new Movie(
                title,
                year,
                director,
                casts,
                rating,
                review,
                0
        ));

        if (result) {
            Toast toast = Toast.makeText(getApplicationContext(), "Movie is registered successfully!", Toast.LENGTH_SHORT);
            toast.show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            view.getContext().startActivity(intent);
        }

    }

}