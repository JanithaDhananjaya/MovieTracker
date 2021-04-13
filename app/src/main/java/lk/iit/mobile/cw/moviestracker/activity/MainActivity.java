package lk.iit.mobile.cw.moviestracker.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import lk.iit.mobile.cw.moviestracker.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void registerMovie(View view){
        Intent intent = new Intent(getApplicationContext(), RegisterMovie.class);
        view.getContext().startActivity(intent);
    }

    public void displayMovies(View view){
        Intent intent = new Intent(getApplicationContext(), DisplayMovies.class);
        view.getContext().startActivity(intent);
    }

    public void favouriteMovies(View view){
        Intent intent = new Intent(getApplicationContext(), FavouriteMovies.class);
        view.getContext().startActivity(intent);
    }

    public void editMovies(View view){
        Intent intent = new Intent(getApplicationContext(), EditMoviesList.class);
        view.getContext().startActivity(intent);
    }

    public void ratingMovies(View view){
        Intent intent = new Intent(getApplicationContext(), RatingActivity.class);
        view.getContext().startActivity(intent);
    }

    public void searchMovies(View view){
        Intent intent = new Intent(getApplicationContext(), SearchMovieActivity.class);
        view.getContext().startActivity(intent);
    }

}