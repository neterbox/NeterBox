package com.neterbox.customadapter;

import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.neterbox.R;
import com.neterbox.jsonpojo.followerlist.Followerlist;
import com.neterbox.jsonpojo.followerlist.FollowerlistDatum;
import com.neterbox.jsonpojo.followingadd.FollowingDatum;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sejal on 1/5/2018.
 */

public class Followers_Adapter extends BaseAdapter {
    Activity activity;
    private LayoutInflater inflater;
    public Resources res;
    List<FollowerlistDatum> followerlistData;

    public Followers_Adapter(Activity activity, List<FollowerlistDatum> followerlistData) {

        this.activity = activity;
        this.followerlistData = followerlistData;
        inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return followerlistData.size();
    }

    @Override
    public Object getItem(int i) {
        return followerlistData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final MyViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.friendlistitem, viewGroup, false);
            viewHolder = new MyViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) convertView.getTag();
        }
        viewHolder.titem3.setText(followerlistData.get(i).getFollowingDetail().getName());
        viewHolder.textView3.setText(followerlistData.get(i).getFollowingDetail().getCompany());
        Glide.with(activity).load(followerlistData.get(i).getFollowingDetail().getProfilePic()).placeholder(R.drawable.dummy).into(viewHolder.ifriendprofile);
        return convertView;
    }

    static class MyViewHolder {
        CircleImageView ifriendprofile;
        TextView titem3, textView3;

        public MyViewHolder(View item) {
            titem3 = (TextView) item.findViewById(R.id.titem3);
            textView3 = (TextView) item.findViewById(R.id.textView3);
            ifriendprofile = (CircleImageView) item.findViewById(R.id.ifriendprofile);
        }
    }
}