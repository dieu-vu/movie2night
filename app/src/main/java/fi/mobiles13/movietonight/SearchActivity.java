package fi.mobiles13.movietonight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class SearchActivity extends AppCompatActivity {

    EditText edtAge;
    public static final String TAG = "USER_SEARCH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Get user object from Login activity delivered by the Intent
        Bundle b = getIntent().getExtras();
        if(b != null) {
            String user = b.getString("user");

            edtAge = findViewById(R.id.edtAge);
            try {
                JSONObject userObject = new JSONObject(user);
                int userAge = Integer.valueOf(userObject.get("age").toString());
                Log.d(TAG, "user age: " + userAge);
                edtAge.setText(Integer.toString(userAge));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}