package pgupta.virtualsock.Controller;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import pgupta.virtualsock.Facade.Callback;
import pgupta.virtualsock.Facade.Facade;
import pgupta.virtualsock.R;

public class MainActivity extends AppCompatActivity {

    ImageButton btn_red, btn_yellow, btn_green, btn_notif, btn_home, btn_profile, btn_group;
    TextView tv_red, tv_yellow, tv_green;
    FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_red = (ImageButton) findViewById(R.id.btn_red);
        btn_yellow = (ImageButton) findViewById(R.id.btn_yellow);
        btn_green = (ImageButton) findViewById(R.id.btn_green);
        tv_red = (TextView) findViewById(R.id.tv_red_users);
        tv_yellow = (TextView) findViewById(R.id.tv_yellow_users);
        tv_green = (TextView) findViewById(R.id.tv_green_users);

        btn_notif = (ImageButton) findViewById(R.id.btn_notifications);
        btn_home = (ImageButton) findViewById(R.id.btn_home);
        btn_profile = (ImageButton) findViewById(R.id.btn_profile);
        btn_group = (ImageButton) findViewById(R.id.btn_group);

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        final Callback<List<String>[]> updateCallback = new Callback<List<String>[]>() {

            @Override
            public void accept(List<String>[] userSort) {
                //todo: deal with info
                if (userSort[0].size() > 0) {
                    btn_red.setImageResource(R.drawable.red_on_ic);
                    String users = "";
                    for (String usr: userSort[0]) {
                        users+= usr + ", ";
                    }
                    tv_red.setText(users);
                } else {
                    btn_red.setImageResource(R.drawable.red_off_ic);
                }

                if (userSort[1].size() > 0) {
                    btn_yellow.setImageResource(R.drawable.yellow_on_ic);
                    String users = "";
                    for (String usr: userSort[1]) {
                        users+= usr + ", ";
                    }
                    tv_yellow.setText(users);
                } else {
                    btn_yellow.setImageResource(R.drawable.yellow_off_ic);
                }

                if (userSort[2].size() > 0) {
                    btn_green.setImageResource(R.drawable.green_on_ic);
                    String users = "";
                    for (String usr: userSort[2]) {
                        users+= usr + ", ";
                    }
                    tv_green.setText(users);
                } else {
                    btn_green.setImageResource(R.drawable.green_off_ic);
                }
            }
        };
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {

                Facade.updateLights(updateCallback);
            }
        }, 5000);

        btn_red.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Facade.setLight("red", new Callback<Boolean>() {

                    @Override
                    public void accept(Boolean b) {
                        if (b) {
                            Facade.updateLights(updateCallback);
                        } else {
                            Toast.makeText(MainActivity.this, "Failed to Update", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        btn_yellow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Facade.setLight("yellow", new Callback<Boolean>() {

                    @Override
                    public void accept(Boolean b) {
                        if (b) {
                            Facade.updateLights(updateCallback);
                        }else {
                            Toast.makeText(MainActivity.this, "Failed to Update", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        btn_green.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Facade.setLight("green", new Callback<Boolean>() {

                    @Override
                    public void accept(Boolean b) {
                        if (b) {
                            Facade.updateLights(updateCallback);
                        }else {
                            Toast.makeText(MainActivity.this, "Failed to Update", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Facade.updateLights(updateCallback);
            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Facade.updateLights(updateCallback);
                Toast.makeText(MainActivity.this, "already at home page", Toast.LENGTH_SHORT).show();
            }
        });

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, EditProfileActivity.class);
                startActivity(i);
            }
        });

        btn_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, EditGroupActivity.class);
                startActivity(i);
            }
        });

        btn_notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NotificationActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(authListener);

    }
}
