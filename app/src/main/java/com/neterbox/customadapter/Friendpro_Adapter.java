package com.neterbox.customadapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.neterbox.R;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DeLL on 05-01-2018.
 */

public class Friendpro_Adapter extends BaseAdapter{

    Activity activity;
    private final String[] itemname;
    private final Integer[] imgid;
    private final String[] itemname1;
    private final Integer[] imgid1;
    private LayoutInflater inflater;
    public Resources res;

    public Friendpro_Adapter(Activity a, String[] itemname, Integer[] imgid,String[] itemname1, Integer[] imgid1) {
        // TODO Auto-generated constructor stub

        this.activity = a;
        this.itemname = itemname;
        this.imgid = imgid;
        this.itemname1 = itemname1;
        this.imgid1 = imgid1;

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

    public View getView(int i, View view, ViewGroup viewGroup) {
        final MyViewHolder viewHolder;

        if (view == null)
        {
            view = inflater.inflate(R.layout.profilelist_item, viewGroup, false);
            viewHolder = new MyViewHolder(view);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (MyViewHolder) view.getTag();
        }

        viewHolder.tlistview_name.setText(itemname[i]);
        viewHolder.tlistview_seen.setText(itemname1[i]);
        Glide.with(activity).load(imgid[i]).into(viewHolder.listview_profile);
        Glide.with(activity).load(imgid[i]).into(viewHolder.ilistview_pic);
        return view;
    }
}


 class MyViewHolder
{
    CircleImageView listview_profile;
    TextView tlistview_name,tlistview_seen;
    ImageView ilistview_pic;

    public MyViewHolder(View item)
    {
        tlistview_name = (TextView)item.findViewById(R.id.tlistview_name);
        tlistview_seen = (TextView)item.findViewById(R.id.tlistview_seen);
        listview_profile = (CircleImageView) item.findViewById(R.id.listview_profile);
        ilistview_pic = (ImageView) item.findViewById(R.id.ilistview_pic);

    }
}
