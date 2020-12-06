package fi.mobiles13.movietonight;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    public static final String TAG = "USER_SEARCH";
    GridView resultView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //Pass search result as a string
        Bundle b = getIntent().getExtras();
        TextView resultTitle = (TextView) findViewById(R.id.resultTitle);
        if(b != null) {
            String resultStr = b.getString("result");
            Log.d(TAG, resultStr);
            ArrayList<Movie> resultMovies = new ArrayList<Movie>();
            ArrayList<String> resultTitles = new ArrayList<String>();
            ArrayList<String> resultYears = new ArrayList<String>();
            ArrayList<String> resultRatings = new ArrayList<String>();
            ArrayList<String> resultUrls = new ArrayList<String>();


            try {
                JSONArray resultMoviesArray = new JSONArray(resultStr);
                Gson gson = new Gson();
                for (int i = 0; i < resultMoviesArray.length(); i++) {
                    Movie m = gson.fromJson(String.valueOf(resultMoviesArray.getJSONObject(i)), Movie.class);
                    Log.d(TAG,m.toString());
                    resultMovies.add(m);
                    resultTitles.add(m.getName());
                    resultYears.add(String.format("Year: %s", Integer.toString(m.getYear())));
                    resultRatings.add(String.format("Rating: %s", Double.toString(m.getRating())));
                    resultUrls.add(m.getUrl());
                }
                Log.d("USER_SEARCH", String.valueOf(resultMovies));
            } catch (JSONException e){
                e.getStackTrace();
            }

            if (resultMovies.size()>0) {
                resultTitle.setText("SEARCH RESULTS");
            } else {
                resultTitle.setText("Sorry, we found no results :( ");
            }


            resultView = (GridView) findViewById(R.id.gridView);
            GridViewAdapter gridAdapter = new GridViewAdapter(this, resultTitles, resultYears, resultRatings, resultUrls);
            Log.d("USER_SEARCH", "result gridview created");
            resultView.setAdapter(gridAdapter);
        }


    }
}