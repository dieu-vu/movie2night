package fi.mobiles13.movietonight;

import android.app.Activity;
import android.content.Context;
import android.renderscript.ScriptGroup;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.ContentHandler;

public class JSONParser {
    public static final String TAG = "MOVIE_DATA";
    //When the user signup
    public JSONObject makeRegisterJson(String username, String password, int age, String email) throws JSONException {

        JSONObject object = new JSONObject();

        object.put("username", username);
        object.put("password", password);
        object.put("age", age);
        object.put("email", email);
        object.put("searchHistory", "");
        return object;
    }

    //When the user login -> Remove?
    public JSONObject makeLoginJson(String username, String password) throws JSONException {

        JSONObject object = new JSONObject();

        object.put("userName", username);
        object.put("password", password);
        return object;
    }

    public static String getJsonFromAssets(Context context){
        Log.d(TAG, String.valueOf(context));
        String jsonString;
        try{
            //get AssetManager object
            // use method open to open a file in assets folder
            // AssetManager.open() method returns an InputStream object
            InputStream is = context.getAssets().open("sorted_data.json");
            Log.d(TAG, "json file received");
            //Use the method InputStream.read to read data into byte[] array
            int size = is.available();
            byte[] buffer = new byte[size];

            //then read the byte[] array into a String
            is.read(buffer);
            is.close();

            jsonString = new String(buffer,"UTF-8");
            Log.d(TAG, "Got Json string: " + jsonString);
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
        return jsonString;
    }







}
