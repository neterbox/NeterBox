package com.neterbox;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.neterbox.customadapter.ContactsforChatAdapter;

public class ContactsforChat extends AppCompatActivity{
    ListView groupchat;
    Button boneononechat;
    ImageView ileft,iright;
    TextView title;
    ContactsforChatAdapter adapter;
    public ContactsforChat contactsforChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactsfor_chat);

        contactsforChat=this;

        Resources res=getResources();
        groupchat=(ListView) findViewById(R.id.groupchat);
        ileft=(ImageView)findViewById(R.id.ileft);
        iright=(ImageView)findViewById(R.id.iright);
        title=(TextView)findViewById(R.id.title);
        ileft.setImageResource(R.drawable.home);
        iright.setImageResource(R.drawable.plus);
        title.setText("Contacts");


       adapter=new ContactsforChatAdapter(contactsforChat);
        groupchat.setAdapter(adapter);
        boneononechat=(Button)findViewById(R.id.boneononechat);
        boneononechat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent it=new Intent(ContactsforChat.this,ContactsforChat.class);
                startActivity(it);
                finish();
            }
        });

        ileft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(ContactsforChat.this,HomePage.class);
                startActivity(i);
                finish();
            }
        });
        iright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(ContactsforChat.this,Create_group.class);
                startActivity(i);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(ContactsforChat.this,HomePage.class);
        startActivity(i);
        finish();
    }
}
