package com.neterbox;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.neterbox.customadapter.AttachmentPreviewAdapter;
import com.neterbox.qb.ChatHelper;
import com.neterbox.qb.PaginationHistoryListener;
import com.neterbox.qb.QbChatDialogMessageListenerImp;
import com.neterbox.qb.QbDialogHolder;
import com.neterbox.qb.VerboseQbChatConnectionListener;
import com.neterbox.qb.adapter.ChatAdapter;
import com.neterbox.qb.widget.AttachmentPreviewAdapterView;
import com.neterbox.utils.Constants;
import com.neterbox.utils.Helper;
import com.neterbox.utils.Sessionmanager;
import com.neterbox.utils.Validators;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.model.QBAttachment;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.QBProgressCallback;
import com.quickblox.core.ServiceZone;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.sample.core.ui.dialog.ProgressDialogFragment;
import com.quickblox.sample.core.utils.Toaster;
import com.quickblox.sample.core.utils.imagepick.ImagePickHelper;
import com.quickblox.users.model.QBUser;
import com.sandrios.sandriosCamera.internal.SandriosCamera;
import com.sandrios.sandriosCamera.internal.configuration.CameraConfiguration;
import com.yalantis.ucrop.util.FileUtils;


import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;


import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class ChatBox extends AppCompatActivity {

    ImageView ileft, button_chat_attachment, ichatyellow, icircle_video, ilocation, icontact, button_chat_send;
    TextView title;
    Sessionmanager sessionmanager;
    CircleImageView image;
    RelativeLayout lpost_upload_option;
    String friendname;
    String friendprofile;
    Button blocation_share;
    String group_name, group_pic;

    Context context;
    LinearLayout layout_chat_send_container, ll_chat;
    int sdk = Build.VERSION.SDK_INT;

    private String image_path = "", cv_path = "", pasteText;
    public static final int GALLARY_REQUEST = 2;
    public static final int CAMERA_REQUEST = 2;
    public static final int VIDEO_REQUEST = 3;

    File myFile;
    // TODO : CONTACT IMPLEMENTATION

    private static final int REQUEST_CODE_PICK_CONTACTS = 1;
    private Uri uriContact;
    private String contactID;

    // TODO : Chat implementation

    private static final String TAG = ChatBox.class.getSimpleName();
    private static final int REQUEST_CODE_ATTACHMENT = 721;
    private static final int REQUEST_CODE_SELECT_PEOPLE = 752;

    private static final String PROPERTY_SAVE_TO_HISTORY = "save_to_history";

    public static final String EXTRA_DIALOG_ID = "dialogId";

    private ProgressBar progressBar;
    private StickyListHeadersListView messagesListView;
    private EditText messageEditText;

    private ChatAdapter chatAdapter;
    private AttachmentPreviewAdapter attachmentPreviewAdapter;
    private ConnectionListener chatConnectionListener;

    private LinearLayout attachmentPreviewContainerLayout;
    private Snackbar snackbar;

    private QBChatDialog qbChatDialog;
    private ArrayList<QBChatMessage> unShownMessages;
    private int skipPagination = 0;
    private ChatMessageListener chatMessageListener;

    public static void startForResult(Activity activity, int code, QBChatDialog dialogId, String full_name, String pic) {
        Intent intent = new Intent(activity, ChatBox.class);
        intent.putExtra(ChatBox.EXTRA_DIALOG_ID, dialogId);
        intent.putExtra("full_name", full_name);
        intent.putExtra("Picture", pic);
        Log.e(TAG, "deserialized dialog = " + dialogId);
        activity.startActivityForResult(intent, code);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);
        context = this;
        sessionmanager = new Sessionmanager(this);
        friendname = getIntent().getStringExtra("full_name");
        friendprofile = getIntent().getStringExtra("Picture");

        idMapping();
        Listener();

        title.setText(friendname);
        Glide.with(context).load(friendprofile).placeholder(R.drawable.dummy).into(image);

        Helper.checkStoragePermission(context);
        Helper.requestAudioPermissions(context);
        Helper.checkCameraPermission(context);

        // TODO : LOCATION IMPLEMENTATION

//        if (!hasGps()) {
//            Log.d(TAG, "This hardware doesn't have GPS.");
//        }


        // TODO : Chat implementation

        QBSettings.getInstance().fastConfigInit(getResources().getString(R.string.application_id),
                getResources().getString(R.string.authorization_key), getResources().getString(R.string.authorization_secret));
        QBSettings.getInstance().setAccountKey(getResources().getString(R.string.account_key));
        QBSettings.getInstance().init(context, getResources().getString(R.string.application_id),
                getResources().getString(R.string.authorization_key), getResources().getString(R.string.authorization_secret));
        QBSettings.getInstance().setEndpoints("https://api.quickblox.com", "chat.quickblox.com", ServiceZone.PRODUCTION);
        QBSettings.getInstance().setZone(ServiceZone.PRODUCTION);
        try {
            qbChatDialog = (QBChatDialog) getIntent().getSerializableExtra(EXTRA_DIALOG_ID);
            Log.v(TAG, "deserialized dialog = " + qbChatDialog);
            qbChatDialog.initForChat(QBChatService.getInstance());

            QBChatService.setDebugEnabled(true); // enable chat logging

            chatMessageListener = new ChatMessageListener();

            qbChatDialog.addMessageListener(chatMessageListener);

            initViews();
            initChat();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void idMapping() {
        ileft = (ImageView) findViewById(R.id.ileft);
//        iright = (ImageView) findViewById(R.id.iright);
        title = (TextView) findViewById(R.id.title);
        ichatyellow = (ImageView) findViewById(R.id.ichatyellow);
        icircle_video = (ImageView) findViewById(R.id.icircle_video);
        icontact = (ImageView) findViewById(R.id.icontact);
        ilocation = (ImageView) findViewById(R.id.ilocation);
        button_chat_attachment = (ImageView) findViewById(R.id.button_chat_attachment);
        button_chat_send = (ImageView) findViewById(R.id.button_chat_send);
        lpost_upload_option = (RelativeLayout) findViewById(R.id.lpost_upload_option);
        layout_chat_send_container = (LinearLayout) findViewById(R.id.layout_chat_send_container);
        messagesListView = (StickyListHeadersListView) findViewById(R.id.list_chat_messages);
        messageEditText = (EditText) findViewById(R.id.edit_chat_message);
        blocation_share = (Button) findViewById(R.id.blocation_share);
        progressBar = (ProgressBar) findViewById(R.id.progress_chat);
        attachmentPreviewContainerLayout = (LinearLayout) findViewById(R.id.layout_attachment_preview_container);
        image = (CircleImageView) findViewById(R.id.image);
        ileft.setImageResource(R.drawable.back);
//        iright.setVisibility(View.GONE);
    }

    //TODO For Check Validations
    private boolean validationChecked() {
        if (Validators.isEmpty(messageEditText.getText().toString())) {
            Helper.showToastMessage(context, getString(R.string.empty_message));
            return false;
        }
        return true;
    }

    public void Listener() {
        ileft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChatBox.this, ChatModule.class);
                startActivity(i);
                finish();
            }
        });

        ilocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChatBox.this, Activiy_Map.class);
                startActivity(i);
                finish();
            }
        });


        messageEditText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                // TODO Auto-generated method stub
                if (sdk < Build.VERSION_CODES.HONEYCOMB) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    pasteText = clipboard.getText().toString();
                    messageEditText.setText(pasteText);

                } else {

                    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    if (clipboard.hasPrimaryClip() == true) {
                        ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                        pasteText = item.getText().toString();
                        messageEditText.setText(pasteText);

                    } else {

//                        Toast.makeText(getApplicationContext(), "Nothing to Paste", Toast.LENGTH_SHORT).show();

                    }
                }
                return false;
            }
        });

        button_chat_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onSendChatClick(messageEditText.getText().toString().trim());
            }
        });

        icontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSelectContact(v);
            }
        });

        ilocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChatBox.this, Activiy_Map.class);
                startActivity(i);
            }
        });


        button_chat_attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    layout_chat_send_container.setVisibility(View.GONE);
                    lpost_upload_option.setVisibility(View.VISIBLE);
                    if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    } else {
                        /*TODO For Storage & Camera Permission*/
                        Helper.checkStoragePermission(context);
                        Helper.checkCameraPermission(context);
                    }
                } else {

                    showPictureDialog();

                }
            }
        });

        ichatyellow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        layout_chat_send_container.setVisibility(View.VISIBLE);
                        lpost_upload_option.setVisibility(View.GONE);

                        showPictureDialog();


                    } else {
                        //Request Location Permission
                        Helper.checkStoragePermission(context);
                        Helper.checkCameraPermission(context);

                    }
                } else {
                    showPictureDialog();
                }
            }
        });

        icircle_video.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        layout_chat_send_container.setVisibility(View.VISIBLE);
                        lpost_upload_option.setVisibility(View.GONE);


                        takeVideo();

                    } else {
                        //Request Location Permission
                        Helper.checkStoragePermission(context);
                        Helper.requestAudioPermissions(context);
                        Helper.checkCameraPermission(context);
                    }
                } else {
                    takeVideo();
                }
            }
        });
    }


    /*
    @Override
    public void onBackPressed() {
        Intent i=new Intent(context,ChatModule.class);
        startActivity(i);
        finish();
    }*/


    // TODO : Chat Implementation

    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        if (qbChatDialog != null) {
            outState.putString(EXTRA_DIALOG_ID, qbChatDialog.getDialogId());
        }
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (qbChatDialog == null) {
            qbChatDialog = QbDialogHolder.getInstance().getChatDialogById(savedInstanceState.getString(EXTRA_DIALOG_ID));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ChatHelper.getInstance().addConnectionListener(chatConnectionListener);

        if (!Constants.shareLoc.equalsIgnoreCase("")) {
            onSendChatClick(Constants.shareLoc);
            Constants.shareLoc = "";
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        ChatHelper.getInstance().removeConnectionListener(chatConnectionListener);
    }

    @Override
    public void onBackPressed() {
//        releaseChat();
//        sendDialogId();
        Intent it = new Intent(context, ChatModule.class);
        startActivity(it);
        finish();
//        super.onBackPressed();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_chat, menu);
//
//        MenuItem menuItemDelete = menu.findItem(R.id.menu_chat_action_delete);
//        if (qbChatDialog.getType() == QBDialogType.PRIVATE) {
//            menuItemDelete.setVisible(true);
//        } else {
//            menuItemDelete.setVisible(false);
//        }
//
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_chat_action_info:
                ChatInfoActivity.start(this, qbChatDialog);
                return true;

            case R.id.menu_chat_action_delete:
                deleteChat();
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    private void releaseChat() {
//        qbChatDialog.removeMessageListrener(chatMessageListener);
//        if (!QBDialogType.PRIVATE.equals(qbChatDialog.getType())) {
//
//        }
//    }

    private void sendDialogId() {
        Intent result = new Intent();
        result.putExtra(EXTRA_DIALOG_ID, qbChatDialog.getDialogId());
        setResult(RESULT_OK, result);
    }

    public View getSnackbarAnchorView() {
        return findViewById(R.id.list_chat_messages);
    }

//    @SuppressWarnings("unchecked")
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            if (requestCode == REQUEST_CODE_SELECT_PEOPLE) {
//               /* ArrayList<QBUser> selectedUsers = (ArrayList<QBUser>) data.getSerializableExtra(
//                        SelectUsersActivity.EXTRA_QB_USERS);*/
//                Toast.makeText(context, "Select User for Making Group", Toast.LENGTH_SHORT).show();
////                 updateDialog(selectedUsers);
//            }
//        }
//    }

//    @Override
//    public void onImagePicked(int requestCode, File file) {
//        switch (requestCode) {
//            case REQUEST_CODE_ATTACHMENT:
//                AttachmentPreviewAdapter.add(file);
//                break;
//        }
//    }

   /* @Override
    public void onImagePickError(int requestCode, Exception e) {
        showErrorSnackbar(0, e, null);
    }

    private Snackbar showErrorSnackbar(@StringRes int resId, Exception e,
                                       View.OnClickListener clickListener) {
        return ErrorUtils.showSnackbar(getSnackbarAnchorView(), resId, e,
                com.quickblox.sample.core.R.string.dlg_retry, clickListener);
    }
*/
//    @Override
//    public void onImagePickClosed(int requestCode) {
//    }


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


    public void onAttachmentsClick(View view) {
        new ImagePickHelper().pickAnImage(this, REQUEST_CODE_ATTACHMENT);
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

    private void initViews() {

        attachmentPreviewAdapter = new AttachmentPreviewAdapter(this,
                new AttachmentPreviewAdapter.OnAttachmentCountChangedListener() {
                    @Override
                    public void onAttachmentCountChanged(int count) {
                        attachmentPreviewContainerLayout.setVisibility(count == 0 ? View.GONE : View.VISIBLE);
                    }
                },
                new AttachmentPreviewAdapter.OnAttachmentUploadErrorListener() {
                    @Override
                    public void onAttachmentUploadError(QBResponseException e) {
//                        showErrorSnackbar(0, e, new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                onAttachmentsClick(v);
//                            }
//                        });
                    }
                });
        AttachmentPreviewAdapterView previewAdapterView = findViewById(R.id.adapter_view_attachment_preview);
        previewAdapterView.setAdapter(attachmentPreviewAdapter);
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

            Helper.showToastMessage(context, "Can't send a message, You are not connected to chat");

            finish();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

//    public void updateDialog(final ArrayList<QBUser> selectedUsers) {
//        ChatHelper.getInstance().updateDialogUsers(qbChatDialog, selectedUsers, new QBEntityCallback<QBChatDialog>() {
//                    @Override
//                    public void onSuccess(QBChatDialog dialog, Bundle args) {
//                        qbChatDialog = dialog;
//                        loadDialogUsers();
//                    }
//
//                    @Override
//                    public void onError(QBResponseException e) {
//                        showErrorSnackbar(R.string.chat_info_add_people_error, e, new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                updateDialog(selectedUsers);
//                            }
//                        });
//                    }
//                }
//        );}

    private void loadChatHistory() {
        try {
            ChatHelper.getInstance().loadChatHistory(qbChatDialog, skipPagination, new QBEntityCallback<ArrayList<QBChatMessage>>() {
                @Override
                public void onSuccess(ArrayList<QBChatMessage> messages, Bundle args) {
                    // The newest messages should be in the end of list,
                    // so we need to reverse list to show messages in the right order

                    try {
                        Collections.reverse(messages);
                        if (chatAdapter == null) {
                            chatAdapter = new ChatAdapter(context, qbChatDialog, messages);
                            chatAdapter.setPaginationHistoryListener(new PaginationHistoryListener() {
                                @Override
                                public void downloadMore() {
                                    loadChatHistory();
                                }
                            });
                            chatAdapter.setOnItemInfoExpandedListener(new ChatAdapter.OnItemInfoExpandedListener() {
                                @Override
                                public void onItemInfoExpanded(final int position) {
                                    if (isLastItem(position)) {
                                        // HACK need to allow info textview visibility change so posting it via handler
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                messagesListView.setSelection(position);
                                            }
                                        });
                                    } else {
                                        messagesListView.smoothScrollToPosition(position);
                                    }
                                }

                                private boolean isLastItem(int position) {
                                    return position == chatAdapter.getCount() - 1;
                                }
                            });
                            if (unShownMessages != null && !unShownMessages.isEmpty()) {
                                List<QBChatMessage> chatList = chatAdapter.getList();
                                for (QBChatMessage message : unShownMessages) {
                                    if (!chatList.contains(message)) {
                                        chatAdapter.add(message, null);
                                    }
                                }
                            }
                            Log.e("Chat Detail :- ", String.valueOf(chatAdapter));
                            messagesListView.setAdapter(chatAdapter);
                            messagesListView.setAreHeadersSticky(false);
                            messagesListView.setDivider(null);
                        } else {
                            chatAdapter.addList(messages);
                            messagesListView.setSelection(messages.size());
                        }
                        progressBar.setVisibility(View.GONE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onError(QBResponseException e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                    skipPagination -= ChatHelper.CHAT_HISTORY_ITEMS_PER_PAGE;
                }
            });
            skipPagination += ChatHelper.CHAT_HISTORY_ITEMS_PER_PAGE;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void loadDialogUsers() {
        ChatHelper.getInstance().getUsersFromDialog(qbChatDialog, new QBEntityCallback<ArrayList<QBUser>>() {
            @Override
            public void onSuccess(ArrayList<QBUser> users, Bundle bundle) {
                loadChatHistory();
            }

            @Override
            public void onError(QBResponseException e) {
//                showErrorSnackbar(R.string.chat_load_users_error, e,
//                        new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                loadDialogUsers();
//                            }
//                        });

                Log.e("Chat Error :", "Load chat history error");
            }
        });
    }

    private void scrollMessageListDown() {
        messagesListView.setSelection(messagesListView.getCount() - 1);
    }

    private void deleteChat() {
        ChatHelper.getInstance().deleteDialog(qbChatDialog, new QBEntityCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid, Bundle bundle) {
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onError(QBResponseException e) {
//                showErrorSnackbar(R.string.dialogs_deletion_error, e,
//                        new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                deleteChat();
//                            }
//                        });
            }
        });
    }

    private void initChat() {
        switch (qbChatDialog.getType()) {

            case PRIVATE:
                loadDialogUsers();
                break;

            default:
                Toaster.shortToast(String.format("%s %s", getString(R.string.chat_unsupported_type), qbChatDialog.getType().name()));
                finish();
                break;
        }
    }

    private void initChatConnectionListener() {
        chatConnectionListener = new VerboseQbChatConnectionListener(getSnackbarAnchorView(), context) {
            @Override
            public void reconnectionSuccessful() {
                super.reconnectionSuccessful();
                skipPagination = 0;
                switch (qbChatDialog.getType()) {
                    case GROUP:
                        chatAdapter = null;
                        // Join active room if we're in Group Chat
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            }
                        });
                        break;
                }
            }
        };
    }

    public class ChatMessageListener extends QbChatDialogMessageListenerImp {
        @Override
        public void processMessage(String s, QBChatMessage qbChatMessage, Integer integer) {
            showMessage(qbChatMessage);
        }
    }

    public void onClickSelectContact(View btnSelectContact) {
        startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_CONTACTS && resultCode == RESULT_OK) {
            Log.d(TAG, "Response: " + data.toString());
            uriContact = data.getData();
        } else if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        //Todo select Images /
        else if (requestCode == GALLARY_REQUEST) {
            if (data != null) {
                Uri selectedImage = data.getData();
                image_path = FileUtils.getPath(context, selectedImage);

                File f = new File(image_path);
                ChatHelper.getInstance().loadFileAsAttachment(f, new QBEntityCallback<QBAttachment>() {
                    @Override
                    public void onSuccess(QBAttachment result, Bundle params) {
                        sendChatMessage(null, result);
                    }

                    @Override
                    public void onError(QBResponseException e) {
                        Log.e("error", e.getMessage());
                    }
                }, new QBProgressCallback() {
                    @Override
                    public void onProgressUpdate(final int progress) {

                    }
                });
            }
        }
        //Todo Capture Images /
        else if (requestCode == CAMERA_REQUEST) {
//            hidden_panel.setVisibility(View.GONE);
            String filePath = data.getStringExtra(CameraConfiguration.Arguments.FILE_PATH);
            myFile = new File(filePath);

            ChatHelper.getInstance().loadFileAsAttachment(myFile, new QBEntityCallback<QBAttachment>() {
                @Override
                public void onSuccess(QBAttachment result, Bundle params) {
                    sendChatMessage(null, result);
                }

                @Override
                public void onError(QBResponseException e) {
                    Log.e("error", e.getMessage());
                }
            }, new QBProgressCallback() {
                @Override
                public void onProgressUpdate(final int progress) {

                }
            });
        }
        //TODO Capture Video
        else if (requestCode == VIDEO_REQUEST) {
            // Log.e("File", "" + data.getStringExtra(CameraConfiguration.Arguments.FILE_PATH));
            //   Toast.makeText(context, filePath, Toast.LENGTH_SHORT).show();

            String filePath = data.getStringExtra(CameraConfiguration.Arguments.FILE_PATH);
            myFile = new File(filePath);
            ChatHelper.getInstance().loadFileAsAttachmentVideo(myFile, new QBEntityCallback<QBAttachment>() {
                @Override
                public void onSuccess(QBAttachment result, Bundle params) {
                    sendChatMessage(null, result);
                }

                @Override
                public void onError(QBResponseException e) {
                    Log.e("error", e.getMessage());
                }
            }, new QBProgressCallback() {
                @Override
                public void onProgressUpdate(final int progress) {

                }
            });

        }
//                if (requestCode == PLACE_PICKER_REQUEST) {
//                    if (resultCode == RESULT_OK) {
//                        Place place = PlacePicker.getPlace(data, context);
//                        String toastMsg = String.format("Place: %s", place.getName());
//                        Toast.makeText(context, toastMsg, Toast.LENGTH_LONG).show();
//                        title.setText((CharSequence) data);
//                    }
//
//                }
    }


//    private boolean hasGps() {
//        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
//    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");

        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Toast.makeText(context, "choose photo from gallary", Toast.LENGTH_SHORT).show();
        startActivityForResult(galleryIntent, GALLARY_REQUEST);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST);

        Toast.makeText(context, "takePhotoFromCamera", Toast.LENGTH_SHORT).show();
    }

    private void takeVideo() {
        new SandriosCamera(ChatBox.this, VIDEO_REQUEST)
                .setShowPicker(true)
                .setShowPickerType(CameraConfiguration.VIDEO)
                .setVideoFileSize(80)
                .setMediaAction(CameraConfiguration.MEDIA_ACTION_BOTH)
                .enableImageCropping(true)
                .launchCamera();
    }
}
