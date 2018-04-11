package com.neterbox.qb.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.neterbox.R;
import com.neterbox.qb.QbDialogUtils;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBDialogType;
import com.quickblox.sample.core.ui.adapter.BaseSelectableListAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

//import com.squareup.picasso.Picasso;

public class DialogsAdapter extends BaseSelectableListAdapter<QBChatDialog> {

    private static final String EMPTY_STRING = "";

    public DialogsAdapter(Context context, List<QBChatDialog> dialogs) {
        super(context, dialogs);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.contact_group_chat, parent, false);

            holder = new ViewHolder();
            holder.rootLayout = (ViewGroup) convertView.findViewById(R.id.root);
            holder.nameTextView = (TextView) convertView.findViewById(R.id.text_dialog_name);
            holder.lastMessageTextView = (TextView) convertView.findViewById(R.id.text_dialog_last_message);
            holder.dialogImageView = (CircleImageView) convertView.findViewById(R.id.dialogImageView);
            holder.unreadCounterTextView = (TextView) convertView.findViewById(R.id.text_dialog_unread_count);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        QBChatDialog dialog = getItem(position);
        if (dialog.getType().equals(QBDialogType.GROUP)) {
//            holder.dialogImageView.setImageResource(R.drawable.rectangle_grey);
//            holder.dialogImageView.setImageResource(R.drawable.ic_chat_group);
        } else {
//            holder.dialogImageView.setBackgroundDrawable(UiUtils.getColorCircleDrawable(position));

//            QBUser qbUser=new QBUser();
//            qbUser.setFullName(QbDialogUtils.getDialogName(dialog));
//
//
//            int userProfilePictureID = qbUser.getFileId(); // user - an instance of QBUser
//
//            QBContent.downloadFileById(userProfilePictureID,null).performAsync(new QBEntityCallback<InputStream>() {
//                @Override
//                public void onSuccess(InputStream inputStream, Bundle bundle) {
//                    BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
//                    StringBuilder total = new StringBuilder();
//                    String line;
//                    try {
//                        while ((line = r.readLine()) != null) {
//                            total.append(line).append('\n');
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    Picasso.with(context).load(String.valueOf(total)).into(holder.dialogImageView);
//
//
//                }
//
//                @Override
//                public void onError(QBResponseException e) {
//
//                }
//            });


//            holder.dialogImageView.setImageResource(R.drawable.re);
        }

        Log.e("name",":"+ QbDialogUtils.getDialogName(dialog));

        holder.nameTextView.setText(QbDialogUtils.getDialogName(dialog));
        holder.lastMessageTextView.setText(prepareTextLastMessage(dialog));

        int unreadMessagesCount = getUnreadMsgCount(dialog);
        if (unreadMessagesCount == 0) {
            holder.unreadCounterTextView.setVisibility(View.GONE);
        } else {
            holder.unreadCounterTextView.setVisibility(View.VISIBLE);
            holder.unreadCounterTextView.setText(String.valueOf(unreadMessagesCount > 99 ? 99 : unreadMessagesCount));
        }

        holder.rootLayout.setBackgroundColor(isItemSelected(position) ? Color.parseColor("#e0e0e0") :
                Color.parseColor("#00000000"));

        return convertView;
    }

    private int getUnreadMsgCount(QBChatDialog chatDialog){
        Integer unreadMessageCount = chatDialog.getUnreadMessageCount();
        if (unreadMessageCount == null) {
            return 0;
        } else {
            return unreadMessageCount;
        }
    }

    private boolean isLastMessageAttachment(QBChatDialog dialog) {
        String lastMessage = dialog.getLastMessage();
        Integer lastMessageSenderId = dialog.getLastMessageUserId();
        return TextUtils.isEmpty(lastMessage) && lastMessageSenderId != null;
    }

    private String prepareTextLastMessage(QBChatDialog chatDialog){
        if (isLastMessageAttachment(chatDialog)){
            return context.getString(R.string.chat_attachment);
        } else {
            return chatDialog.getLastMessage();
        }
    }

    private static class ViewHolder {
        ViewGroup rootLayout;
        CircleImageView dialogImageView;
        TextView nameTextView;
        TextView lastMessageTextView;
        TextView unreadCounterTextView;
    }
}
