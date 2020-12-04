package fi.mobiles13.movietonight;

public class Movie {
    private String name;
    private String genre;
    private boolean isAdult;
    private int year;
    private double rating;

    public Movie (String name, String genre, boolean isAdult, int year, double rating){
        this.name = name;
        this.genre = genre;
        this.isAdult = isAdult;
        this.year = year;
        this.rating = rating;
    }

}
