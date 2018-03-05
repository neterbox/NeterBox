package com.neterbox.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.neterbox.ChatBox;
import com.neterbox.R;
import com.neterbox.customadapter.ChatAdapter.OneToOneChatAdapter;

public class FragmentOneToOne extends Fragment {
    Context context;
    ListView chat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment_one_to_one, container, false);
        context = getContext();
        chat = (ListView)view.findViewById(R.id.chat);
        OneToOneChatAdapter adapter = new OneToOneChatAdapter(context);
        chat.setAdapter(adapter);

        chat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                fragment = new OneToOneChat();
//                if (fragment != null) {
//                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                    ft.replace(R.id.content_frame, fragment);
//                    ft.commit();
//                }

                Intent it = new Intent(getContext(), ChatBox.class);
                getContext().startActivity(it);
                getActivity().finish();

            }
        });
        return view;
    }

}
