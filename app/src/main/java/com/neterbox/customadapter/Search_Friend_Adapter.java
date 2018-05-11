package com.neterbox.customadapter;

import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.neterbox.R;
import com.neterbox.jsonpojo.friend_list.FriendListDatum;
import com.neterbox.retrofit.APIClient;
import com.neterbox.retrofit.APIInterface;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sejal on 1/5/2018.
 */

public class Search_Friend_Adapter extends BaseAdapter{

    Activity activity;
    private LayoutInflater inflater;
    public Resources res;
    String id;
    List<FriendListDatum> friendListData= new ArrayList<>();
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    public Search_Friend_Adapter(Activity a, List<FriendListDatum> friendListData) {
        this.activity = a;
        this.friendListData = friendListData;
        inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return friendListData.size();
    }

    @Override
    public Object getItem(int i) {
        return friendListData.get(i);
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
            convertView = inflater.inflate(R.layout.friendlistitem, viewGroup, false);
            viewHolder = new MyViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (MyViewHolder) convertView.getTag();
        }

        if (!(friendListData.get(i).getReceiver().getProfilePic().equals(""))) {
            Glide.with(activity).load(friendListData.get(i).getReceiver().getProfilePic()).into(viewHolder.ifriendprofile);
        }
        if (!(friendListData.get(i).getReceiver().getName().equals(""))) {
            viewHolder.titem3.setText(friendListData.get(i).getReceiver().getName());
        }
        return convertView;
    }

    private class MyViewHolder
    {
        CircleImageView ifriendprofile;
        TextView titem3;

        public MyViewHolder(View item)
        {
            titem3 = (TextView)item.findViewById(R.id.titem3);
            ifriendprofile = (CircleImageView) item.findViewById(R.id.ifriendprofile);
        }
    }
}
