package pgupta.virtualsock.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import pgupta.virtualsock.Facade.AuthTuple;
import pgupta.virtualsock.Facade.Callback;
import pgupta.virtualsock.Facade.Facade;
import pgupta.virtualsock.R;

public class SignUpActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    Button btnSignIn, btnSignUp, btnResetPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();

                Facade.signUp(email, password, new Callback<AuthTuple>() {

                    @Override
                    public void accept(AuthTuple t) {
                        if (!t.getErrorMessage().isEmpty()) {
                            Toast.makeText(SignUpActivity.this, t.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(SignUpActivity.this, MainActivity.Class));
                        }
                    }
                });
            }
        });
    }
}
