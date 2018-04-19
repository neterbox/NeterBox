package com.neterbox;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.neterbox.customadapter.AttachmentPreviewAdapter;
import com.neterbox.jsonpojo.comment.Comment;
import com.neterbox.qb.ChatHelper;
import com.neterbox.qb.adapter.ChatAdapter;
import com.neterbox.retrofit.APIClient;
import com.neterbox.retrofit.APIInterface;
import com.neterbox.utils.Helper;
import com.neterbox.utils.Sessionmanager;
import com.quickblox.chat.model.QBAttachment;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.sample.core.ui.dialog.ProgressDialogFragment;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class Circle_comment extends AppCompatActivity {

    TextView comment_like;
    EditText edit_comments;
    ImageView comment_send;
    String like;
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    Activity activity;
    Sessionmanager sessionmanager;

    private EditText messageEditText;
    private static final String PROPERTY_SAVE_TO_HISTORY = "save_to_history";
    private ConnectionListener chatConnectionListener;

    private QBChatDialog qbChatDialog;
    private ArrayList<QBChatMessage> unShownMessages;
    private int skipPagination = 0;
    private ChatBox.ChatMessageListener chatMessageListener;

    private ChatAdapter chatAdapter;
    private AttachmentPreviewAdapter attachmentPreviewAdapter;
    private static final String TAG = ChatBox.class.getSimpleName();

    private ProgressBar progressBar;
    private StickyListHeadersListView messagesListView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_comment);
        activity=this;
        sessionmanager = new Sessionmanager(this);
        idMapping();
        Listener();
        like = getIntent().getStringExtra("like");
        comment_like.setText(like);
    }

    private void idMapping() {
        comment_like = (TextView)findViewById(R.id.comment_like);
        comment_send = (ImageView) findViewById(R.id.comment_send);
        messageEditText = (EditText) findViewById(R.id.edit_comments);
    }

    private void Listener() {

        comment_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_id = sessionmanager.getValue(Sessionmanager.Id);
                String post_id = sessionmanager.getValue(Sessionmanager.postid);
                callapi_comment(user_id,post_id,edit_comments.getText().toString());
                onSendChatClick(messageEditText.getText().toString().trim());
            }
        });
    }
    public void onSendChatClick(String text) {
        if (!TextUtils.isEmpty(text)) {
            messageEditText.setText("");
            sendChatMessage(text, null);
        } else {
//            int totalAttachmentsCount = attachmentPreviewAdapter.getCount();
//            Collection<QBAttachment> uploadedAttachments = attachmentPreviewAdapter.getUploadedAttachments();
//            if (!uploadedAttachments.isEmpty()) {
//                if (uploadedAttachments.size() == totalAttachmentsCount) {
//                    for (QBAttachment attachment : uploadedAttachments) {
//                        sendChatMessage(null, attachment);
//                    }
//                }
//                else {
//                    Helper.showToastMessage(context, getString(R.string.chat_wait_for_attachments_to_upload));
//                }
//            }
        }
    }

    private void sendChatMessage(String text, QBAttachment attachment) {

        QBChatMessage chatMessage = new QBChatMessage();
        chatMessage.setSaveToHistory(true);
        if (attachment != null) {
            chatMessage.addAttachment(attachment);
            chatMessage.setSaveToHistory(true);
        } else {
            chatMessage.setBody(text);
            chatMessage.setSaveToHistory(true);
        }
        chatMessage.setProperty(PROPERTY_SAVE_TO_HISTORY, "1");
        chatMessage.setDateSent(System.currentTimeMillis() / 1000);

//        if (!QBDialogType.PRIVATE.equals(qbChatDialog.getType()) && !qbChatDialog.isJoined()) {
//            return;
//        }

        try {
            ChatHelper.getInstance().addConnectionListener(chatConnectionListener);

            qbChatDialog.sendMessage(chatMessage);

//            if (QBDialogType.PRIVATE.equals(qbChatDialog.getType())) {
            showMessage(chatMessage);
//            }
            if (attachment != null) {
                attachmentPreviewAdapter.remove(attachment);
            } else {
                messageEditText.setText("");
            }

            ProgressDialogFragment.hide(getSupportFragmentManager());
        } catch (SmackException.NotConnectedException e) {
            Log.w(TAG, e);

//            Helper.hideSoftKeyboard(context);

            Helper.showToastMessage(activity, "Can't send a message, You are not connected to chat");

            finish();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void showMessage(QBChatMessage message) {
        if (chatAdapter != null) {
            chatAdapter.add(message, "");
            Log.e("Message : ", new Gson().toJson(message));
            scrollMessageListDown();
        } else {
            Log.e("show_msg_error", "SHOW ERROR");
            if (unShownMessages == null) {
                unShownMessages = new ArrayList<>();
            }
            unShownMessages.add(message);
        }
    }

    private void scrollMessageListDown() {
        messagesListView.setSelection(messagesListView.getCount() - 1);
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(Circle_comment.this,Circleprofilepic.class);
        startActivity(i);
        finish();
    }

    /*  TODO : API CALLING COMMENT  */
    public void callapi_comment(String user_id,String post_id,String comments) {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Please Wait...");
        dialog.show();

        Call<Comment> commentcall = apiInterface.commentpojo(user_id, post_id, comments);
        commentcall.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> commentCall, Response<Comment> response) {

                if (response.body().getStatus().equalsIgnoreCase("Success")) {
                    dialog.dismiss();
                    Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                else {
                    dialog.dismiss();
                    Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
