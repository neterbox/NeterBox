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
import com.neterbox.utils.Constants;
import com.neterbox.utils.Sessionmanager;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DeLL on 01-05-2018.
 */

public class CircleChatAdapter extends BaseAdapter {
    Activity activity;
    private LayoutInflater inflater;
    public Resources res;
    Sessionmanager sessionmanager;

    public CircleChatAdapter(Activity activity) {

        this.activity = activity;
        sessionmanager = new Sessionmanager(activity);
        inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return Constants.circleChatData.size();
    }

    @Override
    public Object getItem(int position) {
        return Constants.circleChatData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        final MyViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.circle_chat_adapter, viewGroup, false);
            viewHolder = new MyViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) convertView.getTag();
        }

        viewHolder.textUserCommentsName.setText(Constants.circleChatData.get(position).getUser().getName());
        viewHolder.textUserCommentsDescripstion.setText(Constants.circleChatData.get(position).getCirclechat().getMessage());
        if (Constants.circleChatData.get(position).getUser().getProfilePic() != null) {
            Glide.with(activity).load(Constants.circleChatData.get(position).getUser().getProfilePic()).placeholder(R.drawable.dummy).into(viewHolder.circle_pic);
        }
        return convertView;
    }

    static class MyViewHolder {
        CircleImageView circle_pic;
        TextView textUserCommentsName, textUserCommentsDescripstion, textDateComments;

        public MyViewHolder(View item) {
            textUserCommentsName = (TextView) item.findViewById(R.id.textUserCommentsName);
            textUserCommentsDescripstion = (TextView) item.findViewById(R.id.textUserCommentsDescripstion);
            textDateComments = (TextView) item.findViewById(R.id.textDateComments);
            circle_pic = (CircleImageView) item.findViewById(R.id.circle_pic);

        }
    }
}
