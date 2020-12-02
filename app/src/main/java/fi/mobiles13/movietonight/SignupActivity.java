package fi.mobiles13.movietonight;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignupActivity extends AppCompatActivity {

    Button btnRegister;
    EditText edtUsername, edtPassword, edtAge, edtEmail;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btnRegister = findViewById(R.id.btnsignup);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtAge = findViewById(R.id.edtAge);
        edtEmail = findViewById(R.id.edtEmail);

        userLocalStore = new UserLocalStore(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                int age = Integer.parseInt(edtAge.getText().toString());
                String email = edtEmail.getText().toString();

                User signUpUser = new User(username, password, age, email);
                userLocalStore.storeUserData(signUpUser);
            }
        });
    }

}