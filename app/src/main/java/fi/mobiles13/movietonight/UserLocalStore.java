package fi.mobiles13.movietonight;

import android.content.Context;
import android.content.SharedPreferences;

public class UserLocalStore {

    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    //Save or write the data into shared preferences
    public void storeUserData (User user) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("username", user.getUsername());
        spEditor.putString("password", user.getPassword());
        spEditor.putInt("age", user.getAge());
        spEditor.putString("email", user.getEmail());
        spEditor.commit();
    }

    /**
     * Get the user data from data storage when the user login
     * @return
     */
    public User getLoginUser() {
        String username = userLocalDatabase.getString("username", "");
        String password = userLocalDatabase.getString("password", "");
        int age = userLocalDatabase.getInt("age", 0);
        String email = userLocalDatabase.getString("email", "");

        //Create a new user
        User storedUser = new User(username, password, age, email);
        return storedUser;
    }

    //if user login, set it to be true
    public void setUserLogin(boolean loggedIn) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }
}
