package pgupta.virtualsock.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import pgupta.virtualsock.Facade.AuthTuple;
import pgupta.virtualsock.Facade.Callback;
import pgupta.virtualsock.Facade.Facade;
import pgupta.virtualsock.R;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d("START", "STARTING APP");

        Button btnSignUp, btnLogin, btnReset;
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnSignUp = (Button) findViewById(R.id.btn_sign_up);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            //TODO
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("***", "going to signup");
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();
                Facade.login(email, password, new Callback<AuthTuple>() {

                    @Override
                    public void accept(AuthTuple t) {
                        if (!t.getErrorMessage().isEmpty()) {
                            Toast.makeText(LoginActivity.this, t.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });

            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("***", "resetting password");
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
                finish();
            }
        });
    }
}
