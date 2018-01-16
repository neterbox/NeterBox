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

public class ContactsforoneChatAdapter extends BaseAdapter {

    Activity activity;
    private ArrayList data;
    private LayoutInflater inflater;
    public Resources res;

    public ContactsforoneChatAdapter(Activity a){
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
    public static class ViewHolder {
        public TextView tonechatitem,tonechatitem2;
        public ImageView ionechatback;
        public CircleImageView icontactsforone;
        public LinearLayout lchatforone;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v=view;
        ContactsforoneChatAdapter.ViewHolder holder;
        if(view==null)
        {
            v=inflater.inflate(R.layout.contactsforchatitems,null);
            holder=new ContactsforoneChatAdapter.ViewHolder();
            holder.tonechatitem=(TextView) v.findViewById(R.id.tonechatitem);
            holder.tonechatitem2=(TextView) v.findViewById(R.id.tonechatitem2);
            holder.ionechatback=(ImageView) v.findViewById(R.id.ionechatback);
            holder.icontactsforone=(CircleImageView) v.findViewById(R.id.icontactsforone);
            holder.lchatforone=(LinearLayout) v.findViewById(R.id.lchatforone);

            v.setTag(holder);
        }
        else
        {
            holder=(ContactsforoneChatAdapter.ViewHolder)v.getTag();

        }
        return v;

    }
}
