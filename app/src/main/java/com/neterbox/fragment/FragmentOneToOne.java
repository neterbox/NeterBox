package com.neterbox.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
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

import com.neterbox.ChatBox;
import com.neterbox.R;
import com.neterbox.customadapter.PackageChatAdapter.OneToOneChatAdapter;
import com.neterbox.jsonpojo.chatlist.ChatList;
import com.neterbox.jsonpojo.chatlist.ChatListDatum;
import com.neterbox.qb.ChatHelper;
import com.neterbox.qb.QbDialogHolder;
import com.neterbox.retrofit.APIClient;
import com.neterbox.retrofit.APIInterface;
import com.neterbox.utils.Sessionmanager;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.neterbox.FreindProfile.REQUEST_DIALOG_ID_FOR_UPDATE;

public class FragmentOneToOne extends Fragment {
    Context context;
    ListView chat;
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    Sessionmanager sessionmanager;
    List<ChatListDatum> chatlistdata;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment_one_to_one, container, false);
        context = getContext();
        sessionmanager = new Sessionmanager(context);
        chat = (ListView) view.findViewById(R.id.chat);

        call_Chatlist(sessionmanager.getValue(Sessionmanager.Id));


        chat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                QBRestChatService.getChatDialogById(chatlistdata.get(position).getTblDailog().getDialogId()).performAsync(new QBEntityCallback<QBChatDialog>() {
                    @Override
                    public void onSuccess(QBChatDialog dialog, Bundle params) {

                        ChatBox.startForResult((Activity) context, REQUEST_DIALOG_ID_FOR_UPDATE, dialog, chatlistdata.get(position).getReceiver().getName(), chatlistdata.get(position).getReceiver().getProfilePic());
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

    // TODO : API CALLING CHATLIST
    public void call_Chatlist(String sender_id) {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Please Wait...");
        dialog.show();
        Call<ChatList> chatCall = apiInterface.chatlistpojo(sender_id);

        chatCall.enqueue(new Callback<ChatList>() {
            @Override
            public void onResponse(Call<ChatList> call, Response<ChatList> response) {
                dialog.dismiss();
                if (response.body().getStatus().equals("Success")) {

                    chatlistdata = new ArrayList<>();
                    chatlistdata = response.body().getData();
                    for (int i = 0; i < chatlistdata.size(); i++) {
                        sessionmanager.createSession_chatlist(response.body().getData().get(i));
                    }

                    OneToOneChatAdapter adapter = new OneToOneChatAdapter(context, chatlistdata);
                    chat.setAdapter(adapter);
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, "problem", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChatList> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
