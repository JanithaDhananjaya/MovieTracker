package lk.iit.mobile.cw.moviestracker.model;

public class Movie {
    private int movieId;
    private String title;
    private String year;
    private String director;
    private String casts;
    private String rating;
    private String review;
    private int favouriteStatus;

    public Movie() {
    }

    public Movie(String title, String year, String director, String casts, String rating, String review, int favouriteStatus) {
        this.movieId = movieId;
        this.title = title;
        this.year = year;
        this.director = director;
        this.casts = casts;
        this.rating = rating;
        this.review = review;
        this.favouriteStatus = favouriteStatus;
    }

    public Movie(int movieId, String title, String year, String director, String casts, String rating, String review, int favouriteStatus) {
        this.movieId = movieId;
        this.title = title;
        this.year = year;
        this.director = director;
        this.casts = casts;
        this.rating = rating;
        this.review = review;
        this.favouriteStatus = favouriteStatus;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCasts() {
        return casts;
    }

    public void setCasts(String casts) {
        this.casts = casts;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getFavouriteStatus() {
        return favouriteStatus;
    }

    public void setFavouriteStatus(int favouriteStatus) {
        this.favouriteStatus = favouriteStatus;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieId=" + movieId +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", director='" + director + '\'' +
                ", casts='" + casts + '\'' +
                ", rating=" + rating +
                ", review='" + review + '\'' +
                ", favouriteStatus=" + favouriteStatus +
                '}';
    }
}
