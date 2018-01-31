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

import com.neterbox.*;
import com.neterbox.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DeLL on 09-01-2018.
 */

public class Circle_imageAdapter extends BaseAdapter {
    Activity activity;
    private ArrayList data;
    private LayoutInflater inflater;
    public Resources res;


    public Circle_imageAdapter(Activity a) {
        this.activity = a;
        inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public static class ViewHolder {

        public TextView tlistview_name, tlistview_seen, tlistview_time, tlistview_cap, tlistview_comment, tlistview_likes, tlistview_commentno;
        public ImageView ilistview_pic,ilistview_likes;
        public CircleImageView listview_profile;
        public LinearLayout llistview_comment, llistview_likes,llistview_following,llistview_channel;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v=view;
        Circle_imageAdapter.ViewHolder holder;

        if (view == null) {
            v = inflater.inflate(R.layout.circle_imageitem, null);
            holder = new Circle_imageAdapter.ViewHolder();
            holder.tlistview_name = (TextView) v.findViewById(R.id.tlistview_name);
            holder.tlistview_seen = (TextView) v.findViewById(R.id.tlistview_seen);
            holder.tlistview_time = (TextView) v.findViewById(R.id.tlistview_time);
            holder.tlistview_cap = (TextView) v.findViewById(R.id.tlistview_cap);
            holder.tlistview_comment = (TextView) v.findViewById(R.id.tlistview_comment);
            holder.tlistview_likes = (TextView) v.findViewById(R.id.tlistview_likes);
            holder.tlistview_commentno = (TextView) v.findViewById(R.id.tlistview_commentno);
            holder.ilistview_pic = (ImageView) v.findViewById(R.id.ilistview_pic);
            holder.ilistview_likes = (ImageView) v.findViewById(R.id.ilistview_likes);
            holder.llistview_comment = (LinearLayout) v.findViewById(R.id.llistview_comment);
            holder.llistview_likes = (LinearLayout) v.findViewById(R.id.llistview_likes);
            holder.llistview_following = (LinearLayout) v.findViewById(R.id.llistview_following);
            holder.llistview_channel = (LinearLayout) v.findViewById(R.id.llistview_channel);
            holder.listview_profile = (CircleImageView) v.findViewById(R.id.listview_profile);
            v.setTag(holder);

        }
        else
            holder=(Circle_imageAdapter.ViewHolder)
                    v.getTag();

        return v;
    }
}
