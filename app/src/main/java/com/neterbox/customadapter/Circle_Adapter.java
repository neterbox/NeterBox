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
 * Created by sejal on 1/6/2018.
 */

public class Circle_Adapter extends BaseAdapter {
    Activity activity;
    String[] itemname;
    Integer[] imgid;
    private ArrayList data;
    private LayoutInflater inflater;
    public Resources res;

    public Circle_Adapter(Activity a,String[] itemname, Integer[] imgid) {
        this.activity = a;
        this.itemname = itemname;
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final MyViewHolder viewHolder;

        if (view == null) {
            view = inflater.inflate(R.layout.circlesgriditem, viewGroup, false);
            viewHolder = new MyViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) view.getTag();
        }

        viewHolder.tcircletext.setText(itemname[i]);
        Glide.with(activity).load(imgid[i]).into(viewHolder.icircleprofile);
        return view;
    }

    class MyViewHolder {
        public TextView tcircletext;
        public CircleImageView icircleprofile;
        public LinearLayout lcircleitem;

        public MyViewHolder(View item) {


            tcircletext = (TextView) item.findViewById(R.id.tcircletext);
            icircleprofile = (CircleImageView) item.findViewById(R.id.icircleprofile);
            lcircleitem = (LinearLayout) item.findViewById(R.id.lcircleitem);

        }
    }
}
