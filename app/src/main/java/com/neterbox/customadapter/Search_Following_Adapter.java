package com.neterbox.customadapter;

import android.app.Activity;
import android.content.res.Resources;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.neterbox.R;
import com.neterbox.jsonpojo.followerlist.FollowerlistDatum;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sejal on 1/5/2018.
 */

public class Search_Following_Adapter extends BaseAdapter {

    Activity activity;
    private LayoutInflater inflater;
    public Resources res;

    List<FollowerlistDatum> followerlistData;

    public Search_Following_Adapter(Activity activity, List<FollowerlistDatum> followerlistData) {
        this.activity=activity;
        this.followerlistData=followerlistData;
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
    public View getView(int i, View view, ViewGroup viewGroup) {

        final MyViewHolder viewHolder;

        if (view == null) {
            view = inflater.inflate(R.layout.followinglistitem, viewGroup, false);
            viewHolder = new MyViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) view.getTag();
        }

        viewHolder.titem2.setText(followerlistData.get(i).getFollowerDetail().getName());
        viewHolder.textView2.setText(followerlistData.get(i).getFollowerDetail().getCompany());
        Glide.with(activity).load(followerlistData.get(i).getFollowerDetail().getProfilePic()).placeholder(R.drawable.dummy).into(viewHolder.ifollowingprofile);

        return view;

//        viewHolder.titem2.setText(itemname[i]);
//        viewHolder.textView2.setText(itemname[i]);
//        Glide.with(activity).load(imgid[i]).into(viewHolder.ifollowingprofile);
//        Glide.with(activity).load(imgid[i]).into(viewHolder.followingback);
//        return view;
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
            }
        }
    }

