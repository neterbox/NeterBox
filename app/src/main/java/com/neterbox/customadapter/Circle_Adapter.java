package com.neterbox.customadapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.neterbox.R;
import com.neterbox.jsonpojo.circle.CircleListDatum;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sejal on 1/6/2018.
 */

public class Circle_Adapter extends BaseAdapter {
    Activity activity;
    private LayoutInflater inflater;

    List<CircleListDatum> circleList;
    public Circle_Adapter(Activity a,List<CircleListDatum> circleList) {
        this.activity = a;
        this.circleList=circleList;
        inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return circleList.size();
    }

    @Override
    public Object getItem(int i) {
        return circleList.get(i);
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



        if(!(circleList.get(i).getCircle().getName().equals("")))
        {
            viewHolder.tcircletext.setText(circleList.get(i).getCircle().getName());
        }
//        if(!(circleList.get(i).getCircle().getFiles().equals("")))
//        {
//            Glide.with(activity).load(circleList.get(i).getCircle().getFiles()).into(viewHolder.icircleprofile);
//        }
//        else{
        if(!(circleList.get(i).getCircle().getFiles().equals(""))){
            Glide.with(activity).load(circleList.get(i).getCircle().getFiles()).placeholder(R.drawable.dummy).into(viewHolder.icircleprofile);
        }
        return view;
    }

    public class MyViewHolder {
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
