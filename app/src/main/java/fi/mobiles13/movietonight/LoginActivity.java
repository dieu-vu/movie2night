package fi.mobiles13.movietonight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText eUsername;
    private EditText ePassword;
    private Button btnUserSignIn;
    private Button btnSignUp;
    private ImageView imgHome;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        eUsername = (EditText) findViewById(R.id.etUsername);
        ePassword = (EditText) findViewById(R.id.etPassword);
        btnUserSignIn = (Button) findViewById(R.id.btnUserSignIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        userLocalStore = new UserLocalStore(this);
        //When user click SignIn button

        btnUserSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userLocalStore != null) {
                    String inputName = eUsername.getText().toString();
                    String inputPassword = ePassword.getText().toString();

                    String username = userLocalStore.getLoginUser().username;
                    String password = userLocalStore.getLoginUser().password;

                    if(inputName.isEmpty() || inputPassword.isEmpty()) {
                        Toast.makeText(LoginActivity.this, "Please enter all details correctly ", Toast.LENGTH_SHORT).show();
                    }
                    if (inputName.equals(username) && inputPassword.equals(password)) {
                        //userLocalStore.setUserLogin(true);
                        Intent intent = new Intent(LoginActivity.this, SearchActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Password or Username is incorrect", Toast.LENGTH_SHORT).show();
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