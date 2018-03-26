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
import com.neterbox.jsonpojo.following.FollowingDatum;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sejal on 1/5/2018.
 */

public class Followers_Adapter extends BaseAdapter {
    Activity activity;
    private ArrayList data;
    private LayoutInflater inflater;
    public Resources res;
    List<FollowingDatum> followingDatumList;

    public Followers_Adapter(Activity a,List<FollowingDatum> followingDatumList){
        this.activity=a;
       this.followingDatumList = followingDatumList;
        inflater=(LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return followingDatumList.size();
    }

    @Override
    public Object getItem(int i) {
        return followingDatumList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final MyViewHolder viewHolder;

        if (view == null) {
            view = inflater.inflate(R.layout.followerslistitem, viewGroup, false);
            viewHolder = new MyViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) view.getTag();
        }

        viewHolder.titem1.setText(followingDatumList.get(i).getFollowerDetail().getName());
        viewHolder.textView1.setText(followingDatumList.get(i).getFollowerDetail().getTitle());
        Glide.with(activity).load(followingDatumList.get(i).getFollowerDetail().getProfilePic()).into(viewHolder.ifollowersprofile);
        return view;
    }

    class MyViewHolder {
        public TextView titem1, textView1;
        public CircleImageView ifollowersprofile;
        public LinearLayout lsearchfollowersprofile;
        public ImageView followersback;

        public MyViewHolder(View item) {
            titem1 = (TextView) item.findViewById(R.id.titem1);
            textView1 = (TextView) item.findViewById(R.id.textView1);
            ifollowersprofile = (CircleImageView) item.findViewById(R.id.ifollowersprofile);
            lsearchfollowersprofile = (LinearLayout) item.findViewById(R.id.lsearchfollowersprofile);
            followersback = (ImageView) item.findViewById(R.id.followersback);

        }
    }
    }
