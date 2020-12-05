package fi.mobiles13.movietonight;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MovieUtils extends Application {
    private static final String ALL_MOVIES_KEY = "all_movies";

    private static MovieUtils instance;
    private SharedPreferences sharedPreferences;

    public static ArrayList<Movie> allMovies;

    private MovieUtils(Context context){
        sharedPreferences = context.getSharedPreferences("movie_data", Context.MODE_PRIVATE);
        //Check if shared preferences have any data, otherwise init data
        if (getAllMovies() == null){
            allMovies = new ArrayList<>();
            initData();
        }
    }


    public static MovieUtils getInstance(Context context){
        if (null != instance){
            return instance;
        } else {
            instance = new MovieUtils(context);
            return instance;
        }
    }

    private void initData(){
        //Add initial data from asset file
        ArrayList<Movie> movies = new ArrayList<>();

        //Read data from JSON in assets from here and append to list(100 lines)
        String jSonFileString;
        Context context = getApplicationContext();
        jSonFileString = JSONParser.getJsonFromAssets(context);
//        Log.i("data", jSonFileString);


    // Convert the original json data to create new movie objects:
        try {
            JSONObject movieJsonStr = new JSONObject(jSonFileString);
            JSONArray moviesJsonArray = movieJsonStr.getJSONArray("tconst");
//            ArrayList<HashMap<String, String>> movieList = new ArrayList<HashMap<String, String>>();
//            HashMap<String, String> movieEntry;

            for (int i = 0; i < moviesJsonArray.length(); i++) {
                JSONObject entry = moviesJsonArray.getJSONObject(i);
                String id = entry.getString("tconst");
                String name = entry.getString("primaryTitle:");
                String isAdult = entry.getString("isAdult");
                String year = entry.getString("startYear");
                String genre = entry.getString("genres");
                String rating = entry.getString("rating");
                String url = entry.getString("url");

                Movie movie = new Movie(id, name, genre, Boolean.valueOf(isAdult), Integer.valueOf(year), Double.valueOf(rating), url);
                movies.add(movie);

//                movieEntry = new HashMap<String, String>();
//                movieEntry.put("id", id);
//                movieEntry.put("name", name);
//                movieEntry.put("isAdult", isAdult);
//                movieEntry.put("year", year);
//                movieEntry.put("rating", rating);
//                movieEntry.put("genre", genre);
//
//                movieList.add(movieEntry);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }

        //Convert the array of Movie objects to Json and save into sharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(ALL_MOVIES_KEY, gson.toJson(movies));
        editor.apply();
    }

    public ArrayList<Movie> getAllMovies(){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Movie>>(){}.getType();
        ArrayList<Movie> movies = gson.fromJson(sharedPreferences.getString(ALL_MOVIES_KEY, null), type);
        return movies;
    }

    public Movie getMovieByID(String id){
        ArrayList<Movie> movies = getAllMovies();
        if (movies !=null){
            for (Movie m: movies){
                if (m.getId()== id){
                    return m;
                }
            }
        }
        return null;
    }

    public ArrayList<Movie> searchMovie (String searchStr, int age){
        ArrayList<Movie> movies = getAllMovies();
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
    //TODO: getFavMovie, clear history?
}
