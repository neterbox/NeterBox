package com.neterbox.qb.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;


import com.neterbox.MyVideoView;
import com.neterbox.R;
import com.neterbox.qb.ChatHelper;
import com.neterbox.qb.PaginationHistoryListener;
import com.neterbox.qb.QbUsersHolder;
import com.neterbox.qb.widget.MaskedImageView;
import com.neterbox.qb.widget.TimeUtils;
import com.neterbox.utils.Constants;
import com.neterbox.utils.Helper;
import com.neterbox.utils.Sessionmanager;
import com.quickblox.chat.model.QBAttachment;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.chat.model.QBDialogType;
import com.quickblox.core.helper.CollectionsUtil;
import com.quickblox.sample.core.ui.adapter.BaseListAdapter;
import com.quickblox.users.model.QBUser;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

import java.text.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;


public class ChatAdapter extends BaseListAdapter<QBChatMessage> implements StickyListHeadersAdapter {

    private static final String TAG = ChatAdapter.class.getSimpleName();
    private final QBChatDialog chatDialog;
    private OnItemInfoExpandedListener onItemInfoExpandedListener;
    private PaginationHistoryListener paginationListener;
    private int previousGetCount = 0;
    Sessionmanager sessionmanager;
    List<QBChatMessage> chatMessages;

    MediaPlayer mp = new MediaPlayer();

    String CopyText, video_url;
    int sdk = Build.VERSION.SDK_INT;
    Uri video;

//    private ImageLoader imgLoader;

//    String image_url=null;

    public ChatAdapter(Context context, QBChatDialog chatDialog, List<QBChatMessage> chatMessages) {
        super(context, chatMessages);
        this.chatDialog = chatDialog;
        this.chatMessages = chatMessages;
        sessionmanager = new Sessionmanager(context);
    }

    public void setOnItemInfoExpandedListener(OnItemInfoExpandedListener onItemInfoExpandedListener) {
        this.onItemInfoExpandedListener = onItemInfoExpandedListener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_item_chat_message, parent, false);

            holder.messageBodyTextView = (TextView) convertView.findViewById(R.id.text_image_message);
            holder.messageAuthorTextView = (TextView) convertView.findViewById(R.id.text_message_author);
            holder.messageContainerLayout = (LinearLayout) convertView.findViewById(R.id.layout_chat_message_container);
            holder.messageBodyContainerLayout = (RelativeLayout) convertView.findViewById(R.id.layout_message_content_container);
            holder.messageInfoTextView = (TextView) convertView.findViewById(R.id.text_message_info);
            holder.attachmentImageView = (MaskedImageView) convertView.findViewById(R.id.image_message_attachment);
            holder.attachmentProgressBar = (ProgressBar) convertView.findViewById(R.id.progress_message_attachment);
            holder.sender_image = (CircleImageView) convertView.findViewById(R.id.sender_image);
            holder.receiver_image = (CircleImageView) convertView.findViewById(R.id.receiver_image);
            holder.seek_bar = (SeekBar) convertView.findViewById(R.id.seek_bar);
            holder.seek_bar.setEnabled(false);
            holder.seek_bar.setClickable(false);
            holder.llContact = (LinearLayout) convertView.findViewById(R.id.llContact);
            holder.ivContact = (ImageView) convertView.findViewById(R.id.ivContact);

            holder.videoView = (ImageView) convertView.findViewById(R.id.videoView);
            holder.llVideo = (LinearLayout) convertView.findViewById(R.id.llVideo);
            holder.llDocument = (LinearLayout) convertView.findViewById(R.id.llDocument);
            holder.documentView = (ImageView) convertView.findViewById(R.id.documentView);
            holder.tv_doctext = (TextView) convertView.findViewById(R.id.tv_doctext);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final QBChatMessage chatMessage = getItem(position);

        setIncomingOrOutgoingMessageAttributes(holder, chatMessage);
        setMessageBody(holder, chatMessage);
        setMessageInfo(chatMessage, holder);
        setMessageAuthor(holder, chatMessage);


        holder.messageBodyTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                CopyText = holder.messageBodyTextView.getText().toString();
                if (CopyText.length() != 0) {
                    if (sdk < Build.VERSION_CODES.HONEYCOMB) {

                        android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                        clipboard.setText(CopyText);
                        Toast.makeText(context, "Text Copied to Clipboard", Toast.LENGTH_SHORT).show();

                    } else {
                        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                        android.content.ClipData clip = android.content.ClipData.newPlainText("Clip", CopyText);
                        Toast.makeText(context, "Text Copied to Clipboard", Toast.LENGTH_SHORT).show();
                        clipboard.setPrimaryClip(clip);
                    }
                } else {
                    Toast.makeText(context, "Nothing to Copy", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        holder.messageContainerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (hasAttachments(chatMessage)) {
//                    Collection<QBAttachment> attachments = chatMessage.getAttachments();
//                    QBAttachment attachment = attachments.iterator().next();
////                    AttachmentImageActivity.start(context, attachment.getUrl());
//                } else {
                toggleItemInfo(holder, position);
            }
//            }
        });

        holder.messageContainerLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (hasAttachments(chatMessage)) {
                    toggleItemInfo(holder, position);
                    return true;
                }

                return false;
            }
        });

        holder.messageInfoTextView.setVisibility(View.GONE);

        if (isIncoming(chatMessage) && !isRead(chatMessage)) {
            readMessage(chatMessage);
        }

        downloadMore(position);

        return convertView;
    }

    @Override
    public int getCount() {
        return chatMessages.size();
    }

    private void downloadMore(int position) {
        if (position == 0) {
            if (getCount() != previousGetCount) {
                paginationListener.downloadMore();
                previousGetCount = getCount();
            }
        }
    }

    Handler seekHandler = new Handler();
    Runnable run;


    HashMap<Integer, QBAttachment> qbAttachmentHashMap = new HashMap<>();

    public void setPaginationHistoryListener(PaginationHistoryListener paginationListener) {
        this.paginationListener = paginationListener;
    }

    private void toggleItemInfo(ViewHolder holder, int position) {
        boolean isMessageInfoVisible = holder.messageInfoTextView.getVisibility() == View.VISIBLE;
        holder.messageInfoTextView.setVisibility(isMessageInfoVisible ? View.GONE : View.VISIBLE);

        if (onItemInfoExpandedListener != null) {
            onItemInfoExpandedListener.onItemInfoExpanded(position);
        }
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.view_chat_message_header, parent, false);
            holder.dateTextView = (TextView) convertView.findViewById(R.id.header_date_textview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        QBChatMessage chatMessage = getItem(position);
        try {
            holder.dateTextView.setText(TimeUtils.getDate(chatMessage.getDateSent() * 1000));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) holder.dateTextView.getLayoutParams();
        if (position == 0) {
            lp.topMargin = 30;
        } else {
            lp.topMargin = 0;
        }
        holder.dateTextView.setLayoutParams(lp);

        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        QBChatMessage chatMessage = getItem(position);
        return TimeUtils.getDateAsHeaderId(chatMessage.getDateSent() * 1000);
    }

    private void setMessageBody(final ViewHolder holder, QBChatMessage chatMessage) {

        Log.e("chatmessage_body", ":" + new Gson().toJson(chatMessage));

        if (hasAttachments(chatMessage)) {
            try {
                Collection<QBAttachment> attachments = chatMessage.getAttachments();
                final QBAttachment attachment = attachments.iterator().next();

                if (attachment.getType().equals(Constants.PHOTO)) {
                    holder.messageBodyTextView.setVisibility(View.GONE);
                    holder.attachmentImageView.setVisibility(View.VISIBLE);
                    holder.attachmentProgressBar.setVisibility(View.VISIBLE);
                    holder.llContact.setVisibility(View.GONE);
                    holder.llVideo.setVisibility(View.GONE);
                    holder.llDocument.setVisibility(View.GONE);
                    holder.documentView.setVisibility(View.GONE);
                    holder.tv_doctext.setVisibility(View.GONE);
//                    holder.attachmentProgressBar.setVisibility(View.GONE);
                    Glide.with(context)
                            .load(attachment.getUrl())
                            .listener(new RequestListener<String, GlideDrawable>() {
                                @Override
                                public boolean onException(Exception e, String model,
                                                           Target<GlideDrawable> target, boolean isFirstResource) {
//                                    Toast.makeText(context, e.getMessage() , Toast.LENGTH_SHORT).show();
                                    holder.attachmentImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                                    holder.attachmentProgressBar.setVisibility(View.GONE);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(GlideDrawable resource, String model,
                                                               Target<GlideDrawable> target,
                                                               boolean isFromMemoryCache, boolean isFirstResource) {
                                    holder.attachmentImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                    holder.attachmentProgressBar.setVisibility(View.GONE);
                                    return false;
                                }
                            })
                            .override(80, 80)
                            .dontTransform()
                            .error(R.drawable.ic_error)
                            .into(holder.attachmentImageView);

                    holder.attachmentImageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Helper.displayPopupImageLarge(context, attachment.getUrl(), (Activity) context);
                        }
                    });
                } else if (attachment.getType().equals(Constants.CONTACT)) {
                    try {
                        holder.llContact.setVisibility(View.VISIBLE);
                        holder.llVideo.setVisibility(View.GONE);
                        holder.messageBodyTextView.setVisibility(View.GONE);
                        holder.attachmentImageView.setVisibility(View.GONE);
                        holder.attachmentProgressBar.setVisibility(View.GONE);
                        holder.llDocument.setVisibility(View.GONE);
                        holder.documentView.setVisibility(View.GONE);
                        holder.tv_doctext.setVisibility(View.GONE);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                /*TODO For Compared Type is Video Or Not*/
                else if (attachment.getType().equals(Constants.VIDEO)) {
                    holder.messageBodyTextView.setVisibility(View.GONE);
                    holder.attachmentImageView.setVisibility(View.GONE);
                    holder.attachmentProgressBar.setVisibility(View.VISIBLE);

                    holder.llVideo.setVisibility(View.VISIBLE);
                    holder.llContact.setVisibility(View.GONE);
                    holder.llDocument.setVisibility(View.GONE);
                    holder.documentView.setVisibility(View.GONE);
                    holder.tv_doctext.setVisibility(View.GONE);

                    new AsyncTask<String, String, String>() {
                        Bitmap bitmapVideo;

                        @Override
                        protected String doInBackground(String... strings) {
                            try {
                                //Your method call here
                                bitmapVideo = retriveVideoFrameFromVideo(strings[0]);
                            } catch (Throwable throwable) {

                                throwable.printStackTrace();
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(String id) {
                            super.onPostExecute(id);
                            if (bitmapVideo != null) {
                                //Load your bitmap here
                                holder.attachmentProgressBar.setVisibility(View.GONE);
                                holder.videoView.setImageBitmap(bitmapVideo);
                            }
                        }
                    }.execute(attachment.getUrl());


                    video = Uri.parse(attachment.getUrl());

//                    if (!attachment.getUrl().startsWith("http://") && !attachment.getUrl().startsWith("https://")) {
//                        video = Uri.parse("http://" + attachment.getUrl());
//                    }
                    video_url = String.valueOf(video);

                    holder.llVideo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                Intent myIntent = new Intent(context, MyVideoView.class);
                                myIntent.setDataAndType(video, "video/mp4/3gp");
                                myIntent.putExtra("video", video_url);
                                context.startActivity(myIntent);
                            } catch (Exception e) {
                                Toast.makeText(context, "No Video Player Found.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

        } else {
            holder.messageBodyTextView.setText(chatMessage.getBody());
            holder.messageBodyTextView.setVisibility(View.VISIBLE);
            holder.attachmentImageView.setVisibility(View.GONE);
            holder.attachmentProgressBar.setVisibility(View.GONE);
            holder.llContact.setVisibility(View.GONE);
            holder.llVideo.setVisibility(View.GONE);
            holder.llDocument.setVisibility(View.GONE);
        }
    }

    Runnable runnable;

    private void playSong(String songPath/*, final ViewHolder holder,boolean isPlaying*/) {
        try {

            mp.prepare();
            mp.start();

        } catch (Exception e) {
            Log.d(TAG, "SecurityException: " + e.getMessage());
        }
    }
    protected void stopPlaying() {
        // If media player is not null then try to stop it
        if (mp != null) {
            mp.pause();
        }
    }

    public static Bitmap retriveVideoFrameFromVideo(String videoPath)
            throws Throwable {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);
            //   mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.getFrameAtTime();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Throwable(
                    "Exception in retriveVideoFrameFromVideo(String videoPath)"
                            + e.getMessage());

        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }

        return bitmap;
    }


    private void setMessageAuthor(ViewHolder holder, QBChatMessage chatMessage) {
        try {
            if (chatDialog.getType() == QBDialogType.GROUP) {
                if (isIncoming(chatMessage)) {
                    holder.receiver_image.setVisibility(View.GONE);

                    QBUser sender = QbUsersHolder.getInstance().getUserById(chatMessage.getSenderId());
                    if (sender.getLogin() != null) {
                        holder.messageAuthorTextView.setText(sender.getLogin());
                    } else {
                        holder.messageAuthorTextView.setText(sender.getEmail());
                    }

                    holder.messageAuthorTextView.setVisibility(View.VISIBLE);

                    if (hasAttachments(chatMessage)) {
                        holder.messageAuthorTextView.setBackgroundResource(R.drawable.shape_rectangle_semi_transparent);
                        holder.messageAuthorTextView.setTextColor(Color.parseColor("#ef3026"));
                    } else {
                        holder.messageAuthorTextView.setBackgroundResource(0);
                        holder.messageAuthorTextView.setTextColor(Color.parseColor("#ef3026"));
                    }
                } else {
                    holder.messageAuthorTextView.setVisibility(View.VISIBLE);
                    holder.messageAuthorTextView.setText("You");
                    holder.messageAuthorTextView.setTextColor(Color.parseColor("#ef3026"));
                    holder.sender_image.setVisibility(View.VISIBLE);
                }
            } else {
                holder.messageAuthorTextView.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void setMessageInfo(QBChatMessage chatMessage, ViewHolder holder) {
        holder.messageInfoTextView.setText(TimeUtils.getTime(chatMessage.getDateSent() * 1000));
    }

    @SuppressLint("RtlHardcoded")
    private void setIncomingOrOutgoingMessageAttributes(ViewHolder holder, QBChatMessage chatMessage) {
        boolean isIncoming = isIncoming(chatMessage);
        int gravity = isIncoming ? Gravity.LEFT : Gravity.RIGHT;
        holder.messageContainerLayout.setGravity(gravity);
        holder.messageInfoTextView.setGravity(gravity);

        int messageBodyContainerBgResource = isIncoming
                ? R.drawable.send_reply___copy
                : R.drawable.chat_send_message;
        if (hasAttachments(chatMessage)) {
//            holder.messageBodyContainerLayout.setBackgroundResource(0);
            holder.messageBodyContainerLayout.setBackgroundResource(messageBodyContainerBgResource);
            holder.messageBodyContainerLayout.setPadding(0, 0, 0, 0);
            holder.attachmentImageView.setMaskResourceId(messageBodyContainerBgResource);
        } else {
            holder.messageBodyContainerLayout.setBackgroundResource(messageBodyContainerBgResource);
        }

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) holder.messageAuthorTextView.getLayoutParams();
        if (isIncoming && hasAttachments(chatMessage)) {
            lp.leftMargin = 12;
            lp.topMargin = 12;
        } else if (isIncoming) {
            lp.leftMargin = 4;
            lp.topMargin = 0;
        }
        holder.messageAuthorTextView.setLayoutParams(lp);

        int textColorResource = isIncoming
                ? R.color.text_color_black
                : R.color.text_color_white;
        if (isIncoming) {
            holder.messageBodyTextView.setTextColor(Color.parseColor("#7B7B7B"));
            holder.receiver_image.setVisibility(View.VISIBLE);
            holder.sender_image.setVisibility(View.GONE);
        } else {
            holder.messageBodyTextView.setTextColor(Color.parseColor("#7B7B7B"));
            holder.sender_image.setVisibility(View.VISIBLE);
            holder.sender_image.setImageResource(R.drawable.dummy);
            holder.receiver_image.setVisibility(View.GONE);
        }
    }

    private boolean hasAttachments(QBChatMessage chatMessage) {
        Collection<QBAttachment> attachments = chatMessage.getAttachments();
        return attachments != null && !attachments.isEmpty();
    }

    private boolean isIncoming(QBChatMessage chatMessage) {
        QBUser currentUser = ChatHelper.getCurrentUser();
        return chatMessage.getSenderId() != null && !chatMessage.getSenderId().equals(currentUser.getId());
    }

    private boolean isRead(QBChatMessage chatMessage) {
        Integer currentUserId = ChatHelper.getCurrentUser().getId();
        return !CollectionsUtil.isEmpty(chatMessage.getReadIds()) && chatMessage.getReadIds().contains(currentUserId);
    }

    private void readMessage(QBChatMessage chatMessage) {
        try {
            chatDialog.readMessage(chatMessage);
        } catch (XMPPException | SmackException.NotConnectedException e) {
            Log.w(TAG, e);
        }
    }

//    private static class HeaderViewHolder {
//        public TextView dateTextView;
//    }

    private static class ViewHolder {
        public TextView messageBodyTextView;
        public TextView messageAuthorTextView, tv_doctext;
        public TextView messageInfoTextView;
        public LinearLayout messageContainerLayout;
        public RelativeLayout messageBodyContainerLayout;
        public MaskedImageView attachmentImageView;
        public CircleImageView receiver_image, sender_image;
        public ProgressBar attachmentProgressBar;
        public TextView dateTextView;
        ;

        public SeekBar seek_bar;
        public ImageView ivContact;
        public LinearLayout llContact;

        ImageView videoView, documentView;
        LinearLayout llVideo, llDocument;

    }

    public interface OnItemInfoExpandedListener {
        void onItemInfoExpanded(int position);
    }
}
