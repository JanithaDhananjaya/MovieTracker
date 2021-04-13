package lk.iit.mobile.cw.moviestracker.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import lk.iit.mobile.cw.moviestracker.R;
import lk.iit.mobile.cw.moviestracker.data.MovieData;
import lk.iit.mobile.cw.moviestracker.model.Movie;

public class EditMoviesList extends AppCompatActivity {

    private MovieData movieData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movies_list);

        movieData = new MovieData(this);

        ArrayList<Movie> allMovies = movieData.getAllMovies();

        RecyclerView recyclerView = findViewById(R.id.edit_movies_list);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, allMovies);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

        private ArrayList<Movie> movieList = new ArrayList<>();
        private Context context;

        public RecyclerViewAdapter(Context context, ArrayList<Movie> movieList) {
            this.movieList = movieList;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_edit_listitem, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.title.setText(movieList.get(position).getTitle());
            holder.director.setText(movieList.get(position).getDirector());

            holder.edit_parent_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(context.getApplicationContext(), UpdateMovie.class);
                    Movie selectedMovie = movieList.get(position);
                    String[] movieInfoArray = new String[]{
                            String.valueOf(selectedMovie.getMovieId()),
                            selectedMovie.getTitle(),
                            String.valueOf(selectedMovie.getYear()),
                            selectedMovie.getDirector(),
                            selectedMovie.getCasts(),
                            selectedMovie.getRating(),
                            selectedMovie.getReview(),
                            String.valueOf(selectedMovie.getFavouriteStatus())
                    };
                    i.putExtra("selectedMovie", movieInfoArray);
                    context.startActivity(i);
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
            LinearLayout edit_parent_layout;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.edit_lbl_title);
                director = itemView.findViewById(R.id.edit_lbl_director);
                edit_parent_layout = itemView.findViewById(R.id.edit_parent_layout);
            }
        }
    }
}

