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

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sejal on 1/5/2018.
 */

public class Search_Following_Adapter extends BaseAdapter {
    Activity activity;
    String[] itemname;
    Integer[] imgid;
    private LayoutInflater inflater;
    public Resources res;

    public Search_Following_Adapter(Activity a, String[] itemname, Integer[] imgid) {
        this.activity = a;
        this.itemname = itemname;
        this.imgid = imgid;

        inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return (itemname != null ? itemname.length : 0);
    }

    @Override
    public Object getItem(int i) {
        return itemname[i];
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
        viewHolder.titem2.setText(itemname[i]);
        viewHolder.textView2.setText(itemname[i]);
        Glide.with(activity).load(imgid[i]).into(viewHolder.ifollowingprofile);
        Glide.with(activity).load(imgid[i]).into(viewHolder.followingback);
        return view;
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

