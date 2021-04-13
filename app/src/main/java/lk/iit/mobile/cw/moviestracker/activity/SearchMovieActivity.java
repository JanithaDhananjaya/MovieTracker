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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import lk.iit.mobile.cw.moviestracker.R;
import lk.iit.mobile.cw.moviestracker.data.MovieData;
import lk.iit.mobile.cw.moviestracker.model.Movie;

public class SearchMovieActivity extends AppCompatActivity {

    private MovieData movieData;

    private EditText txt_search;

    private RecyclerView recyclerView;
    private RecyclerViewSearchAdapter recyclerViewSearchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);

        txt_search = findViewById(R.id.txt_search);

        movieData = new MovieData(this);

        this.recyclerView = findViewById(R.id.search_movie_list);
    }

    public void lookUphMovies(View view) {
        String keyword = txt_search.getText().toString();
        if (keyword!=null || !keyword.isEmpty()){
            ArrayList<Movie> movies = movieData.searchMovies(keyword);
            if (movies.size()>0){
                this.recyclerViewSearchAdapter = new RecyclerViewSearchAdapter(this, movies);
                recyclerView.setAdapter(recyclerViewSearchAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            }else{
                Toast.makeText(this, "No movies found...!", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Search name cannot be empty...", Toast.LENGTH_SHORT).show();
        }




    }

    class RecyclerViewSearchAdapter extends RecyclerView.Adapter<RecyclerViewSearchAdapter.ViewHolder> {

        private ArrayList<Movie> movieList = new ArrayList<>();
        private Context context;

        public RecyclerViewSearchAdapter(Context context, ArrayList<Movie> movieList) {
            this.movieList = movieList;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search_movies, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.search_lbl_title.setText(movieList.get(position).getTitle());
            holder.search_lbl_director.setText(movieList.get(position).getDirector());

            holder.search_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    Intent i = new Intent(context.getApplicationContext(), UpdateMovie.class);
//                    Movie selectedMovie = movieList.get(position);
//                    String[] movieInfoArray = new String[]{
//                            String.valueOf(selectedMovie.getMovieId()),
//                            selectedMovie.getTitle(),
//                            String.valueOf(selectedMovie.getYear()),
//                            selectedMovie.getDirector(),
//                            selectedMovie.getCasts(),
//                            selectedMovie.getRating(),
//                            selectedMovie.getReview(),
//                            String.valueOf(selectedMovie.getFavouriteStatus())
//                    };
//                    i.putExtra("selectedMovie", movieInfoArray);
//                    context.startActivity(i);
                }
            });
        }

        @Override
        public int getItemCount() {
            return movieList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView search_lbl_title;
            TextView search_lbl_director;
            LinearLayout search_layout;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                search_lbl_title = itemView.findViewById(R.id.search_lbl_title);
                search_lbl_director = itemView.findViewById(R.id.search_lbl_director);
                search_layout = itemView.findViewById(R.id.search_layout);
            }
        }
    }

}