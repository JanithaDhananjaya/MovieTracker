package lk.iit.mobile.cw.moviestracker.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import lk.iit.mobile.cw.moviestracker.model.Movie;

public class MovieData extends SQLiteOpenHelper {

    private static final String DB_NAME = "movietracker";
    private static final String TABLE_NAME = "movie";
    private static final int DB_VERSION = 1;

    private Context context;
    private SQLiteDatabase database;

    public MovieData(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT NOT NULL, " +
                "year INTEGER NOT NULL, " +
                "director TEXT NOT NULL, " +
                "casts TEXT NOT NULL, " +
                "rating INTEGER NOT NULL, " +
                "review TEXT NOT NULL, " +
                "favouriteStatus INTEGER" +
                ")");

        Log.i("Database Table", "Table created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public boolean registerMovie(Movie movie) {
        database = getWritableDatabase();
        String query = "INSERT INTO " + TABLE_NAME + " (title, year, director, casts, rating, review, favouriteStatus) VALUES (?,?,?,?,?,?,?);";
        database.execSQL(query, new String[]{
                movie.getTitle(),
                movie.getYear(),
                movie.getDirector(),
                movie.getCasts(),
                movie.getRating(),
                movie.getReview(),
                String.valueOf(movie.getFavouriteStatus())
        });
        database.close();
        return true;
    }

    public void updateMovie(Movie movie) {
        database = getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET title = ?, year = ?, director = ?, casts = ?, rating = ?, review = ?, favouriteStatus = ? WHERE _ID = ?;";
        database.execSQL(query, new String[]{
                movie.getTitle(),
                movie.getYear(),
                movie.getDirector(),
                movie.getCasts(),
                movie.getRating(),
                movie.getReview(),
                String.valueOf(movie.getFavouriteStatus()),
                String.valueOf(movie.getMovieId())
        });
        database.close();
    }

    public boolean markFavouriteMovies(ArrayList<Movie> favMovieList) {
        database = getWritableDatabase();

        for (Movie movie : favMovieList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("favouriteStatus", 1);
            database.update(TABLE_NAME, contentValues, "_ID = ?", new String[]{String.valueOf(movie.getMovieId())});
        }

        return true;
    }

    public boolean removeFromFavouriteList(ArrayList<Movie> removeMovieList) {
        database = getWritableDatabase();

        for (Movie movie : removeMovieList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("favouriteStatus", 0);
            database.update(TABLE_NAME, contentValues, "_ID = ?", new String[]{String.valueOf(movie.getMovieId())});
        }

        return true;
    }

    public ArrayList<Movie> getAllMovies() {
        database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        ArrayList<Movie> movieList = new ArrayList<>();

        while (cursor.moveToNext()) {
            movieList.add(
                    new Movie(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getInt(7)
                    ));
        }

        Log.i("All Movies", movieList.toString());
        return movieList;
    }

    public ArrayList<Movie> getAllFavouriteMovies() {
        database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE favouriteStatus =1", null);
        ArrayList<Movie> movieList = new ArrayList<>();
        while (cursor.moveToNext()) {
            movieList.add(
                    new Movie(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getInt(7)
                    ));
        }
        Log.i("Fav movie lsit", movieList.toString());
        return movieList;
    }

    public ArrayList<Movie> searchMovies(String keyword) {

        database = getReadableDatabase();
        ArrayList<Movie> movieList = new ArrayList<>();

        String[] parts = keyword.split(" ");

        String queryString = "";
        for (int i = 0; i < parts.length; i++) {
            queryString += "title" + " LIKE '%" + parts[i] + "%' OR ";
            queryString += "director" + " LIKE '%" + parts[i] + "%' OR ";
            queryString += "casts" + " LIKE '%" + parts[i] + "%'";
            if (i != (parts.length - 1)) {
                queryString += " OR ";
            }
        }

        Cursor cursor = database.query(true, TABLE_NAME,
                new String[]{"_ID", "title", "year", "director", "casts", "rating", "review", "favouriteStatus"},
                queryString, null, null, null, null, null);

        while (cursor.moveToNext()) {
            movieList.add(
                    new Movie(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getInt(7)
                    ));
        }

        return movieList;
    }
}
