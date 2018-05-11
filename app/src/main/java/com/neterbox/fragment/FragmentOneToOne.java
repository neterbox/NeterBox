package com.neterbox.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.neterbox.ChatBox;
import com.neterbox.ChatModule;
import com.neterbox.CirclePost;
import com.neterbox.R;
import com.neterbox.customadapter.PackageChatAdapter.ConntactForGroupChatAdapter;
import com.neterbox.customadapter.PackageChatAdapter.OneToOneChatAdapter;
import com.neterbox.jsonpojo.chatlist.ChatList;
import com.neterbox.jsonpojo.chatlist.ChatListDatum;
import com.neterbox.jsonpojo.circlepostdelete.CirclePostDeleteP;
import com.neterbox.jsonpojo.deletechat.DeleteChat;
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

import static com.neterbox.FreindProfile.REQUEST_DIALOG_ID_FOR_UPDATE;

public class FragmentOneToOne extends Fragment {
    Context context;
    ListView chat;
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    Sessionmanager sessionmanager;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment_one_to_one, container, false);
        context = getContext();
        sessionmanager = new Sessionmanager(context);
        chat = (ListView) view.findViewById(R.id.chat);

        OneToOneChatAdapter adapter = new OneToOneChatAdapter(context);
        chat.setAdapter(adapter);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }

        chat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id)
            {

//                if (!chat.isLongClickable())
//                {
                QBRestChatService.getChatDialogById(Constants.one.get(position).getTblDailog().getDialogId()).performAsync(new QBEntityCallback<QBChatDialog>() {
                    @Override
                    public void onSuccess(QBChatDialog dialog, Bundle params) {
                        ChatBox.startForResult((Activity) context, REQUEST_DIALOG_ID_FOR_UPDATE, dialog, Constants.one.get(position).getReceiver().get(0).getName(), Constants.one.get(position).getReceiver().get(0).getProfilePic());
                        getActivity().finish();
                    }

                    @Override
                    public void onError(QBResponseException responseException) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
//                }
            }
        });

        chat.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setMessage("Are you sure you want to delete chat ??");

                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int which) {
                                deletechat(Constants.one.get(position).getTblDailog().getId());
                            }
                        });
                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
                return true;
            }
        });
        return view;
    }

    public void deletechat(String id)
    {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Please Wait...");
        dialog.show();

        Call<DeleteChat> registration = apiInterface.deletechatpojo(id);
        registration.enqueue(new Callback<DeleteChat>() {
            @Override
            public void onResponse(Call<DeleteChat> registrationCall, Response<DeleteChat> response) {

                if (response.body().getStatus().equalsIgnoreCase("Success")) {
                    dialog.dismiss();
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                } else {
                    dialog.dismiss();
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteChat> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
