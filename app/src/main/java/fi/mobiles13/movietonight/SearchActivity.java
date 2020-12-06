package fi.mobiles13.movietonight;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    EditText edtAge;
    int userAge;
//    Log.d("BUTTON", findViewById(R.id.edtSearch));

    public static final String TAG = "USER_SEARCH";


    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Button searchBtn = (Button) findViewById(R.id.searchBtn);
        //Get user object from Login activity delivered by the Intent
        Bundle b = getIntent().getExtras();
        if(b != null) {
            String userName = b.getString("user");
            try {
                SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
                User currentUser = new User(userName, sharedPreferences);
                edtAge = findViewById(R.id.edtAge);
                Log.d(TAG, "user age: " + currentUser.getAge());
                userAge = currentUser.getAge();
                edtAge.setText("Age: " + Integer.toString(currentUser.getAge()));
            } catch (JSONException e){
                e.printStackTrace();
            }
        }


        //Handle search String and pass to MovieUtils searchMovie method

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchEdt = findViewById(R.id.edtSearch);
                String searchText = searchEdt.getText().toString();
                if (searchText.isEmpty()){
                    Toast.makeText(SearchActivity.this, "Please enter search texts", Toast.LENGTH_SHORT).show();
                } else {
                    ArrayList<Movie> results = MovieUtils.getInstance(SearchActivity.this).searchMovie(searchText, userAge, SearchActivity.this);
                    Log.d(TAG, results.toString());
                    Gson gson = new Gson();

                    String resultStr = gson.toJson(results);
                    Log.d(TAG,"result json: " + resultStr);
                    Intent intent = new Intent(SearchActivity.this, ResultActivity.class);
                    //put user object to the next activity
                    intent.putExtra("result", resultStr);
                    startActivity(intent);
                }
            }
        });



        //Set list view of top rated movies
        if (MovieUtils.getInstance(this) != null) {
            ListView topRated = (ListView) findViewById(R.id.listTopRated);
            Log.d("MOVIE_DATA", "listView created");
            topRated.setAdapter(new ArrayAdapter<String>(
                    this,
                    R.layout.movie_item_layout,
                    MovieUtils.getInstance(this).getTopRated(this))
            );
        } else {
            MovieUtils.getInstance(this).initData(this);
            Log.d("MOVIE_DATA", "init data");
        }
        //Set list view of saved Searches if user logged in



    }
}