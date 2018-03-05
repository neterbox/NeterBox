package com.neterbox.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.neterbox.ChatBox;
import com.neterbox.GroupChatBox;
import com.neterbox.R;
import com.neterbox.customadapter.ChatAdapter.ConntactForGroupChatAdapter;
import com.neterbox.customadapter.ChatAdapter.OneToOneChatAdapter;


public class GroupChatFragment extends Fragment {
    Context context;
    ListView chat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_fragment_one_to_one, container, false);
        context = getContext();
        chat = (ListView)view.findViewById(R.id.chat);
        ConntactForGroupChatAdapter adapter = new ConntactForGroupChatAdapter(context);
        chat.setAdapter(adapter);

        chat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent it = new Intent(getContext(),GroupChatBox.class);
                getContext().startActivity(it);
                getActivity().finish();

            }
        });
        return view;
    }
    }