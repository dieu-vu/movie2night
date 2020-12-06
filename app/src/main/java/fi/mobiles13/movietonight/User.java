package fi.mobiles13.movietonight;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class User {

    String username, password, email;
    int age;
    SharedPreferences sharedPreferences;

    ArrayList<String> searchHistory;
    //constructor
    public User(String username, String password, int age, String email) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.email = email;
        this.searchHistory = new ArrayList<String>();
    }

    public User(String username, String password, int age, String email, ArrayList<String> searchHistory) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.email = email;
        this.searchHistory = searchHistory;
    }

    public User (String username, String password) {
        this.username = username;
        this.password = password;
        this.age = -1;
        this.email = null;
        this.searchHistory = new ArrayList<String>();
    }

    //User constructor which received only username and share preferences of the app - to receive user info in process in Log In and Search Activity
    public User (String username, SharedPreferences sharedPreferences) throws JSONException {
        try {
            JSONObject obj = new sharedPrefsWriter().getData(username, sharedPreferences);
            Log.d("USER_DATA", String.valueOf(obj));
            if (obj != null) {
                this.username = obj.getString("username");
                Log.d("USER_DATA", this.getUsername());
                this.password = obj.getString("password");
                Log.d("USER_DATA", this.getPassword());
                this.age = obj.getInt("age");
                Log.d("USER_DATA", Integer.toString(this.getAge()));
                this.email = obj.getString("email");
                Log.d("USER_DATA", this.getEmail());
                try {
                    ArrayList<String> searchHistory = (ArrayList<String>) obj.get("searchHistory");
                    this.searchHistory = searchHistory;
                }
                catch (Exception e) {
                    this.searchHistory = new ArrayList<String>();
                }
                Log.d("USER_DATA", String.valueOf(this.getSearchHistory()));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String getUsername() {
        return username;
    }

    public int getAge() {
        return age;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void addHistory(String entry){
        this.searchHistory.add(entry);
    }

    public ArrayList<String> getSearchHistory() {
        return this.searchHistory;
    }
}
