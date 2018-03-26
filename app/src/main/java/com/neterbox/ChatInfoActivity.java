package com.neterbox;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.neterbox.qb.BaseActivity;
import com.neterbox.qb.QbUsersHolder;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.users.model.QBUser;

import java.util.List;

public class ChatInfoActivity extends BaseActivity {

    private static final String EXTRA_DIALOG = "dialog";

    private ListView usersListView;
    private QBChatDialog qbDialog;

    public static void start(Context context, QBChatDialog qbDialog) {
        Intent intent = new Intent(context, ChatInfoActivity.class);
        intent.putExtra(EXTRA_DIALOG, qbDialog);
        context.startActivity(intent);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        qbDialog = (QBChatDialog) getIntent().getSerializableExtra(EXTRA_DIALOG);
        usersListView = _findViewById(R.id.list_login_users);

        actionBar.setDisplayHomeAsUpEnabled(true);

        buildUserList();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
    }

    private void buildUserList() {
        List<Integer> userIds = qbDialog.getOccupants();
        List<QBUser> users = QbUsersHolder.getInstance().getUsersByIds(userIds);

        //UsersAdapter adapter = new UsersAdapter(this, users);
       // usersListView.setAdapter(adapter);
    }

    @Override
    protected View getSnackbarAnchorView() {
        return usersListView;
    }

}
