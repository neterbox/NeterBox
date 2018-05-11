package com.neterbox.customadapter.PackageChatAdapter;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.neterbox.R;
import com.neterbox.utils.Constants;

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
        Log.e("CHATLIST DATA",new Gson().toJson(Constants.one));
    }


    @Override
    public int getCount() {
        return (Constants.one != null ? Constants.one.size() : 0);
    }

    @Override
    public Object getItem(int i) {
        return Constants.one.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public static class ViewHolder {
        public TextView tonechatitem;
        public ImageView ionechatback;
        public CircleImageView icontactsforone;
        public LinearLayout lchatforone;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        View v = view;
        ViewHolder holder;

        if (view == null) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contactforonechatitem, parent, false);
            holder = new ViewHolder();
            holder.tonechatitem = (TextView) v.findViewById(R.id.tonechatitem);
            holder.icontactsforone = (CircleImageView) v.findViewById(R.id.icontactsforone);
            holder.lchatforone = (LinearLayout) v.findViewById(R.id.lchatforone);
            holder.ionechatback = (ImageView) v.findViewById(R.id.ionechatback);
            v.setTag(holder);
        } else
            holder = (ViewHolder) v.getTag();

        if(holder.tonechatitem != null){
            holder.tonechatitem.setText(Constants.one.get(i).getReceiver().getName());
        }
        if(holder.icontactsforone != null){
            Glide.with(context).load(Constants.one.get(i).getReceiver().getProfilePic()).placeholder(R.drawable.dummy).into(holder.icontactsforone);
        }
        return v;
    }
}
