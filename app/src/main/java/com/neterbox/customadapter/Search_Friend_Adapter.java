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

import com.bumptech.glide.Glide;
import com.neterbox.R;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sejal on 1/5/2018.
 */

public class Search_Friend_Adapter extends BaseAdapter{
    Activity activity;
    private final String[] itemname;
    private final Integer[] imgid;
    private LayoutInflater inflater;
    public Resources res;

    public Search_Friend_Adapter(Activity a, String[] itemname, Integer[] imgid) {

    // TODO Auto-generated constructor stub

        this.activity = a;
        this.itemname = itemname;
        this.imgid = imgid;

        inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
}

    @Override
    public int getCount() {
        return (itemname !=null ? itemname.length :0);
    }

    @Override
    public Object getItem(int i) {
        return itemname[i];
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

        viewHolder.titem3.setText(itemname[i]);
        Glide.with(activity).load(imgid[i]).into(viewHolder.ifriendprofile);
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
