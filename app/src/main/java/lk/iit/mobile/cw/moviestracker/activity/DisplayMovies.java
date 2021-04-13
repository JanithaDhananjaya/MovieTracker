package lk.iit.mobile.cw.moviestracker.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
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

public class DisplayMovies extends AppCompatActivity {

    private Button btn_add_fav;

    private MovieData movieData;

    private ArrayList<Movie> allMovies;

    private ArrayList<Movie> selectedFavouriteMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movies);

        btn_add_fav = findViewById(R.id.btn_add_favourite);

        movieData = new MovieData(this);
        allMovies = movieData.getAllMovies();
        selectedFavouriteMovies = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.display_movies_list);
        RecyclerViewDisplayAdapter adapter = new RecyclerViewDisplayAdapter(this, allMovies);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void addToFavourite(View view) {
        Log.i("size ", String.valueOf(selectedFavouriteMovies.size()));
        Log.i("selected fav list ", selectedFavouriteMovies.toString());

        for (Movie movie : selectedFavouriteMovies) {
            movie.setFavouriteStatus(1);

            movieData.updateMovie(movie);
        }

    }

    class RecyclerViewDisplayAdapter extends RecyclerView.Adapter<RecyclerViewDisplayAdapter.ViewHolder> {

        private ArrayList<Movie> movieList = new ArrayList<>();
        private Context context;

        public RecyclerViewDisplayAdapter(Context context, ArrayList<Movie> movieList) {
            this.movieList = movieList;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_display_movies, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.lbl_display_title.setText(movieList.get(position).getTitle());
            holder.lbl_display_director.setText(movieList.get(position).getDirector());
            holder.favourite.setChecked(movieList.get(position).getFavouriteStatus() == 1 ? true : false);

            holder.favourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isChecked = ((CheckBox) v).isChecked();
                    Movie selectedMovie = movieList.get(position);
                    if (isChecked) {
                        if (!selectedFavouriteMovies.contains(selectedMovie)) {
                            selectedFavouriteMovies.add(selectedMovie);
                        }
                    } else {
                        selectedFavouriteMovies.remove(selectedMovie);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return movieList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView lbl_display_title;
            TextView lbl_display_director;
            CheckBox favourite;
            LinearLayout row_display_movies;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                lbl_display_title = itemView.findViewById(R.id.lbl_display_title);
                lbl_display_director = itemView.findViewById(R.id.lbl_display_director);
                favourite = itemView.findViewById(R.id.favourite);
                row_display_movies = itemView.findViewById(R.id.display_parent_movies);
            }
        }
    }
}