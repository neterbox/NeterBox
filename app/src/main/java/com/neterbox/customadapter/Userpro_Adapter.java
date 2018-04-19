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
import com.neterbox.jsonpojo.get_profile.GetProfilePostdetail;
import com.neterbox.retrofit.APIClient;
import com.neterbox.retrofit.APIInterface;
import com.neterbox.utils.Sessionmanager;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DeLL on 05-01-2018.
 */

public class Userpro_Adapter extends BaseAdapter {
    Activity activity;
    private ArrayList data;
    List<GetProfilePostdetail> getProfilePostdetails;
    private LayoutInflater inflater;
    public Resources res;
    Sessionmanager sessionmanager;
//    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    public Userpro_Adapter(Activity a, List<GetProfilePostdetail> getProfileDatumList) {
        this.activity = a;
        this.getProfilePostdetails = getProfileDatumList;
        sessionmanager = new Sessionmanager(activity);
    }

    @Override
    public int getCount() {
        return getProfilePostdetails.size();
    }

    @Override
    public Object getItem(int i) {
        return getProfilePostdetails.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public static class ViewHolder {
        public TextView tlistview_name, tlistview_seen, tlistview_time, tlistview_cap, tlistview_comment, tlistview_likes, tlistview_commentno;
        public ImageView ilistview_pic, ilistview_likes;
        public CircleImageView listview_profile;
        public LinearLayout llistview_comment, llistview_likes;
    }

    @Override
    public View getView(int i, View v, ViewGroup viewGroup) {

        ViewHolder holder;
        // inflate the layout for each list row

        v = LayoutInflater.from(activity).
                inflate(R.layout.profilelist_item, viewGroup, false);

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

        String datetime = getProfilePostdetails.get(i).getPostFile().get(0).getCreated();
        String[] separated = datetime.split(" ");
        String date = separated[0];
        String time = separated[1];

        holder.tlistview_time.setText(time);

        holder = (ViewHolder) v.getTag();
        if (!(getProfilePostdetails.get(i).getPostFile().equals(""))) {
            holder.tlistview_name.setText(sessionmanager.getValue(Sessionmanager.Name));
            Glide.with(activity).load(sessionmanager.getValue(Sessionmanager.profile)).placeholder(R.drawable.dummy).into(holder.listview_profile);
            holder.tlistview_cap.setText(getProfilePostdetails.get(i).getPost().getComments());
            Glide.with(activity).load(getProfilePostdetails.get(i).getPostFile().get(i).getFiles()).into(holder.ilistview_pic);
        }
        return v;
    }
}