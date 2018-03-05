package com.neterbox;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.neterbox.customadapter.Search_Friend_Adapter;
import com.neterbox.jsonpojo.friend_list.FriendListDatum;
import com.neterbox.jsonpojo.friend_list.FriendListPojo;
import com.neterbox.retrofit.APIClient;
import com.neterbox.retrofit.APIInterface;
import com.neterbox.utils.Helper;
import com.neterbox.utils.Sessionmanager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendList extends Activity {
    ListView listview;

    List<FriendListDatum> friendListData = new ArrayList<>();
    Activity activity;
    String login_id;
    ImageView ileft,iright;
    TextView title;
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    Sessionmanager sessionmanager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        activity = this;
        friendListData = new ArrayList<>();
        idMappings();
        listener();
        sessionmanager = new Sessionmanager(this);
        login_id = sessionmanager.getValue(Sessionmanager.Id);
        if(Helper.isConnectingToInternet(activity)){
            FriendList(login_id);
        }
        else {
            Helper.showToastMessage(activity,"No Internet Connection");
        }

    }

    private void listener() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Intent it =new Intent(FriendList.this,FreindProfile.class);
                it.putExtra("friendlist",(Serializable) friendListData.get(i));
                startActivity(it);
                finish();
            }
        });
        ileft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(FriendList.this,HomePage.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(FriendList.this,HomePage.class);
        startActivity(i);
        finish();
    }

    private void idMappings()
    {

        listview=(ListView)findViewById(R.id.listview);
        ileft=(ImageView)findViewById(R.id.ileft);
        iright=(ImageView)findViewById(R.id.iright);
        title=(TextView)findViewById(R.id.title);
        ileft.setImageResource(R.drawable.home);
        iright.setVisibility(View.INVISIBLE);
        title.setText("Friends");
    }

    public void FriendList(String login_id)
    {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Please Wait...");
        dialog.show();
        final Call<FriendListPojo> friendListPojoCall = apiInterface.friendlistpojo(login_id);
        friendListPojoCall.enqueue(new Callback<FriendListPojo>() {
            @Override
            public void onResponse(Call<FriendListPojo> call, Response<FriendListPojo> response) {
                if (response.body().getStatus().equals("Success")) {
                    dialog.dismiss();
                    friendListData = response.body().getData();
                    Search_Friend_Adapter adapter = new Search_Friend_Adapter(activity, friendListData);
                    listview.setAdapter(adapter);
//                    Intent i = new Intent(FriendRequestList.this, FriendListPojo.class);
//                    // i.putExtra("login_name",response.body().getData().getUser().getName());
//                    startActivity(i);
//                    finish();
                } else {
                    dialog.dismiss();
                    Toast.makeText(FriendList.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<FriendListPojo> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }
}
