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
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText eUsername;
    private EditText ePassword;
    private Button btnUserSignIn;
    private Button btnSignUp;
    private ImageView imgHome;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "Preferences";
    public static final String TAG = "USER_LOGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        eUsername = (EditText) findViewById(R.id.etUsername);
        ePassword = (EditText) findViewById(R.id.etPassword);
        btnUserSignIn = (Button) findViewById(R.id.btnUserSignIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        //When user click SignIn button
        btnUserSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get username and password from EditText
                String inputName = eUsername.getText().toString();
                String inputPassword = ePassword.getText().toString();

                //Ask username and password to be filled
                if(inputName.isEmpty() || inputPassword.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter all details correctly ", Toast.LENGTH_SHORT).show();
                }

                if(sharedPreferences.contains(inputName)) {
                    String userDetails = sharedPreferences.getString(inputName, "");
                    try {
                        JSONObject userObject = new JSONObject(userDetails);
                        String username = userObject.get("username").toString();
                        String password = userObject.get("password").toString();
                        int age = Integer.valueOf(userObject.get("age").toString());
                        Log.d(TAG, "name of the user log in : " + userObject.get("username").toString());
                        //validate if username and password are correct
                        if (inputName.equals(username) && inputPassword.equals(password)) {
                            //userLocalStore.setUserLogin(true);
                            Intent intent = new Intent(LoginActivity.this, SearchActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Password or Username is incorrect", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        });

        //when user click sign up button
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        //back to home screen
        imgHome = findViewById(R.id.imgHome);
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}