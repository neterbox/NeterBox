package com.neterbox.customadapter;

import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.neterbox.*;
import com.neterbox.R;
import com.neterbox.jsonpojo.FriendRequestList.FriendRequestListDatum;

import java.security.PublicKey;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sejal on 2/1/2018.
 */

public class Friend_RequestList_Adapter extends BaseAdapter {

    Activity activity;
    private LayoutInflater inflater;
    public Resources res;
    List<FriendRequestListDatum> friendRequestListDatumList;

    public Friend_RequestList_Adapter(Activity a, List<FriendRequestListDatum> friendRequestListDatumList) {
        this.activity = a;
        inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return friendRequestListDatumList.size();
    }

    @Override
    public Object getItem(int i) {
        return friendRequestListDatumList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Friend_RequestList_Adapter.MyViewHolder viewHolder;

        View convertView = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.friendlistitem, viewGroup, false);
            viewHolder = new Friend_RequestList_Adapter.MyViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Friend_RequestList_Adapter.MyViewHolder) convertView.getTag();
        }

        if(!(friendRequestListDatumList.get(i).getReceiver().getProfilePic().equals("")))
        {
            Glide.with(activity).load(friendRequestListDatumList.get(i).getReceiver().getProfilePic()).into(viewHolder.civiewfrequest);
        }
        if(!(friendRequestListDatumList.get(i).getReceiver().getUsername().equals("")))
        {
            viewHolder.friendrequestname.setText(friendRequestListDatumList.get(i).getReceiver().getUsername());
        }

        return convertView;
    }
    static class MyViewHolder {
            CircleImageView civiewfrequest;
            TextView friendrequestname;
            LinearLayout lfrequets, lfriend_req_pname;
            Button bfriend_request_accept, bfriend_request_decline;

            public MyViewHolder(View item) {
                civiewfrequest = (CircleImageView) item.findViewById(R.id.civiewfrequest);
                friendrequestname = (TextView) item.findViewById(R.id.friendrequestname);
                lfrequets = (LinearLayout) item.findViewById(R.id.lfrequets);
                lfriend_req_pname = (LinearLayout) item.findViewById(R.id.lfriend_req_pname);
                bfriend_request_accept = (Button) item.findViewById(R.id.bfriend_request_accept);
                bfriend_request_decline = (Button) item.findViewById(R.id.bfriend_request_decline);
            }
        }
}
