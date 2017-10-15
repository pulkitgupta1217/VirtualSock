package pgupta.virtualsock.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import pgupta.virtualsock.Facade.Callback;
import pgupta.virtualsock.Facade.Facade;
import pgupta.virtualsock.R;

public class EditProfileActivity extends AppCompatActivity {

    ImageView profile;
    EditText et_name, et_username;
    Button save, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        profile = (ImageView) findViewById(R.id.profilePicture);
        save = (Button) findViewById(R.id.btn_save);
        cancel = (Button) findViewById(R.id.btn_Cancel);
        et_name = (EditText) findViewById(R.id.et_name);
        et_username  = (EditText) findViewById(R.id.et_username);

        final boolean showCancel = !savedInstanceState.getBoolean("newUser");
        if (!showCancel) {
            cancel.setVisibility(View.INVISIBLE);
        } else {
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        Facade.loadProfile(new Callback<Object[]>() {
            @Override
            public void accept(Object[] objects) {
                et_name.setText((String) objects[0]);
                et_username.setText((String) objects[1]);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();
                String name = et_name.getText().toString();
                Facade.updateProfile(name, username, new Callback<String>() {
                    @Override
                    public void accept(String s) {
                        if (!s.isEmpty()) {
                            Toast.makeText(EditProfileActivity.this, s, Toast.LENGTH_SHORT).show();
                        } else {
                            if (showCancel) {
                                finish();
                            } else {
                                Intent i = new Intent(EditProfileActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }
                    }
                });
            }
        });
    }
}
