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
import com.neterbox.R;
import com.neterbox.customadapter.Circle_Adapter;
import com.neterbox.jsonpojo.chatlist.ChatList;
import com.neterbox.jsonpojo.chatlist.ChatListDatum;
import com.neterbox.utils.Sessionmanager;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DeLL on 01-03-2018.
 */

public class OneToOneChatAdapter extends BaseAdapter{
    Context context;
    private ArrayList data;
    private LayoutInflater inflater;
    public Resources res;
    List<ChatListDatum> chatListDatum = new ArrayList<>();


    public OneToOneChatAdapter(Context a, List<ChatListDatum> chatListDatum) {
        this.chatListDatum = chatListDatum;
        this.context = a;
    }


    @Override
    public int getCount() {
        return (chatListDatum != null ? chatListDatum.size() : 0);
    }

    @Override
    public Object getItem(int i) {
        return chatListDatum.get(i);
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
        ConntactForGroupChatAdapter.ViewHolder holder;

        if (view == null) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_group_chat, parent, false);
            holder = new ConntactForGroupChatAdapter.ViewHolder();
            holder.tonechatitem = (TextView) v.findViewById(R.id.tonechatitem);
            holder.icontactsforone = (CircleImageView) v.findViewById(R.id.icontactsforone);
            holder.lchatforone = (LinearLayout) v.findViewById(R.id.lchatforone);
            holder.ionechatback = (ImageView) v.findViewById(R.id.ionechatback);
            v.setTag(holder);
        } else
            holder = (ConntactForGroupChatAdapter.ViewHolder) v.getTag();

        holder.tonechatitem.setText(chatListDatum.get(i).getReceiver().getName());
        Glide.with(context).load(chatListDatum.get(i).getReceiver().getProfilePic()).placeholder(R.drawable.dummy).into(holder.icontactsforone);
        return v;
    }
}
