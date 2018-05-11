package com.neterbox.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.neterbox.ChatBox;
import com.neterbox.ChatModule;
import com.neterbox.Create_group;
import com.neterbox.R;
import com.neterbox.customadapter.PackageChatAdapter.ConntactForGroupChatAdapter;
import com.neterbox.customadapter.PackageChatAdapter.OneToOneChatAdapter;
import com.neterbox.jsonpojo.chatlist.ChatList;
import com.neterbox.jsonpojo.chatlist.ChatListDatum;
import com.neterbox.retrofit.APIClient;
import com.neterbox.retrofit.APIInterface;
import com.neterbox.utils.Constants;
import com.neterbox.utils.Sessionmanager;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.neterbox.Create_group.REQUEST_DIALOG_ID_FOR_UPDATE;


public class GroupChatFragment extends Fragment {
    Context context;
    ListView chat;
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    Sessionmanager sessionmanager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_fragment_one_to_one, container, false);
        context = getContext();
        sessionmanager=new Sessionmanager(context);

        chat = (ListView)view.findViewById(R.id.chat);
        ChatModule.iright.setImageResource(R.drawable.plus);

        ConntactForGroupChatAdapter adapter = new ConntactForGroupChatAdapter(context);
        chat.setAdapter(adapter);
        if(adapter != null)
        {
            adapter.notifyDataSetChanged();
        }

        chat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                QBRestChatService.getChatDialogById(Constants.group.get(position).getTblDailog().getDialogId()).performAsync(new QBEntityCallback<QBChatDialog>() {
                    @Override
                    public void onSuccess(QBChatDialog dialog, Bundle params) {

                        ChatBox.startForResult((Activity) context, REQUEST_DIALOG_ID_FOR_UPDATE, dialog, Constants.group.get(position).getTblDailog().getGroupName(), Constants.group.get(position).getTblDailog().getIcon());
                        getActivity().finish();
                    }

                    @Override
                    public void onError(QBResponseException responseException) {

                    }
                });
            }
        });

        return view;
    }

}