package com.neterbox;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.neterbox.customadapter.ContactsforoneChatAdapter;

public class Contactsforoneononechat extends AppCompatActivity {
    ListView lonechat;
    Button bgroupchat;
    ContactsforoneChatAdapter adapter;
    public Contactsforoneononechat Chat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactsforoneononechat);

        Chat=this;

        Resources res=getResources();
        lonechat=(ListView) findViewById(R.id.lonechat);

        adapter=new ContactsforoneChatAdapter(Chat);
        lonechat.setAdapter(adapter);
        bgroupchat=(Button)findViewById(R.id.bgroupchat);
        bgroupchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(Contactsforoneononechat.this,ContactsforChat.class);
                startActivity(it);
            }
        });
    }
}
