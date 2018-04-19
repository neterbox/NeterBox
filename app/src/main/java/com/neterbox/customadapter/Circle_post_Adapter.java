package com.neterbox.customadapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.neterbox.*;
import com.neterbox.R;
import com.neterbox.jsonpojo.circlepostlist.CirclePostListDatum;
import com.neterbox.jsonpojo.followingadd.Following;
import com.neterbox.jsonpojo.followingadd.FollowingDatum;
import com.neterbox.jsonpojo.like.Like;
import com.neterbox.retrofit.APIClient;
import com.neterbox.retrofit.APIInterface;
import com.neterbox.utils.Sessionmanager;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sejal on 2/1/2018.
 */

public class Circle_post_Adapter extends BaseAdapter {

    Activity activity;
    LayoutInflater inflater;
    public Resources res;
    Context context;
    Sessionmanager sessionmanager;
    FollowingDatum followingData;
    List<CirclePostListDatum> circlePostListData = new ArrayList<>();
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    public Circle_post_Adapter(Activity a, List<CirclePostListDatum> circlePostListData) {
        this.activity = a;
        this.circlePostListData = circlePostListData;
        this.sessionmanager = new Sessionmanager(activity);
        inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return circlePostListData.size();
    }

    @Override
    public Object getItem(int i) {
        return circlePostListData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final MyViewHolder viewHolder;

        final String user_id = sessionmanager.getValue(Sessionmanager.Id);
        final String post_id = circlePostListData.get(i).getPost().getId();
        final String follower_id = sessionmanager.getValue(Sessionmanager.Id);
        final String following_id = circlePostListData.get(i).getUser().getId();
        final String list = sessionmanager.getValue(Sessionmanager.following_id);
//        final String comments = circlePostListData.get(i).getComment().get(i).getComments();

        if (view == null) {
            view = inflater.inflate(R.layout.circlepostitem, viewGroup, false);
            viewHolder = new MyViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) view.getTag();
        }

//        String datetime= circlePostListData.get(i).getPostFile().get(0).getCreated();
//        String[] separated = datetime.split(" ");
//        String date = separated[0];
//        String time =separated[1];
        viewHolder.tcircle_post_name.setText(circlePostListData.get(i).getUser().getName());
        viewHolder.tcircle_post_cap.setText(circlePostListData.get(i).getPost().getComments());

//        if(viewHolder.tcircle_post_likesno!= null)
//        {
//            Log.e("circlepstdata : ",new Gson().toJson(circlePostListData));
//            viewHolder.tcircle_post_likesno.setText(circlePostListData.get(i).getPostlike().get(0).getLikes());
//        }

//        if(viewHolder.tcircle_post_commentno != null)
//        {
//            viewHolder.tcircle_post_commentno.setText(circlePostListData.get(i).getComment().get(i).getComments());
//        }
//        viewHolder.tcircle_post_time.setText(time);
//        Glide.with(activity).load(circlePostListData.get(i).getPostFile().get(i).getFiles()).placeholder(R.drawable.dummy).into(viewHolder.icircle_post_pic);
        Glide.with(activity).load(circlePostListData.get(i).getUser().getProfilePic()).placeholder(R.drawable.dummy).into(viewHolder.cvcircle_post_profile);

//        Glide.with(activity).load(circlePostListData.get(i).getPostFile().get(i).getFiles()).into(viewHolder.icircle_post_pic);

        viewHolder.tcircle_post_channel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(activity, PlayGridview.class);
                activity.startActivity(it);
                activity.finish();
            }
        });
        viewHolder.lcircle_post_likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callApi_Like(user_id,post_id);
            }
        });
        viewHolder.lcircle_post_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent it =new Intent(activity,Circle_comment.class);
                it.putExtra("Likes",circlePostListData.get(1).getPostlike().get(1).getLikes());
                activity.startActivity(it);
                activity.finish();
            }
        });
        if(following_id.equalsIgnoreCase(list))
        {
            viewHolder.tfollowing.setEnabled(false);
        }
        viewHolder.lfollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callApi_following(follower_id,following_id);
            }
        });
        return view;
    }
    private class MyViewHolder {

        LayoutInflater inflater;
        LinearLayout lcircle_post_comment, lcircle_post_likes, lfollowing, lcircle_post_channel;
        CircleImageView cvcircle_post_profile;
        TextView tcircle_post_name, tcirce_post_seen, tcircle_post_time, tcircle_post_cap,
                tcircle_post_commentno, tcircle_post_likesno, tfollowing, tcircle_post_channel;
        ImageView icircle_post_pic, icircle_post_likes, icircle_post_share;

        public MyViewHolder(View item) {
            cvcircle_post_profile = (CircleImageView) item.findViewById(R.id.cvcircle_post_profile);
            tcircle_post_name = (TextView) item.findViewById(R.id.tcircle_post_name);
            tcirce_post_seen = (TextView) item.findViewById(R.id.tcirce_post_seen);
            tfollowing = (TextView) item.findViewById(R.id.tfollowing);
            tcircle_post_time = (TextView) item.findViewById(R.id.tcircle_post_time);
            tcircle_post_cap = (TextView) item.findViewById(R.id.tcircle_post_cap);
            tcircle_post_commentno = (TextView) item.findViewById(R.id.tcircle_post_commentno);
            tcircle_post_likesno = (TextView) item.findViewById(R.id.tcircle_post_likesno);
            tfollowing = (TextView) item.findViewById(R.id.tfollowing);
            tcircle_post_channel = (TextView) item.findViewById(R.id.tcircle_post_channel);
            icircle_post_pic = (ImageView) item.findViewById(R.id.icircle_post_pic);
            icircle_post_likes = (ImageView) item.findViewById(R.id.icircle_post_likes);
            icircle_post_share = (ImageView) item.findViewById(R.id.icircle_post_share);
            lcircle_post_comment = (LinearLayout) item.findViewById(R.id.lcircle_post_comment);
            lcircle_post_likes = (LinearLayout) item.findViewById(R.id.lcircle_post_likes);
            lfollowing = (LinearLayout) item.findViewById(R.id.lfollowing);
            lcircle_post_channel = (LinearLayout) item.findViewById(R.id.lcircle_post_channel);
        }
    }

    //TODO Call Api For Like
    public void callApi_Like(String user_id,String post_id)
    {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Please Wait...");
        dialog.show();

        Call<Like> registration=apiInterface.likepojo(user_id,post_id);
        registration.enqueue(new Callback<Like>() {
            @Override
            public void onResponse(Call<Like> registrationCall, Response<Like> response) {

                if (response.body().getStatus().equalsIgnoreCase("Success")) {
                    dialog.dismiss();
                    Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Like> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //TODO Call Api For following
    public void callApi_following(String follower_id, String following_id) {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Please Wait...");
        dialog.show();

        Call<Following> followingCall = apiInterface.followingpojo(follower_id, following_id);
        followingCall.enqueue(new Callback<Following>()
        {
            @Override
            public void onResponse(Call<Following> followingCall1, Response<Following> response)
            {
                if(response.code()== 200)
                {
                    if (response.body().getStatus().equals("Success"))
                    {
                        dialog.dismiss();
                        followingData = new FollowingDatum();
                        followingData = response.body().getData();
                        Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("RESPONSE DATA FOLLOWING",new Gson().toJson(response.body()));
                    }
                    else
                    {
                        dialog.dismiss();
                        Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    dialog.dismiss();
                    Toast.makeText(activity,"Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Following> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}