package com.neterbox;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.neterbox.customadapter.Friend_RequestList_Adapter;
import com.neterbox.customadapter.NearbyfriendAdapter;
import com.neterbox.jsonpojo.Decline_friend_request.DeclineFriendRequest;
import com.neterbox.jsonpojo.FriendRequestList.FriendRequestListDatum;
import com.neterbox.jsonpojo.FriendRequestList.FriendRequestListpojo;
import com.neterbox.jsonpojo.Login.Login;
import com.neterbox.jsonpojo.accept_friend_request.AcceptFriendRequest;
import com.neterbox.jsonpojo.accept_friend_request.AcceptFriendRequestDatum;
import com.neterbox.jsonpojo.near_by_friend.NearbyfriendDatum;
import com.neterbox.jsonpojo.register.RegistrationPojo;
import com.neterbox.retrofit.APIClient;
import com.neterbox.retrofit.APIInterface;
import com.neterbox.utils.Constants;
import com.neterbox.utils.Sessionmanager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendRequestList extends Activity {

    ListView onechat;

    List<FriendRequestListDatum> friendRequestListDatumList = new ArrayList<>();
    Activity activity;
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    Sessionmanager sessionmanager;
    private FriendRequestListDatum data;
    private String status, id;
    private int message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request_list);
        activity = this;
        this.friendRequestListDatumList = friendRequestListDatumList;
        idMappings();
        listener();
        Friend_RequestList_Adapter adapter = new Friend_RequestList_Adapter(this, friendRequestListDatumList);
        AlertDialog.Builder listview = null;
        FriendRequestListpojo(id);
//        AlertDialog.Builder builder = listview.setAdapter(adapter );
    }

    private void listener() {
        onechat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(FriendRequestList.this, FriendRequestList.class);
                it.putExtra("profile", (Serializable) friendRequestListDatumList.get(i));
                //it.putExtra("profile",(Serializable) response.body().getData().getReceiver());
                startActivity(it);
                finish();
            }
        });
    }

    public void FriendRequestList(String id) {

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(FriendRequestList.this, SearchGroupFriend.class);
        startActivity(i);
        finish();
    }

    private void idMappings() {
        onechat = (ListView) findViewById(R.id.onechat);

    }
////////////////Freiend REquest List

    public void FriendRequestListpojo(String id) {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Please Wait...");
        dialog.show();
        Call<FriendRequestListpojo> friendRequestListCall = apiInterface.friendRequestListpojocall(id);
        friendRequestListCall.enqueue(new Callback<FriendRequestListpojo>() {
            @Override
            public void onResponse(Call<FriendRequestListpojo> call, Response<FriendRequestListpojo> response) {
                dialog.dismiss();
                if (response.body().getStatus().equals("Success")) {
                    Intent i = new Intent(FriendRequestList.this, HomePage.class);
                    // i.putExtra("login_name",response.body().getData().getUser().getName());
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(FriendRequestList.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FriendRequestListpojo> call, Throwable t) {
                dialog.dismiss();
            }
        });

    }

    //////ACCEPT FRIENDREQUEST

    public void AcceptFriendRequest(String id)
    {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Please Wait...");
        dialog.show();
        Call<AcceptFriendRequest> acceptFriendRequestCall = apiInterface.acceptFriendRequestCall(id);
        acceptFriendRequestCall.enqueue(new Callback<AcceptFriendRequest>() {
            @Override
            public void onResponse(Call<AcceptFriendRequest> call, Response<AcceptFriendRequest> response) {
                dialog.dismiss();
                if (response.body().getStatus().equals("Success")){
                    Intent i = new Intent(FriendRequestList.this, HomePage.class);
                    // i.putExtra("login_name",response.body().getData().getUser().getName());
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(FriendRequestList.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AcceptFriendRequest> call, Throwable t) {


            }


        });

    }



    ////////DECLINE FRIENDREQUEST

    public void DeclineFriendRequest(String id)
    {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Please Wait...");
        dialog.show();
        Call<DeclineFriendRequest> declineFriendRequestCall = apiInterface.declineFriendRequestCall(id);
        declineFriendRequestCall.enqueue(new Callback<DeclineFriendRequest>() {
            @Override
            public void onResponse(Call<DeclineFriendRequest> call, Response<DeclineFriendRequest> response) {
                dialog.dismiss();
                if (response.body().getStatus().equals("Success")){
                    Intent i = new Intent(FriendRequestList.this, HomePage.class);
                    // i.putExtra("login_name",response.body().getData().getUser().getName());
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(FriendRequestList.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeclineFriendRequest> call, Throwable t) {
                dialog.dismiss();
            }
        });

    }

}

