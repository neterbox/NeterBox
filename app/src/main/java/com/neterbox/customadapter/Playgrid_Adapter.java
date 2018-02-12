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
 * Created by DeLL on 06-01-2018.
 */

public class Playgrid_Adapter extends BaseAdapter {
    Activity activity;
    String[] itemname;
    Integer[] imgid;
    String[] itemname1;
    Integer[] imgid1;
    private ArrayList data;
    private LayoutInflater inflater;
    public Resources res;

    public Playgrid_Adapter(Activity a,String[] itemname, Integer[] imgid,String[] itemname1, Integer[] imgid1) {
        this.activity = a;
        this.itemname = itemname;
        this.imgid = imgid;
        this.itemname1 = itemname1;
        this.imgid1 = imgid1;

        inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return itemname.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final MyViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.playgrid_item, viewGroup, false);
            viewHolder = new MyViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) view.getTag();
        }

        viewHolder.tplaygrid_text.setText(itemname[i]);
        viewHolder.tplaygrid_subtext.setText(itemname1[i]);
        Glide.with(activity).load(imgid[i]).into(viewHolder.iplaygrid_pic);
        Glide.with(activity).load(imgid[i]).into(viewHolder.iplaygrid_text);
        return view;
    }
    class MyViewHolder {
        public TextView tplaygrid_text,tplaygrid_subtext;
        public ImageView iplaygrid_pic,iplaygrid_text;
        public LinearLayout lplaygrid_item,lplaygrid_text;

    public MyViewHolder(View item) {
        tplaygrid_text = (TextView) item.findViewById(R.id.tplaygrid_text);
        tplaygrid_subtext = (TextView) item.findViewById(R.id.tplaygrid_subtext);
        iplaygrid_pic = (ImageView) item.findViewById(R.id.iplaygrid_pic);
        iplaygrid_text = (ImageView) item.findViewById(R.id.iplaygrid_text);
        lplaygrid_item = (LinearLayout) item.findViewById(R.id.lplaygrid_item);
        lplaygrid_text = (LinearLayout) item.findViewById(R.id.lplaygrid_text);
    }
}
}