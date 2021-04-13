package lk.iit.mobile.cw.moviestracker.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import lk.iit.mobile.cw.moviestracker.R;
import lk.iit.mobile.cw.moviestracker.data.MovieData;
import lk.iit.mobile.cw.moviestracker.model.Movie;

public class UpdateMovie extends AppCompatActivity {

    private EditText txt_title;
    private EditText txt_year;
    private EditText txt_director;
    private EditText txt_casts;
    private EditText txt_review;
    private RadioGroup fav_or_not_fav;
    private RadioButton fav_or_not_fav_RadioButton;
    private RadioButton fav;
    private RadioButton not_fav;
    private RatingBar ratingBar;
    private Button btn_update;

    private MovieData movieData;

    private String[] selectedMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_movie);

        txt_title = (EditText) findViewById(R.id.txt_title);
        txt_year = (EditText) findViewById(R.id.txt_year);
        txt_director = (EditText) findViewById(R.id.txt_director);
        txt_casts = (EditText) findViewById(R.id.txt_casts);
        txt_review = (EditText) findViewById(R.id.txt_review);
        fav_or_not_fav = (RadioGroup) findViewById(R.id.fav_or_not_fav);
        fav = (RadioButton) findViewById(R.id.fav);
        not_fav = (RadioButton) findViewById(R.id.not_fav);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        btn_update = (Button) findViewById(R.id.btn_update);

        movieData = new MovieData(this);

        Intent i = getIntent();
        String[] selectedMovie = i.getStringArrayExtra("selectedMovie");
        this.selectedMovie = selectedMovie;
        txt_title.setText(selectedMovie[1]);
        txt_year.setText(selectedMovie[2]);
        txt_director.setText(selectedMovie[3]);
        txt_casts.setText(selectedMovie[4]);
        ratingBar.setRating(Float.parseFloat(selectedMovie[5]));
        Log.i("Rating", selectedMovie[5]);
        txt_review.setText(selectedMovie[6]);
        if (selectedMovie[7].equals("0")) {
            not_fav.setChecked(true);
            fav.setChecked(false);
        } else {
            not_fav.setChecked(false);
            fav.setChecked(true);
        }
    }

    public void updateMovie(View v) {
        int selectedId = fav_or_not_fav.getCheckedRadioButtonId();
        fav_or_not_fav_RadioButton = (RadioButton) findViewById(selectedId);

        movieData.updateMovie(
                new Movie(
                        Integer.parseInt(selectedMovie[0]),
                        txt_title.getText().toString(),
                        txt_year.getText().toString(),
                        txt_director.getText().toString(),
                        txt_casts.getText().toString(),
                        String.valueOf(Math.round(ratingBar.getRating())),
                        txt_review.getText().toString(),
                        fav_or_not_fav_RadioButton.getText().equals("Favourite") ? 1 : 0
                )
        );

        Toast toast = Toast.makeText(getApplicationContext(), "Movie is updated...!", Toast.LENGTH_SHORT);
        toast.show();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        v.getContext().startActivity(intent);
    }
}