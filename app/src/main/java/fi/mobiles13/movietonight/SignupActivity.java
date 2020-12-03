package fi.mobiles13.movietonight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class SignupActivity extends AppCompatActivity {

    Button btnRegister;
    EditText edtUsername, edtPassword, edtAge, edtEmail;
    UserLocalStore userLocalStore;
    JSONParser jsonParser;
    public static final String TAG = "USER_DATA";

    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "Preferences";
    public String usernameKey;


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
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        //userLocalStore = new UserLocalStore(this);

        //Save user data on shared preferences when user signs up
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                int age = Integer.parseInt(edtAge.getText().toString());
                String email = edtEmail.getText().toString();

                try {
                    JSONObject obj = jsonParser.makeRegisterJson(username, password, age, email);

                    Log.d(TAG, obj.toString());

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    usernameKey = username;
                    editor.putString(usernameKey, obj.toString());
                    editor.commit();

                    Log.d(TAG, "key: " + usernameKey + ", value: " + sharedPreferences.getString(usernameKey, ""));
                    Toast.makeText(SignupActivity.this, "Registered Successfully!", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}