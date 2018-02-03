package com.neterbox.customadapter;

import android.app.Activity;
import android.content.res.Resources;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.neterbox.*;
import com.neterbox.R;
import com.neterbox.jsonpojo.FriendRequestList.FriendRequestListDatum;
import com.neterbox.jsonpojo.circle.CircleListDatum;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sejal on 2/1/2018.
 */

public class Circle_post_Adapter extends BaseAdapter {

    Activity activity;
    private LayoutInflater inflater;
    public Resources res;
    List<CircleListDatum> circleListData;

    public Circle_post_Adapter(Activity a) {
        this.activity = a;
        inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return circleListData.size();
    }

    @Override
    public Object getItem(int i) {
        return circleListData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public static class ViewHolder {

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        View convertView = null;
        Circle_post_Adapter.MyViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.circlepostitem, viewGroup, false);
            viewHolder = new Circle_post_Adapter.MyViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Circle_post_Adapter.MyViewHolder) convertView.getTag();
        }



//        if (!(circleListData.get(i).getReceiver().getProfilePic().equals(""))) {
//            Glide.with(activity).load(circleListData.get(i).getReceiver().getProfilePic()).into(viewHolder.civiewfrequest);
//        }
//        if (!(circleListData.get(i).getReceiver().getUsername().equals(""))) {
//            viewHolder.friendrequestname.setText(circleListData.get(i).getReceiver().getUsername());
//        }

        return convertView;
    }

    private class MyViewHolder {

        LayoutInflater inflater;
        LinearLayout lcircle_post_comment, lcircle_post_likes, lfollowing, lcircle_post_channel;
        CircleImageView cvcircle_post_profile;
        TextView tcircle_post_name, tcirce_post_seen, tcircle_post_time, tcircle_post_cap,
                tcircle_post_commentno, tcircle_post_likes, tfollowing, tcircle_post_channel;
        ImageView icircle_post_pic, icircle_post_likes, icircle_post_share;

        public MyViewHolder(View item) {

            cvcircle_post_profile = (CircleImageView) item.findViewById(R.id.cvcircle_post_profile);
            tcircle_post_name = (TextView) item.findViewById(R.id.tcircle_post_name);
            tcirce_post_seen = (TextView) item.findViewById(R.id.tcirce_post_seen);
            tcircle_post_time = (TextView) item.findViewById(R.id.tcircle_post_time);
            tcircle_post_cap = (TextView) item.findViewById(R.id.tcircle_post_cap);
            tcircle_post_commentno = (TextView) item.findViewById(R.id.tcircle_post_commentno);
            tcircle_post_likes = (TextView) item.findViewById(R.id.tcircle_post_likes);
            tfollowing = (TextView) item.findViewById(R.id.tfollowing);
            tcircle_post_channel = (TextView) item.findViewById(R.id.tcircle_post_channel);

            icircle_post_pic = (ImageView) item.findViewById(R.id.icircle_post_pic);
            icircle_post_likes = (ImageView) item.findViewById(R.id.icircle_post_likes);
            icircle_post_share = (ImageView) item.findViewById(R.id.icircle_post_share);

            lcircle_post_comment = (LinearLayout) item.findViewById(R.id.lcircle_post_comment);
            lcircle_post_likes = (LinearLayout) item.findViewById(R.id.lcircle_post_likes);
            lfollowing = (LinearLayout) item.findViewById(R.id.lfollowing);
            lcircle_post_channel = (LinearLayout) item.findViewById(R.id.lcircle_post_channel);

        }
    }
}