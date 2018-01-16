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
import android.widget.VideoView;

import com.neterbox.R;

import java.util.ArrayList;

/**
 * Created by DeLL on 08-01-2018.
 */

public class Playvideo_Adapter extends BaseAdapter {

    Activity activity;
    private ArrayList data;
    private LayoutInflater inflater;
    public Resources res;

    public Playvideo_Adapter(Activity a) {
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

        public LinearLayout lplayvideo_text;
        public TextView tplayvideo_text1,tplayvideo_text2,tplayvideo_text3;
        public VideoView vplayvideo;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        Playvideo_Adapter.ViewHolder holder;

        if (view == null) {
            v = inflater.inflate(R.layout.playvideo_item, null);
            holder = new Playvideo_Adapter.ViewHolder();
            holder.lplayvideo_text = (LinearLayout) v.findViewById(R.id.lplayvideo_text);
            holder.tplayvideo_text1 = (TextView) v.findViewById(R.id.tplayvideo_text1);
            holder.tplayvideo_text2 = (TextView) v.findViewById(R.id.tplayvideo_text2);
            holder.tplayvideo_text3 = (TextView) v.findViewById(R.id.tplayvideo_text3);
            holder.vplayvideo = (VideoView) v.findViewById(R.id.vplayvideo);

            v.setTag(holder);
        }
        else
            holder=(Playvideo_Adapter.ViewHolder)v.getTag();

        return v;
    }
}
