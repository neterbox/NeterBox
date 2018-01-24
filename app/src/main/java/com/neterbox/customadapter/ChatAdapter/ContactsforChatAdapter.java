package com.neterbox.customadapter.ChatAdapter;

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
 * Created by sejal on 1/9/2018.
 */

public class ContactsforChatAdapter extends BaseAdapter {

    Activity activity;
    private ArrayList data;
    private LayoutInflater inflater;
    public Resources res;

    String[] itemname, itemname2;
    Integer[] imgid;

    public ContactsforChatAdapter(Activity a, String[] itemname, String[] itemname2, Integer[] imgid) {
        this.activity = a;
        this.itemname = itemname;
        this.itemname = itemname2;
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

    public static class ViewHolder {
        public TextView tgroupitem,tonechatitem,tonechatitem2;
        public ImageView igroupback;
        public CircleImageView icontactsforgroupchat,icontactsforone;
        public LinearLayout lchatforgroup,lchatforone;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = view;
        ViewHolder holder;
        if (view == null) {
            v = inflater.inflate(R.layout.fragment_one_to_one, null);
            holder = new ViewHolder();
            holder.tgroupitem = (TextView) v.findViewById(R.id.tgroupitem);
            holder.igroupback = (ImageView) v.findViewById(R.id.igroupback);
            holder.icontactsforgroupchat = (CircleImageView) v.findViewById(R.id.icontactsforgroupchat);
            holder.lchatforgroup = (LinearLayout) v.findViewById(R.id.lchatcorgroup);
            holder.tonechatitem = (TextView) v.findViewById(R.id.tonechatitem);
            holder.tonechatitem2 = (TextView) v.findViewById(R.id.tonechatitem2);
            holder.icontactsforone = (CircleImageView) v.findViewById(R.id.icontactsforone);
            holder.lchatforone = (LinearLayout) v.findViewById(R.id.lchatforone);
            view = inflater.inflate(R.layout.fragment_one_to_one, viewGroup, false);
            view.setTag(holder);
            v.setTag(holder);
        } else {
            holder = (ContactsforChatAdapter.ViewHolder) v.getTag();

        }
        holder.tonechatitem.setText(itemname[i]);
        Glide.with(activity).load(imgid[i]).into(holder.icontactsforone);
        return v;

    }
}