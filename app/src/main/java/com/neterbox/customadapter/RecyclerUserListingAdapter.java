package com.neterbox.customadapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import com.google.gson.Gson;
import com.neterbox.R;
import com.neterbox.jsonpojo.friend_list.FriendListDatum;
import com.neterbox.jsonpojo.register.RegistrationDatum;
import com.neterbox.utils.Sessionmanager;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by user on 10/16/2017.
 */

public class RecyclerUserListingAdapter extends RecyclerView.Adapter<RecyclerUserListingAdapter.DataObjectHolder>
        implements SectionIndexer {

    Context context;
    List<FriendListDatum> userDatumList;
    private List<FriendListDatum> selectedUsers;
    private List<Integer> initiallySelectedUsers=new ArrayList<>();
    private ArrayList<Integer> mSectionPositions;


//    protected FriendListDatum currentUser;

    public RecyclerUserListingAdapter(Context context, List<FriendListDatum> userDatumList) {
        this.context = context;
        this.userDatumList = userDatumList;
//        currentUser= new FriendListDatum();
//        currentUser = Sessionmanager.getDtUserFromPreference(context);

        selectedUsers = new ArrayList<>();
        selectedUsers.clear();
//        selectedUsers.add(currentUser);

    }

    public RecyclerUserListingAdapter(Context context, List<FriendListDatum> userDatumList, List<Integer> initiallySelectedUsers) {
        this.context = context;
        this.userDatumList = userDatumList;
//        currentUser= new FriendListDatum();

        selectedUsers = new ArrayList<>();
        selectedUsers.clear();
//        selectedUsers.add(currentUser);
        this.initiallySelectedUsers = initiallySelectedUsers;
    }


    @Override
    public RecyclerUserListingAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_user, parent, false);

        RecyclerUserListingAdapter.DataObjectHolder dataObjectHolder = new RecyclerUserListingAdapter.DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerUserListingAdapter.DataObjectHolder holder, final int position) {


        Log.e("initially=====" + initiallySelectedUsers.size(), ":" + new Gson().toJson(initiallySelectedUsers));
//        holder.loginTextView.setText(userDatumList.get(holder.getAdapterPosition()).getReceiver().getName());
        holder.loginTextView.setText(userDatumList.get(1).getReceiver().getName());
        holder.loginTextView.setTextColor(Color.parseColor("#000000"));
        holder.userCheckBox.setClickable(true);
        holder.userCheckBox.setEnabled(true);

        if(initiallySelectedUsers.size()>0)
        {
            for(Integer integer:initiallySelectedUsers)
            {
                if(!userDatumList.get(1).getReceiver().getQuickbloxId().equals(""))
                    if (Integer.parseInt(userDatumList.get(1).getReceiver().getQuickbloxId())==integer) {

//                        if (isUserMe(userDatumList.get(holder.getAdapterPosition()))) {
                        if (isUserMe(userDatumList.get(1))) {
                            holder.loginTextView.setText(context.getString(R.string.placeholder_username_you, userDatumList.get(1).getReceiver().getName()));

                        }

                        holder.loginTextView.setTextColor(Color.parseColor("#b1b1b1"));
                        holder.userCheckBox.setClickable(false);
                        holder.userCheckBox.setChecked(true);
                        holder.userCheckBox.setEnabled(false);
                        selectedUsers.add(userDatumList.get(1));
                        break;
                    }
            }
        }
        else
        {
            if (isUserMe(userDatumList.get(1))) {
//                holder.loginTextView.setText(context.getString(R.string.placeholder_username_you, userDatumList.get(position).getReceiver().getName()));
                holder.loginTextView.setText(context.getString(R.string.placeholder_username_you, userDatumList.get(1).getReceiver().getName()));
                holder.userCheckBox.setClickable(false);
                holder.userCheckBox.setChecked(true);
                holder.userCheckBox.setEnabled(false);
                holder.loginTextView.setTextColor(Color.parseColor("#b1b1b1"));

                selectedUsers.add(userDatumList.get(1));
            }
            else
                {
                holder.loginTextView.setText(userDatumList.get(1).getReceiver().getName());
                holder.loginTextView.setTextColor(Color.parseColor("#000000"));
            }
        }



//        if (isAvailableForSelection1(userDatumList.get(position))) {
//            holder.loginTextView.setTextColor(Color.parseColor("#ffffff"));
//        } else {
//        }

//        if (!(userDatumList.get(position).getProfile_pic().equals("")))
//
//        Glide.with(context).load(userDatumList.get(position).getReceiver().getProfilePic())
        Glide.with(context).load(userDatumList.get(1).getReceiver().getProfilePic())
                .asBitmap().centerCrop().placeholder(R.drawable.dummy).into(new BitmapImageViewTarget(holder.userImageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                holder.userImageView.setImageDrawable(circularBitmapDrawable);
            }
        });
//        else
//            holder.userImageView.setImageResource(R.drawable.profilescreen_chatscreen);

        holder.userCheckBox.setOnCheckedChangeListener(null);

        //if true, your checkbox will be selected, else unselected

        holder.userCheckBox.setChecked(userDatumList.get(1).getReceiver().isSelected());

        holder.userCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                userDatumList.get(1).getReceiver().setSelected(isChecked);

                if (isChecked) {
                    selectedUsers.add(userDatumList.get(1));
                } else {
                    selectedUsers.remove(userDatumList.get(1));
                }
            }
        });

    }
    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    @Override
    public Object[] getSections() {
        List<String> sections = new ArrayList<>(26);
        mSectionPositions = new ArrayList<>(26);
        for (int i = 0, size = userDatumList.size(); i < size; i++) {
            String section = String.valueOf(userDatumList.get(1).getReceiver().getName().charAt(0)).toUpperCase();
            if (!sections.contains(section)) {
                sections.add(section);
                mSectionPositions.add(i);
            }
        }
        return sections.toArray(new String[0]);
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return mSectionPositions.get(sectionIndex);
    }

    @Override
    public int getItemCount() {
        return userDatumList.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        CircleImageView userImageView;
        TextView loginTextView;
        CheckBox userCheckBox;

        public DataObjectHolder(View convertView) {
            super(convertView);
            userImageView = (CircleImageView) convertView.findViewById(R.id.image_user);
            loginTextView = (TextView) convertView.findViewById(R.id.text_user_login);
            userCheckBox = (CheckBox) convertView.findViewById(R.id.checkbox_user);

        }
    }

    protected boolean isUserMe(FriendListDatum user) {
        boolean data = false;

        for (int i = 0; i < userDatumList.size(); i++) {
            Log.e("Current USER ", new Gson().toJson(userDatumList));
            data= userDatumList != null && userDatumList.get(1).getReceiver().getId().equals(user.getReceiver().getId());
        }
        return data;
    }

//    protected boolean isAvailableForSelection(LoginRegisterDatum user) {
////        return currentUser == null || !currentUser.getId().equals(user.getId());
//        return isAvailableForSelection1(user) && !initiallySelectedUsers.contains(user.getQb_id());
//    }

//    protected boolean isAvailableForSelection1(FriendListDatum user) {
//        return currentUser == null || !currentUser.getReceiver().getId().equals(user.getReceiver().getId());
//    }

    public List<FriendListDatum> getSelectedUsers() {
        return selectedUsers;
    }

    public void addSelectedUsers(List<Integer> userIds) {
        for (FriendListDatum user : userDatumList) {
            for (Integer id : userIds) {
                if (user.getReceiver().getQuickbloxId().equals(id)) {
                    selectedUsers.add(user);
                    initiallySelectedUsers.add(Integer.parseInt(user.getReceiver().getQuickbloxId()));
                    break;
                }
            }
        }
    }
}
