package com.neterbox.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.neterbox.GroupChatBox;
import com.neterbox.R;
import com.neterbox.customadapter.PackageChatAdapter.ConntactForGroupChatAdapter;
import com.neterbox.jsonpojo.chatlist.ChatList;
import com.neterbox.jsonpojo.chatlist.ChatListDatum;
import com.neterbox.retrofit.APIClient;
import com.neterbox.retrofit.APIInterface;
import com.neterbox.utils.Sessionmanager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GroupChatFragment extends Fragment {
    Context context;
    ListView chat;
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    Sessionmanager sessionmanager;
    List<ChatListDatum> chatListDatum = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_fragment_one_to_one, container, false);
        context = getContext();
        sessionmanager=new Sessionmanager(context);

        chat = (ListView)view.findViewById(R.id.chat);
        call_Chatlist(sessionmanager.getValue(sessionmanager.Id));

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

    // TODO : API CALLING CHATLIST
    public void call_Chatlist(String sender_id) {
        Call<ChatList> chatCall = apiInterface.chatlistpojo(sender_id);

        chatCall.enqueue(new Callback<ChatList>() {
            @Override
            public void onResponse(Call<ChatList> call, Response<ChatList> response) {
                if (response.body().getStatus().equals("Success")) {
                    chatListDatum  = response.body().getData();
                    ConntactForGroupChatAdapter adapter = new ConntactForGroupChatAdapter(context,chatListDatum);
                    chat.setAdapter(adapter);
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, "problem", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChatList> call, Throwable t) {
                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    }