package fi.mobiles13.movietonight;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;


import static fi.mobiles13.movietonight.LoginActivity.USER_DATA_KEY;


//Singleton to handle all Movie object and Movie data
public class MovieUtils extends Application {
    private static final String ALL_MOVIES_KEY = "all_movies";
    private static final String TAG = "MOVIE_DATA";
    private static MovieUtils instance = null;
    private SharedPreferences sharedPreferences;
    private Context context;
    public static ArrayList<Movie> allMovies;
    private User user;


    //Initiate object - constructor
    private MovieUtils(Context context, SharedPreferences sharedPreferences){
        //user shared preferences to call data from movie_data in the context
        sharedPreferences = context.getSharedPreferences("movie_data", Context.MODE_PRIVATE);
        Log.d(TAG, "MovieUtils initialized");
        //Check if shared preferences have any data, otherwise init data
        if (getAllMovies(context).size() == 0){
            allMovies = new ArrayList<>();
            initData(context); //if the device doesn't have the shared prefs movie_data.xml yet, then init the data
        }
    }


    public static MovieUtils getInstance(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("movie_data", Context.MODE_PRIVATE);
        if (null != instance){
            return instance;
        } else {
            instance = new MovieUtils(context, sharedPreferences);
            return instance;
        }
    }


    public void initData(Context context){
        //Add initial data from asset file
        ArrayList<Movie> movies = new ArrayList<>();
        Log.d(TAG, "initData called");
        //Read data from JSON in assets from here and append to list(100 lines)
        String jSonFileString;
        Log.d(TAG, String.valueOf(context));
        jSonFileString = JSONParser.getJsonFromAssets(context);
        Log.d(TAG, "movie data read in: " + jSonFileString);

    // Convert the original json data to create new movie objects:
        try {
            Log.d(TAG, "Checking json object");
            Gson g = new Gson();
            JSONArray moviesJsonArray = new JSONArray(jSonFileString);
            Log.d(TAG, String.valueOf(moviesJsonArray));

            for (int i = 0; i < moviesJsonArray.length(); i++) {
                JSONObject entry = moviesJsonArray.getJSONObject(i);
                String id = entry.getString("tconst");
                String name = entry.getString("primaryTitle");
                String isAdult = entry.getString("isAdult");
                String year = entry.getString("startYear");
                String genre = entry.getString("genres");
                String rating = entry.getString("rating");
                String url = entry.getString("url");
                Log.d(TAG, id+name+isAdult+year+genre+rating+url);
                Movie movie = new Movie(id, name, genre, Boolean.valueOf(isAdult), Integer.valueOf(year), Double.valueOf(rating), url);
                Log.d(TAG, String.valueOf(movie));
                movies.add(movie);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        Log.d(TAG, movies.toString());

        //Convert the array of all Movie objects to Json and save into sharedPreferences movie_data.xml
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(ALL_MOVIES_KEY, gson.toJson(movies));
        editor.apply();
        Log.d(TAG, "movie data written");
    }

    //METHOD TO GET ALL MOVIE DATA AS AN ARRAY OF MOVIE OBJECTS
    public ArrayList<Movie> getAllMovies(Context context){
        sharedPreferences = context.getSharedPreferences("movie_data", Context.MODE_PRIVATE);
        Log.d(TAG, "sharedPreferences" + sharedPreferences);
        if (sharedPreferences.contains(ALL_MOVIES_KEY)){
            //transfer array to Movie objects
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Movie>>(){}.getType();
            ArrayList<Movie> movies = gson.fromJson(sharedPreferences.getString(ALL_MOVIES_KEY, null), type);
            Log.d(TAG, "Movies: " + movies);
            Log.d(TAG, "list of Movie objects created in MovieUtils class");
            return movies;
        } else {
            Log.d(TAG, "cannot find Movie data");
        }
        return new ArrayList<Movie>();
    }


    // METHOD TO GET TOP RATED MOVIES
    public ArrayList<String> getTopRated(Context context){
        ArrayList<String> topRated = new ArrayList<>();
        for (int i=0; i<5;i++){
            topRated.add(getAllMovies(context).get(i).getName());
        }
        Log.d(TAG, topRated.toString());
        return topRated;
    }


    public Movie getMovieByID(String id, Context context){
        ArrayList<Movie> movies = getAllMovies(context);
        if (movies !=null){
            for (Movie m: movies){
                if (m.getId()== id){
                    return m;
                }
            }
        }
        return null;
    }


    //METHOD TO SEARCH MOVIE BASED ON INPUT SEARCH STRINGS:
    public ArrayList<Movie> searchMovie (String searchStr, int age, Context context){
        ArrayList<Movie> movies = getAllMovies(context);
        ArrayList<Movie> resultMovies = new ArrayList<>();
        if (null != movies){
            for (Movie m: movies){
                if (age > 17){
                    if (checkContain(m.getName(), searchStr) || checkContain(m.getGenre(),searchStr) || m.isAdult()) {
                        resultMovies.add(m);
                    }
                } else {
                    if (checkContain(m.getName(), searchStr) || checkContain(m.getGenre(),searchStr) || !(m.isAdult())) {
                        resultMovies.add(m);
                    }
                }
            }
            return resultMovies;
        }
        return null;
    }

     private boolean checkContain(String str, String subStr){
        return str.toLowerCase().contains(subStr.toLowerCase());
     }
}
