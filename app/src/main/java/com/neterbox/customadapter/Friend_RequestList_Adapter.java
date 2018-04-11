package com.neterbox.customadapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.neterbox.R;
import com.neterbox.jsonpojo.Decline_friend_request.DeclineFriendRequest;
import com.neterbox.jsonpojo.friend_requestlist.FrndReqListModel;
import com.neterbox.jsonpojo.accept_friend_request.AcceptFriendRequest;
import com.neterbox.retrofit.APIClient;
import com.neterbox.retrofit.APIInterface;
import com.neterbox.utils.Helper;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sejal on 2/1/2018.
 */

public class Friend_RequestList_Adapter extends BaseAdapter {
    Activity activity;
    private LayoutInflater inflater;
    String id;
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    List<FrndReqListModel.Datum> friendRequestListData;

    public Friend_RequestList_Adapter(Activity a,List<FrndReqListModel.Datum> friendRequestListData) {
        this.activity = a;
        this.friendRequestListData=friendRequestListData;
        inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return friendRequestListData.size();
    }

    @Override
    public Object getItem(int i) {
        return friendRequestListData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final MyViewHolder viewHolder;

        if (view == null) {
            view = inflater.inflate(R.layout.friendrequestlistitem, viewGroup, false);
            viewHolder = new MyViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) view.getTag();
        }

        if(!(friendRequestListData.get(i).getSender().getName().equals("")))
        {
            viewHolder.friendrequestname.setText(friendRequestListData.get(i).getSender().getName());
        }
        if(!(friendRequestListData.get(i).getSender().getProfilePic().equals(""))){
            Glide.with(activity).load(friendRequestListData.get(i).getSender().getProfilePic()).placeholder(R.drawable.dummy).into(viewHolder.civiewfrequest);
        }
        viewHolder.bfriend_request_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = friendRequestListData.get(i).getFriend().getId();
                if (Helper.isConnectingToInternet(activity)) {
                    accept_request(id);
                    viewHolder.bfriend_request_accept.setEnabled(false);
                } else {
                    Helper.showToastMessage(activity, "No Internet Connection");
                }
            }
        });
        viewHolder.bfriend_request_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = friendRequestListData.get(i).getFriend().getId();
                if (Helper.isConnectingToInternet(activity)) {
                    decline_request(id);
                    viewHolder.bfriend_request_decline.setEnabled(false);
                } else {
                    Helper.showToastMessage(activity, "No Internet Connection");
                }
            }
        });
        return view;
    }
    static class MyViewHolder {
        CircleImageView civiewfrequest;
        TextView friendrequestname;
        LinearLayout lfrequets, lfriend_req_pname;
        public Button bfriend_request_accept, bfriend_request_decline;

        public MyViewHolder(View item) {
            civiewfrequest = (CircleImageView) item.findViewById(R.id.civiewfrequest);
            friendrequestname = (TextView) item.findViewById(R.id.friendrequestname);
            lfrequets = (LinearLayout) item.findViewById(R.id.lfrequets);
            lfriend_req_pname = (LinearLayout) item.findViewById(R.id.lfriend_req_pname);
            bfriend_request_accept = (Button) item.findViewById(R.id.bfriend_request_accept);
            bfriend_request_decline = (Button) item.findViewById(R.id.bfriend_request_decline);
        }
    }


    ////// TO BE ACCEPT REQUEST API CALLING

    public void accept_request(String id){
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Please Wait...");
        dialog.show();
        Call<AcceptFriendRequest> acceptfriend = apiInterface.AcceptFriendListtpojo(id);
        acceptfriend.enqueue(new Callback<AcceptFriendRequest>() {
            @Override
            public void onResponse(Call<AcceptFriendRequest> call, Response<AcceptFriendRequest> response) {
                if (response.body().getStatus().equals("Success")) {
                    dialog.dismiss();
                    Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    Toast.makeText(activity,"Please Try Again", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<AcceptFriendRequest> call, Throwable t) {
                dialog.dismiss();
            }
        });

    }

    /////// TO BE DECLINE REQUEST API CALLING

    public void decline_request(String id){
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Please Wait...");
        dialog.show();
        Call<DeclineFriendRequest> declinefriend = apiInterface.DeclineFriendListtpojo(id);
        declinefriend.enqueue(new Callback<DeclineFriendRequest>() {
            @Override
            public void onResponse(Call<DeclineFriendRequest> call, Response<DeclineFriendRequest> response) {

                if (response.body().getStatus().equals("Success")) {
                    dialog.dismiss();

                    Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    Toast.makeText(activity,"Please Try Again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeclineFriendRequest> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

}
