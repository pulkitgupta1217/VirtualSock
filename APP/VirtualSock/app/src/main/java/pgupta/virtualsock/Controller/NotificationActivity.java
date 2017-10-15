package pgupta.virtualsock.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import pgupta.virtualsock.R;

public class NotificationActivity extends AppCompatActivity {

    RecyclerView rv_notifs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        rv_notifs = (RecyclerView) findViewById(R.id.rv_notifs);


    }
}
