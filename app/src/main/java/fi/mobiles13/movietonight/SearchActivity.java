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
import java.util.Arrays;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    EditText edtAge;
    int userAge;
    String currentUserName;
//    Log.d("BUTTON", findViewById(R.id.edtSearch));

    public static final String TAG = "USER_SEARCH";
    sharedPrefsWriter spw = new sharedPrefsWriter();
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Button searchBtn = (Button) findViewById(R.id.searchBtn);
        Log.d(TAG, "search activity starts");

        //Get user object from Login activity delivered by the Intent
        Bundle b = getIntent().getExtras();
        if(b != null) {
            //IF LOGGED IN:
            if (b.containsKey("user")){
                String userName = b.getString("user");
                try {
                    //Prefill age after user logged in:
                    SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
                    User currentUser = new User(userName, sharedPreferences);
                    edtAge = findViewById(R.id.edtAge);
                    Log.d(TAG, "user age: " + currentUser.getAge());
                    userAge = currentUser.getAge();
                    edtAge.setText(Integer.toString(currentUser.getAge()));

                    currentUserName = userName;

                    //Get and Show recent searches:
                    ArrayList<String> recentSearches = new ArrayList<String>();
                    String searchHistoryStr = currentUser.getSearchHistory();
                    //convert from search history as String to ArrayList<String>
                    ArrayList<String> searchHistory = new ArrayList<String>(Arrays.asList(searchHistoryStr.split(",")));
                    Log.d(TAG, "search history: " + searchHistory.toString());

                    //show max 5 recent searches, if less than 3 history records then show all
                    if (searchHistory.size() < 3 ) {
                        for (int i = searchHistory.size() - 1; i > 0; i--) {
                            recentSearches.add(searchHistory.get(i));
                        }
                    } else {
                        for (int i = searchHistory.size() - 1; i > searchHistory.size() - searchHistory.size()+1; i--) {
                            recentSearches.add(searchHistory.get(i));
                        }
                    }

                    //SHOW RECENT SEARCHES IN LISTVIEW:
                    ListView recentSearchLv = (ListView) findViewById(R.id.listSavedSearch);
                    Log.d("MOVIE_DATA", "listView created");
                    recentSearchLv.setAdapter(new ArrayAdapter<String>(
                            this,
                            R.layout.movie_item_layout,
                            recentSearches));
                    Log.d(TAG, recentSearches.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //IF CHOOSE CONTINUE AS GUEST:
            else if (b.containsKey("guest")) {
                edtAge = findViewById(R.id.edtAge);
                Toast.makeText(SearchActivity.this, "Please enter your Age", Toast.LENGTH_SHORT).show();
                updateAge();
            }
        }


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Handle search String and pass to MovieUtils searchMovie method
                EditText searchEdt = findViewById(R.id.edtSearch);
                String searchText = searchEdt.getText().toString();
                updateAge();
                Log.d(TAG, "check age before search: " + userAge);
                if (userAge != (int)userAge) {
                    Toast.makeText(SearchActivity.this, "Please enter your age", Toast.LENGTH_SHORT).show();
                    updateAge();
                    Log.d(TAG, String.valueOf(userAge));
                }

                if (searchText.isEmpty()) {
                    Toast.makeText(SearchActivity.this, "Please enter search texts", Toast.LENGTH_SHORT).show();

                } else {
                    Log.d(TAG,"current Age: " + userAge);
                    updateAge();
                    ArrayList<Movie> results = MovieUtils.getInstance(SearchActivity.this).searchMovie(searchText, userAge, SearchActivity.this);
                    Log.d(TAG, "search result" + results.toString());

                    //Update search History
                    if (b.containsKey("user")) {
                        try {
                            SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
                            spw.updateUserData(currentUserName, sharedPreferences, "searchHistory", searchText);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    //Convert search string to Json string and pass to Result Activity
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
        
    }

    public void updateAge(){
        edtAge = findViewById(R.id.edtAge);
        try{
            userAge = Integer.valueOf(edtAge.getText().toString());
        } catch (NumberFormatException e){
            Toast.makeText(SearchActivity.this, "Please enter your Age in number", Toast.LENGTH_SHORT).show();
            e.getStackTrace();
        }
    }
}