package lk.iit.mobile.cw.moviestracker.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import lk.iit.mobile.cw.moviestracker.R;
import lk.iit.mobile.cw.moviestracker.data.MovieData;
import lk.iit.mobile.cw.moviestracker.model.Movie;

public class FavouriteMovies extends AppCompatActivity {

    private MovieData movieData;

    private ArrayList<Movie> favMovieList;

    private ArrayList<Movie> removedSelectedFavMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_movies);

        movieData = new MovieData(this);
        favMovieList = movieData.getAllFavouriteMovies();
        removedSelectedFavMovies = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.fav_movies_list);
        RecyclerViewFavouriteAdapter adapter = new RecyclerViewFavouriteAdapter(this, favMovieList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void removeFromFavourite(View view) {
        boolean status = movieData.removeFromFavouriteList(removedSelectedFavMovies);
        if (status) {
            Toast.makeText(getApplicationContext(), removedSelectedFavMovies.size() + " Movies removed From Favourite List", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(getIntent());
        }
    }

    class RecyclerViewFavouriteAdapter extends RecyclerView.Adapter<RecyclerViewFavouriteAdapter.ViewHolder> {

        private ArrayList<Movie> movieList = new ArrayList<>();
        private Context context;

        public RecyclerViewFavouriteAdapter(Context context, ArrayList<Movie> movieList) {
            this.movieList = movieList;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_favourite_movies, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.lbl_fav_title.setText(movieList.get(position).getTitle());
            holder.lbl_fav_director.setText(movieList.get(position).getDirector());
            holder.chbx_remove_fav.setChecked(movieList.get(position).getFavouriteStatus() == 1 ? true : false);

            holder.chbx_remove_fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isChecked = ((CheckBox) v).isChecked();
                    Movie removeMovie = movieList.get(position);
                    if (!isChecked) {
                        if (!removedSelectedFavMovies.contains(removeMovie)) {
                            removedSelectedFavMovies.add(removeMovie);
                        }
                    } else {
                        removedSelectedFavMovies.remove(removeMovie);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return movieList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView lbl_fav_title;
            TextView lbl_fav_director;
            CheckBox chbx_remove_fav;
            LinearLayout favourite_parent_movies;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                lbl_fav_title = itemView.findViewById(R.id.lbl_fav_title);
                lbl_fav_director = itemView.findViewById(R.id.lbl_fav_director);
                chbx_remove_fav = itemView.findViewById(R.id.chbx_remove_fav);
                favourite_parent_movies = itemView.findViewById(R.id.favourite_parent_movies);
            }
        }
    }
}