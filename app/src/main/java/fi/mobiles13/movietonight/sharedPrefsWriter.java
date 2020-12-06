package fi.mobiles13.movietonight;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static fi.mobiles13.movietonight.SignupActivity.USER_DATA_KEY;

public class sharedPrefsWriter extends Application {

    Context context;

    public static final String TAG = "CHECK_SP";

    public void updateUserData(String user, SharedPreferences sharedPreferences, String fieldName, String newData) throws JSONException {
        JSONObject obj = getData(user, sharedPreferences);
        switch (fieldName){
            case ("searchHistory"):
                String searchHistory;
                try {
                    searchHistory = obj.get(fieldName).toString();
                    searchHistory += ("," + newData);
                } catch (ClassCastException e) {
                    searchHistory = newData;
                }
                obj.remove(fieldName);
                obj.put(fieldName, searchHistory);
                break;
            case ("age"):
                obj.remove(fieldName);
                obj.put(fieldName,Integer.valueOf(newData));
                break;
            default:
                obj.remove(fieldName);
                obj.put(fieldName,newData);
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(user, obj.toString());
        editor.commit();
    }

    public JSONObject getData(String key, SharedPreferences sharedPreferences) {
        Log.d(TAG, USER_DATA_KEY);
        String userData = sharedPreferences.getString(key,"");
        Log.d(TAG, userData);
        try {
            JSONObject userObject = new JSONObject(userData);
            return userObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}