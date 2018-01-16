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

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sejal on 1/5/2018.
 */

public class Followers_Adapter extends BaseAdapter {
    Activity activity;
    String[] itemname;
    Integer[] imgid;
    private ArrayList data;
    private LayoutInflater inflater;
    public Resources res;

    public Followers_Adapter(Activity a,String[] itemname, Integer[] imgid){
        this.activity=a;
        this.itemname = itemname;
        this.imgid = imgid;
        inflater=(LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
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
            view = inflater.inflate(R.layout.followerslistitem, viewGroup, false);
            viewHolder = new MyViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) view.getTag();
        }

        viewHolder.titem1.setText(itemname[i]);
        viewHolder.textView1.setText(itemname[i]);
        Glide.with(activity).load(imgid[i]).into(viewHolder.ifollowersprofile);
        Glide.with(activity).load(imgid[i]).into(viewHolder.followersback);
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
