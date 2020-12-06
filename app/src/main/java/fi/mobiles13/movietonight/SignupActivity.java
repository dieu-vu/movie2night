package fi.mobiles13.movietonight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import static fi.mobiles13.movietonight.LoginActivity.USER_DATA_KEY;

public class SignupActivity extends AppCompatActivity {

    Button btnRegister;
    EditText edtUsername, edtPassword, edtAge, edtEmail;
    JSONParser jsonParser;
    public static final String TAG = "USER_DATA";

    SharedPreferences sharedPreferences;
    public static final String USER_DATA_KEY = "user_data";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btnRegister = findViewById(R.id.btnsignup);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtAge = findViewById(R.id.edtAge);
        edtEmail = findViewById(R.id.edtEmail);

        jsonParser = new JSONParser();
        sharedPreferences = getSharedPreferences(USER_DATA_KEY, Context.MODE_PRIVATE);
        //userLocalStore = new UserLocalStore(this);

        //Save user data on shared preferences when user signs up
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                String ageStr = edtAge.getText().toString();
                String email = edtEmail.getText().toString();
                Log.d(TAG, username);

                //Require user to fill up all information
                if(username.isEmpty() || password.isEmpty() || ageStr.isEmpty() || email.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please enter required information correctly!", Toast.LENGTH_LONG).show();
                }
                else {
                    try {
                        int age = Integer.parseInt(ageStr);
                        JSONObject obj = jsonParser.makeRegisterJson(username, password, age, email);
                        Log.d(TAG, obj.toString());

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        //Set key as username, value is an object of a user
                        editor.putString(username, obj.toString());
                        editor.commit();

                        Log.d(TAG, "key: " + username + ", value: " + sharedPreferences.getString(username, ""));
                        Toast.makeText(SignupActivity.this, "Registered Successfully!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }
}