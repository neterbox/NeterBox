package com.neterbox.customadapter;

import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.neterbox.R;
import com.neterbox.jsonpojo.followinglist.FollowinglistDatum;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sejal on 1/5/2018.
 */

public class Search_Following_Adapter extends BaseAdapter {
    Activity activity;
    private LayoutInflater inflater;
    public Resources res;
    List<FollowinglistDatum> followinglistData;

    public Search_Following_Adapter(Activity a,  List<FollowinglistDatum> followinglistData) {
        this.activity = a;
        this.followinglistData = followinglistData;

        inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return followinglistData.size();
    }

    @Override
    public Object getItem(int i) {
        return followinglistData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final MyViewHolder viewHolder;

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.followinglistitem,viewGroup, false);
            viewHolder = new MyViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (MyViewHolder) convertView.getTag();
        }

        viewHolder.titem2.setText(followinglistData.get(i).getFollowerDetail().getName());
        viewHolder.textView2.setText(followinglistData.get(i).getFollowerDetail().getCompany());
        Glide.with(activity).load(followinglistData.get(i).getFollowerDetail().getProfilePic()).placeholder(R.drawable.dummy).into(viewHolder.ifollowingprofile);
        return convertView;

    }

    class MyViewHolder {
            CircleImageView ifollowingprofile;
            TextView titem2, textView2;
            LinearLayout lsearchfollowingssprofile;
            ImageView followingback;

            public MyViewHolder(View item) {
                titem2 = (TextView) item.findViewById(R.id.titem2);
                textView2 = (TextView) item.findViewById(R.id.textView2);
                ifollowingprofile = (CircleImageView) item.findViewById(R.id.ifollowingprofile);
                lsearchfollowingssprofile = (LinearLayout) item.findViewById(R.id.lsearchfollowingssprofile);
                followingback = (ImageView) item.findViewById(R.id.followingback);

            }
        }
    }

