package com.neterbox.customadapter;

import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.neterbox.R;

import java.util.ArrayList;

/**
 * Created by DeLL on 05-01-2018.
 */

public class Playlist_Adapter extends BaseAdapter {

    Activity activity;
    String[] itemname;
    Integer[] imgid;
    private ArrayList data;
    private LayoutInflater inflater;
    public Resources res;

    public Playlist_Adapter(Activity a,String[] itemname, Integer[] imgid) {
        this.activity = a;
        this.itemname = itemname;
        this.imgid = imgid;
        inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return itemname.length;
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

        public TextView tplaylistitem;
        public ImageView iplaylistitem;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        Playlist_Adapter.ViewHolder holder;

        if (view == null) {
            v = inflater.inflate(R.layout.playlist_item, null);
            holder = new Playlist_Adapter.ViewHolder();
            holder.tplaylistitem = (TextView) v.findViewById(R.id.tplaylistitem);
            holder.iplaylistitem = (ImageView) v.findViewById(R.id.iplaylistitem);
//            holder.tplaylistitem2 = (TextView) v.findViewById(R.id.tplaylistitem2);
//            holder.iplaylistitem2 = (ImageView) v.findViewById(R.id.iplaylistitem2);
            v.setTag(holder);
        }
        else
            holder=(Playlist_Adapter.ViewHolder)v.getTag();

        holder.tplaylistitem.setText(itemname[i]);
        Glide.with(activity).load(imgid[i]).into(holder.iplaylistitem);

        return v;
    }
}
