package com.neterbox.customadapter.ChatAdapter;

import android.content.Context;
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
 * Created by DeLL on 01-03-2018.
 */

public class OneToOneChatAdapter extends BaseAdapter{
    Context context;
    private ArrayList data;
    private LayoutInflater inflater;
    public Resources res;


    public OneToOneChatAdapter(Context a) {
        this.context = a;
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
        public TextView tgroupitem,tonechatitem,tonechatitem2;
        public ImageView igroupback;
        public CircleImageView icontactsforgroupchat,icontactsforone;
        public LinearLayout lchatforgroup,lchatforone;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View v = view;
        ViewHolder holder;

        if (view == null) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contactforonechatitem,parent, false);
            holder = new ViewHolder();
            holder.tgroupitem = (TextView) v.findViewById(R.id.tgroupitem);
            holder.igroupback = (ImageView) v.findViewById(R.id.igroupback);
            holder.icontactsforgroupchat = (CircleImageView) v.findViewById(R.id.icontactsforgroupchat);
            holder.lchatforgroup = (LinearLayout) v.findViewById(R.id.lchatcorgroup);
            holder.tonechatitem = (TextView) v.findViewById(R.id.tonechatitem);
            holder.tonechatitem2 = (TextView) v.findViewById(R.id.tonechatitem2);
            holder.icontactsforone = (CircleImageView) v.findViewById(R.id.icontactsforone);
            holder.lchatforone = (LinearLayout) v.findViewById(R.id.lchatforone);
            v.setTag(holder);
        } else
            holder = (ViewHolder) v.getTag();

        return v;
    }
}


//    @Override
//    public int getCount() {
//        return 6;
//
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return 6;
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return i;
//    }
//
//    public static class ViewHolder {
//        public TextView tgroupitem,tonechatitem,tonechatitem2;
//        public ImageView igroupback;
//        public CircleImageView icontactsforgroupchat,icontactsforone;
//        public LinearLayout lchatforgroup,lchatforone;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//
//        View v = view;
//        ViewHolder holder;
//        if (view == null) {
//            v = inflater.inflate(R.layout.fragment_one_to_one,null);
//            holder = new ViewHolder();
//            holder.tgroupitem = (TextView) v.findViewById(R.id.tgroupitem);
//            holder.igroupback = (ImageView) v.findViewById(R.id.igroupback);
//            holder.icontactsforgroupchat = (CircleImageView) v.findViewById(R.id.icontactsforgroupchat);
//            holder.lchatforgroup = (LinearLayout) v.findViewById(R.id.lchatcorgroup);
//            holder.tonechatitem = (TextView) v.findViewById(R.id.tonechatitem);
//            holder.tonechatitem2 = (TextView) v.findViewById(R.id.tonechatitem2);
//            holder.icontactsforone = (CircleImageView) v.findViewById(R.id.icontactsforone);
//            holder.lchatforone = (LinearLayout) v.findViewById(R.id.lchatforone);
//            view = inflater.inflate(R.layout.fragment_one_to_one, viewGroup, false);
//            view.setTag(holder);
//            v.setTag(holder);
//        } else {
//            holder = (ViewHolder) v.getTag();
//
//        }
////        holder.tonechatitem.setText(itemname[i]);
////        Glide.with(activity).load(imgid[i]).into(holder.icontactsforone);
//        return v;
