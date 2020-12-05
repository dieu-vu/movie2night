package fi.mobiles13.movietonight;
import com.google.gson.Gson;

public class Movie {
    private String id;
    private String name;
    private String genre;
    private boolean isAdult;
    private int year;
    private double rating;
    private String url;

    public Movie (String id, String name, String genre, boolean isAdult, int year, double rating, String url){
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.isAdult = isAdult;
        this.year = year;
        this.rating = rating;
        this.url = url;
    }

    public String getName() {
        return this.name;
    }

    public int getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    public double getRating() {
        return rating;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public boolean isAdult() {
        return isAdult;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", isAdult=" + isAdult +
                ", year=" + year +
                ", rating=" + rating +
                '}';
    }
}
