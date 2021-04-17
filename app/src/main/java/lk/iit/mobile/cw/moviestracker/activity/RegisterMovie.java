package lk.iit.mobile.cw.moviestracker.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

        txt_rating.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (Integer.parseInt(txt_rating.getText().toString()) > 10) {
                    Toast.makeText(getApplicationContext(), "Rating should be in 1 - 10 range", Toast.LENGTH_SHORT).show();
                    txt_rating.getBackground().setColorFilter(getResources().getColor(R.color.errorColor), PorterDuff.Mode.SRC_ATOP);
                    return;
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        movieData = new MovieData(this);
    }

    public void saveMovie(View view) {
        String title = txt_title.getText().toString();
        if (title.isEmpty() || title == null) {
            Toast.makeText(getApplicationContext(), "Movie Title cannot be empty", Toast.LENGTH_SHORT).show();
            txt_title.getBackground().setColorFilter(getResources().getColor(R.color.errorColor), PorterDuff.Mode.SRC_ATOP);
            return;
        }

        String year = txt_year.getText().toString();
        if (year.isEmpty() || Integer.parseInt(year) < 1895) {
            Toast.makeText(getApplicationContext(), "Please enter a year after 1895", Toast.LENGTH_SHORT).show();
            txt_year.getBackground().setColorFilter(getResources().getColor(R.color.errorColor), PorterDuff.Mode.SRC_ATOP);
            return;
        }
        String director = txt_director.getText().toString();
        if (director.isEmpty() || director == null) {
            Toast.makeText(getApplicationContext(), "Director cannot be empty", Toast.LENGTH_SHORT).show();
            txt_director.getBackground().setColorFilter(getResources().getColor(R.color.errorColor), PorterDuff.Mode.SRC_ATOP);
            return;
        }
        String casts = txt_casts.getText().toString();
        String rating = txt_rating.getText().toString();
        if (rating.isEmpty() || Integer.parseInt(rating) > 11) {
            Toast.makeText(getApplicationContext(), "Rating should be in 1 - 10 range", Toast.LENGTH_SHORT).show();
            txt_rating.getBackground().setColorFilter(getResources().getColor(R.color.errorColor), PorterDuff.Mode.SRC_ATOP);
            return;
        }
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