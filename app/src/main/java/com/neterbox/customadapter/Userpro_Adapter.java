package com.neterbox.customadapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
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
import com.neterbox.jsonpojo.get_profile.GetProfile;
import com.neterbox.jsonpojo.get_profile.GetProfileDatum;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DeLL on 05-01-2018.
 */

public class Userpro_Adapter extends BaseAdapter {
    Activity activity;
    private ArrayList data;
    List<GetProfileDatum> getProfileDatumList = new ArrayList<>();
    private LayoutInflater inflater ;
    public Resources res;


    public Userpro_Adapter(Activity a,List<GetProfileDatum> getProfileDatumList) {
        this.activity = a;
        this.getProfileDatumList=getProfileDatumList;
        inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return getProfileDatumList.size();
    }

    @Override
    public Object getItem(int i) {
        return getProfileDatumList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public static class ViewHolder {

        public TextView tlistview_name, tlistview_seen, tlistview_time, tlistview_cap, tlistview_comment, tlistview_likes, tlistview_commentno;
        public ImageView ilistview_pic,ilistview_likes;
        public CircleImageView listview_profile;
        public LinearLayout llistview_comment, llistview_likes;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = view;
        ViewHolder holder;

        if (view == null) {
            v = inflater.inflate(R.layout.profilelist_item, null);
            holder = new ViewHolder();
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
            holder.listview_profile = (CircleImageView) v.findViewById(R.id.listview_profile);
            v.setTag(holder);
        }
        else
            holder=(ViewHolder)v.getTag();
        if(!(getProfileDatumList.get(i).getUser().getName().equals("")))
        {
            holder.tlistview_name.setText(getProfileDatumList.get(i).getUser().getName());
        }
        if(!(getProfileDatumList.get(i).getUser().getProfilePic().equals(""))){
            Glide.with(activity).load(getProfileDatumList.get(i).getUser().getProfilePic()).placeholder(R.drawable.dummy).into(holder.listview_profile);
        }
            return v;
        }
    }
