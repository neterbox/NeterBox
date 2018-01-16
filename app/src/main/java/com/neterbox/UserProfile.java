package com.neterbox;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.neterbox.customadapter.Userpro_Adapter;

public class UserProfile extends AppCompatActivity {

    ListView user_listview;
    Userpro_Adapter adapter;
    public  UserProfile userProfile ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        userProfile = this;

        Resources res =getResources();
        user_listview= ( ListView )findViewById( R.id.user_listview );

        adapter=new Userpro_Adapter(userProfile);
        user_listview.setAdapter( adapter );
    }
}

