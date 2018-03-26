package com.neterbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.neterbox.customadapter.Friend_RequestList_Adapter;
import com.neterbox.jsonpojo.friend_requestlist.FrndReqListModel;
import com.neterbox.retrofit.APIClient;
import com.neterbox.retrofit.APIInterface;
import com.neterbox.utils.Sessionmanager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendRequestList extends Activity {

    ListView requestlist;
    List<FrndReqListModel.Datum> friendRequestListData;
    Activity activity;
    String loginuserid;
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    Sessionmanager sessionmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request_list);
        activity = this;

        idMappings();
        listener();
        sessionmanager = new Sessionmanager(this);
        loginuserid = sessionmanager.getValue(Sessionmanager.Id);
        friendreqpojo(loginuserid);
    }

    private void listener() {
        requestlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(FriendRequestList.this, FriendList.class);
                startActivity(it);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(FriendRequestList.this,SearchGroupFriend.class);
        startActivity(i);
        finish();
    }
    private void idMappings() {
        requestlist = (ListView) findViewById(R.id.requestlist);
    }
////////////////Freiend REquest List

    public void friendreqpojo(String loginuserid)
    {
        Call<FrndReqListModel> circleCall = apiInterface.friendRequestListpojocall(loginuserid);

        circleCall.enqueue(new Callback <FrndReqListModel>() {
            @Override
            public void onResponse(Call<FrndReqListModel> call, Response<FrndReqListModel> response) {
                if (response.body().getStatus().equals("Success")) {
//                    for(FriendRequestListDatum friendRequestListDatum :res.body().getData())
//                    {
//                        friendRequestListData.add(friendRequestListDatum);
//                    }
////                             friendRequestListData = res.body().getData();
                    friendRequestListData = new ArrayList<FrndReqListModel.Datum>();
                    friendRequestListData = response.body().getData();

                    Friend_RequestList_Adapter adapter = new Friend_RequestList_Adapter(activity, friendRequestListData);
                    requestlist.setAdapter(adapter);

                    Toast.makeText(activity,response.body().getMessage(), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(FriendRequestList.this,"problem", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<FrndReqListModel> call, Throwable t) {
                Toast.makeText(activity, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }





//    public void FriendRequestList(String loginuserid)
//    {
//        final ProgressDialog dialog = new ProgressDialog(activity);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setMessage("Please Wait...");
//        dialog.show();
//        final Call<FriendRequestListPojo> friendRequestListPojoCall = apiInterface.friendRequestListpojocall(loginuserid);
//        friendRequestListPojoCall.enqueue(new Callback<FriendRequestListPojo>() {
//            @Override
//            public void onResponse(Call<FriendRequestListPojo> call, Response<FriendRequestListPojo> response) {
//                if (response.body().getStatus().equals("Success")) {
//                    dialog.dismiss();
//                    friendRequestListData = response.body().getData();
//                    Friend_RequestList_Adapter adapter = new Friend_RequestList_Adapter(activity, friendRequestListData);
//                    requestlist.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
//                    Toast.makeText(FriendRequestList.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                } else {
//                    dialog.dismiss();
//                    Toast.makeText(FriendRequestList.this, "Try Again", Toast.LENGTH_SHORT).show();
//                }
//            }
//            @Override
//            public void onFailure(Call<FriendRequestListPojo> call, Throwable t) {
//                dialog.dismiss();
//            }
//        });
//    }

}