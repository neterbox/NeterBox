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

    public ContactsforChatAdapter(Activity a){
        this.activity=a;
        inflater=(LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return 15;
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
        public TextView tgroupitem;
        public ImageView igroupback;
        public CircleImageView icontactsforgroupchat;
        public LinearLayout lchatforgroup;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v=view;
        ContactsforChatAdapter.ViewHolder holder;
        if(view==null)
        {
            v=inflater.inflate(R.layout.contactsforchatitems,null);
            holder=new ContactsforChatAdapter.ViewHolder();
            holder.tgroupitem=(TextView) v.findViewById(R.id.tgroupitem);
            holder.igroupback=(ImageView) v.findViewById(R.id.igroupback);
            holder.icontactsforgroupchat=(CircleImageView) v.findViewById(R.id.icontactsforgroupchat);
            holder.lchatforgroup=(LinearLayout) v.findViewById(R.id.lchatcorgroup);

            v.setTag(holder);
        }
        else
        {
            holder=(ContactsforChatAdapter.ViewHolder)v.getTag();

        }
        return v;

    }
}
