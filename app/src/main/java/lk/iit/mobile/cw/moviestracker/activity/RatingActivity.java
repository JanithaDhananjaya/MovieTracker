package lk.iit.mobile.cw.moviestracker.activity;

import androidx.annotation.NonNull;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import lk.iit.mobile.cw.moviestracker.R;
import lk.iit.mobile.cw.moviestracker.data.MovieData;
import lk.iit.mobile.cw.moviestracker.model.Movie;

public class RatingActivity extends AppCompatActivity {

    private MovieData movieData;

    private String selectedMovie;

    private Button btn_find_in_imdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        btn_find_in_imdb = findViewById(R.id.btn_find_imdb);

        movieData = new MovieData(this);

        ArrayList<Movie> allMovies = movieData.getAllMovies();

        RecyclerView recyclerView = findViewById(R.id.rating_movies);
        RecyclerViewRatingAdapter adapter = new RecyclerViewRatingAdapter(this, allMovies);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void findInIMDB(View view){
        Log.i("dd", selectedMovie);
        Intent i = new Intent(getApplicationContext(), FindInIMDBActivity.class);

        i.putExtra("selectedMovie", selectedMovie);
        startActivity(i);
    }

    class RecyclerViewRatingAdapter extends RecyclerView.Adapter<RecyclerViewRatingAdapter.ViewHolder> {

        private ArrayList<Movie> movieList = new ArrayList<>();
        private Context context;

        public RecyclerViewRatingAdapter(Context context, ArrayList<Movie> movieList) {
            this.movieList = movieList;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_rating_movies, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.title.setText(movieList.get(position).getTitle());
            holder.director.setText(movieList.get(position).getDirector());

            holder.rbtn_select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedMovie = movieList.get(position).getTitle();
                }
            });
        }

        @Override
        public int getItemCount() {
            return movieList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView title;
            TextView director;
            RadioButton rbtn_select;
            LinearLayout rating_layout;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.rating_lbl_title);
                director = itemView.findViewById(R.id.rating_lbl_director);
                rbtn_select = itemView.findViewById(R.id.rbtn_select);
                rating_layout = itemView.findViewById(R.id.rating_layout);
            }
        }
    }
}