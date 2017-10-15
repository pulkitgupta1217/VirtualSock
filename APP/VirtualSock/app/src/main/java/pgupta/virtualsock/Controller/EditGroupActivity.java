package pgupta.virtualsock.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import pgupta.virtualsock.R;

public class EditGroupActivity extends AppCompatActivity {

    Button btn_save, btn_cancel;
    ImageButton btn_addmember;
    EditText et_groupname;
    RecyclerView rv_memberList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);

        btn_save = (Button) findViewById(R.id.btn_saveGroup);
        btn_cancel = (Button) findViewById(R.id.btn_cancelgroupedit);
        btn_addmember = (ImageButton) findViewById(R.id.btn_addGroupmembers);
        et_groupname = (EditText) findViewById(R.id.et_groupName);
        rv_memberList = (RecyclerView) findViewById(R.id.rv_groupMembers);

        //todo: populate fields and functionality for buttons
    }
}
