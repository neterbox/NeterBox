package com.neterbox.customadapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.neterbox.R;
import com.neterbox.jsonpojo.friend_list.FriendListDatum;
import com.neterbox.retrofit.APIClient;
import com.neterbox.retrofit.APIInterface;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DeLL on 05-01-2018.
 */

public class Friendpro_Adapter extends BaseAdapter {
    Activity activity;
    private LayoutInflater inflater;
    public Resources res;
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    public Friendpro_Adapter(Activity a) {
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
    public static class ViewHolder{
        CircleImageView listview_profile;
        TextView tlistview_name, tlistview_seen;
        ImageView ilistview_pic;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View v=view;
        ViewHolder holder;

        if (view == null) {
            v = inflater.inflate(R.layout.profilelist_item, null);
            holder = new ViewHolder();
            holder.tlistview_name = (TextView) v.findViewById(com.neterbox.R.id.tlistview_name);
            holder.tlistview_seen = (TextView) v.findViewById(com.neterbox.R.id.tlistview_seen);
            holder.listview_profile = (CircleImageView) v.findViewById(com.neterbox.R.id.listview_profile);
            holder.ilistview_pic = (ImageView) v.findViewById(com.neterbox.R.id.ilistview_pic);
            v.setTag(holder);
        }
        else
            holder=(ViewHolder)v.getTag();
        return v;
    }

}

