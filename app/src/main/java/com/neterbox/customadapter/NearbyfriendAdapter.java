package com.neterbox.customadapter;

import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.neterbox.*;
import com.neterbox.R;
import com.neterbox.jsonpojo.near_by_friend.NearbyfriendDatum;
import com.neterbox.utils.Sessionmanager;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.neterbox.R.id.ifriendprofile;
import static com.neterbox.R.id.profile_image;

/**
 * Created by DeLL on 30-01-2018.
 */

public class NearbyfriendAdapter extends BaseAdapter{
    Activity activity;
    LinearLayout lfrndprofile;
    private LayoutInflater inflater;
    public Resources res;

    List<NearbyfriendDatum> nearbyList;

    public NearbyfriendAdapter(Activity activity, List<NearbyfriendDatum> nearbyList) {

        this.activity=activity;
        this.nearbyList=nearbyList;

        inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount()
        {
//            return (itemname != null ? itemname.length : 0);
            return nearbyList.size();
        }

    @Override
    public Object getItem(int i) {
        return nearbyList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final MyViewHolder viewHolder;

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.friendlistitem,viewGroup, false);
            viewHolder = new MyViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (MyViewHolder) convertView.getTag();
        }

        if(!(nearbyList.get(i).getUsers().getName().equals("")))
        {
            viewHolder.titem3.setText(nearbyList.get(i).getUsers().getName());
        }
        if(!(nearbyList.get(i).getUsers().getCompany().equals("")))
        {
            viewHolder.textView3.setText(nearbyList.get(i).getUsers().getCompany());
        }
        if(!(nearbyList.get(i).getUsers().getProfilePic().equals("")))
        {
            Glide.with(activity).load(nearbyList.get(i).getUsers().getProfilePic()).into(viewHolder.ifriendprofile);
        }
       else{
            Glide.with(activity).load(nearbyList.get(i).getUsers().getProfilePic()).placeholder(R.drawable.dummy).into(viewHolder.ifriendprofile);
        }
        return convertView;
    }

    static class MyViewHolder
    {
        CircleImageView ifriendprofile;
        TextView titem3,textView3;

        public MyViewHolder(View item)
        {
            titem3 = (TextView)item.findViewById(R.id.titem3);
            textView3 = (TextView)item.findViewById(R.id.textView3);
            ifriendprofile = (CircleImageView) item.findViewById(R.id.ifriendprofile);
        }
    }
}
