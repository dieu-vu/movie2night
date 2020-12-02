package fi.mobiles13.movietonight;

import android.content.Context;
import android.content.SharedPreferences;

public class UserLocalStore {

    public static final String SP_NAME = "userDetails";
    public static final String KEY_NAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_AGE = "age";
    public static final String KEY_EMAIL = "email";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    //Save or write the data into shared preferences
    public void storeUserData (User user) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString(KEY_NAME, user.getUsername());
        spEditor.putString(KEY_PASSWORD, user.getPassword());
        spEditor.putInt(KEY_AGE, user.getAge());
        spEditor.putString(KEY_EMAIL, user.getEmail());
        spEditor.commit();
    }

    /**
     * Get the user data from data storage when the user login
     * @return
     */
    public User getLoginUser() {
        String username = userLocalDatabase.getString(KEY_NAME, "");
        String password = userLocalDatabase.getString(KEY_PASSWORD, "");
        //int age = userLocalDatabase.getInt("age", 0);
        //String email = userLocalDatabase.getString("email", "");

        //Create a new user
        User storedUser = new User(username, password);
        return storedUser;
    }

    //if user login, set it to be true
    public void setUserLogin(boolean loggedIn) {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }
}
