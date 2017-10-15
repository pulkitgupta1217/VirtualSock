package pgupta.virtualsock.Controller;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import pgupta.virtualsock.Facade.Callback;
import pgupta.virtualsock.Facade.Facade;
import pgupta.virtualsock.R;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText inputEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        inputEmail = (EditText) findViewById(R.id.email);
        Button btnReset = (Button) findViewById(R.id.btn_reset_password);
        Button btnBack = (Button) findViewById(R.id.btn_back);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Enter your registered email id",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Facade.sendResetInstructions(inputEmail.getText().toString().trim(), new Callback<String>() {
                    public void accept(String s) {
                        Toast.makeText(ResetPasswordActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

}
