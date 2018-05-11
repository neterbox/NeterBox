package com.neterbox;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.neterbox.customadapter.AttachmentPreviewAdapter;
import com.neterbox.jsonpojo.chatlist.ChatListDatum;
import com.neterbox.jsonpojo.friend_list.FriendListDatum;
import com.neterbox.qb.ChatHelper;
import com.neterbox.qb.PaginationHistoryListener;
import com.neterbox.qb.QbChatDialogMessageListenerImp;
import com.neterbox.qb.QbDialogHolder;
import com.neterbox.qb.QbDialogUtils;
import com.neterbox.qb.VerboseQbChatConnectionListener;
import com.neterbox.qb.adapter.ChatAdapter;
import com.neterbox.qb.widget.AttachmentPreviewAdapterView;
import com.neterbox.utils.Constants;
import com.neterbox.utils.Helper;
import com.neterbox.utils.Sessionmanager;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.model.QBAttachment;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.chat.model.QBDialogType;
import com.quickblox.content.QBContent;
import com.quickblox.content.model.QBFile;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.ServiceZone;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.sample.core.ui.dialog.ProgressDialogFragment;
import com.quickblox.users.model.QBUser;
import com.sandrios.sandriosCamera.internal.SandriosCamera;
import com.sandrios.sandriosCamera.internal.configuration.CameraConfiguration;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


public class ChatBox extends AppCompatActivity {

    private static final String TAG = ChatBox.class.getSimpleName();

    private final int MY_PERMISSIONS_RECORD_AUDIO = 1;
    private static final int CAPTURE_MEDIA = 368;

    ImageView ileft, ivActionImage, button_chat_send, button_chat_attachment, ichatyellow, ichatvideo, ilocation, icontact, button_chat_smiley;
    TextView title;
    ImageView image;
    ChatListDatum chatListDatum;

    ChatBox chatActivity;

    RelativeLayout llParentMain;
    LinearLayout layout_chat_send_container;

    private static final int REQUEST_CODE_SELECT_PEOPLE = 752;

    private static final String PROPERTY_SAVE_TO_HISTORY = "save_to_history";

    public static final String EXTRA_DIALOG_ID = "dialogId";

    private ProgressBar progressBar;
    private StickyListHeadersListView messagesListView;
    private EditText messageEditText;

    private LinearLayout attachmentPreviewContainerLayout;
    private ChatAdapter chatAdapter;
    private AttachmentPreviewAdapter attachmentPreviewAdapter;
    private ConnectionListener chatConnectionListener;

    private QBChatDialog qbChatDialog;
    private ArrayList<QBChatMessage> unShownMessages;
    private int skipPagination = 0;
    private ChatMessageListener chatMessageListener;
    LinearLayout hidden_panel_sugegstion;
    RelativeLayout lpost_upload_option;

    RecyclerView rvSuggetsionList;

    FriendListDatum dtUser;
    LinearLayout llChatMsgs;
    String friendname;
    String friendprofile;
    Sessionmanager sessionmanager;
    String group_name, group_pic;
    Button blocation_share;

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
        chatActivity = this;
        sessionmanager = new Sessionmanager(this);
        dtUser = new FriendListDatum();
        chatListDatum = new ChatListDatum();
        idMappings();
        Listeners();
        image.setVisibility(View.INVISIBLE);
        ivActionImage.setImageResource(R.drawable.side_menu_new);
        checkStoragePermission();
        requestAudioPermissions();
        checkCameraPermission();
        title.setText("Chat");

        friendname = getIntent().getStringExtra("full_name");
        friendprofile = getIntent().getStringExtra("Picture");

        title.setText(friendname);
        Glide.with(chatActivity).load(friendprofile).placeholder(R.drawable.dummy).into(image);


        try {
            QBSettings.getInstance().fastConfigInit(getResources().getString(R.string.application_id),
                    getResources().getString(R.string.authorization_key), getResources().getString(R.string.authorization_secret));
            QBSettings.getInstance().setAccountKey(getResources().getString(R.string.account_key));
            QBSettings.getInstance().init(chatActivity, getResources().getString(R.string.application_id),
                    getResources().getString(R.string.authorization_key), getResources().getString(R.string.authorization_secret));
            QBSettings.getInstance().setEndpoints("https://api.quickblox.com", "chat.quickblox.com", ServiceZone.PRODUCTION);
            QBSettings.getInstance().setZone(ServiceZone.PRODUCTION);
            try {
                if (Helper.checkSignIn()) {

                    qbChatDialog = (QBChatDialog) getIntent().getSerializableExtra(EXTRA_DIALOG_ID);

                    Log.v(TAG, "deserialized dialog = " + qbChatDialog);

                    qbChatDialog.initForChat(QBChatService.getInstance());

                    chatMessageListener = new ChatMessageListener();

                    qbChatDialog.addMessageListener(chatMessageListener);

                    initChatConnectionListener();

                    initViews();
                    initChat();

                    if (qbChatDialog.getType() == QBDialogType.PRIVATE) {
                        image.setVisibility(View.INVISIBLE);
                    } else {
                        image.setVisibility(View.VISIBLE);
                    }

                } else {
                    Helper.hideSoftKeyboard(chatActivity);
                    Helper.showSnackBar(llParentMain, getResources().getString(R.string.session_expired));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {

        }
    }

    private void Listeners() {

        ivActionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(chatActivity, v);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.poupup_menu_chat, popup.getMenu());
                Menu popupMenu = popup.getMenu();
                if ((chatListDatum.getTblDailog().getDialogType().equals("group"))) {
                    popupMenu.findItem(R.id.add_people).setVisible(true);
                } else {
                    popupMenu.findItem(R.id.add_people).setVisible(false);

                }

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.add_people:
                                Create_group.startForResult(chatActivity, REQUEST_CODE_SELECT_PEOPLE, qbChatDialog);
                                break;
                            case R.id.leave_chat:
                                leaveGroupChat();
                                break;
                        }

                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        });

        ileft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                releaseChat();
                sendDialogId();

                Intent i = new Intent(chatActivity, ChatModule.class);
                startActivity(i);
                finish();
            }
        });


        button_chat_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSendChatClick(messageEditText.getText().toString().trim());
            }
        });

        button_chat_attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lpost_upload_option.setVisibility(View.VISIBLE);

            }
        });

        ichatyellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hidden_panel_sugegstion.setVisibility(View.GONE);
                lpost_upload_option.setVisibility(View.GONE);

                onAttachmentsClick(view);
            }
        });

        icontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hidden_panel_sugegstion.setVisibility(View.GONE);
                lpost_upload_option.setVisibility(View.GONE);

                onAttachmentsClick(view);
            }
        });

        ilocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hidden_panel_sugegstion.setVisibility(View.GONE);
                lpost_upload_option.setVisibility(View.GONE);
                        Intent i = new Intent(ChatBox.this, share_location_customview.class);
//                Intent i = new Intent(ChatBox.this, Activiy_Map.class);
                        startActivity(i);
                        finish();
                onAttachmentsClick(view);
            }
        });

        ichatvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hidden_panel_sugegstion.setVisibility(View.GONE);
                lpost_upload_option.setVisibility(View.GONE);

                onAttachmentsClick(view);
            }
        });

        icontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   onClickSelectContact(v);
                Intent i = new Intent(ChatBox.this, share_contact.class);
                startActivity(i);
            }
        });

        ilocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChatBox.this, Activiy_Map.class);
                startActivity(i);
            }
        });


    }

    private void idMappings() {

        progressBar = (ProgressBar) findViewById(R.id.progress_chat);
        lpost_upload_option = (RelativeLayout) findViewById(R.id.lpost_upload_option);
        hidden_panel_sugegstion = (LinearLayout) findViewById(R.id.hidden_panel_sugegstion);
        image = (ImageView) findViewById(R.id.image);
        ileft = (ImageView) findViewById(R.id.ileft);
        ivActionImage = (ImageView) findViewById(R.id.ivActionImage);
        title = (TextView) findViewById(R.id.title);
        layout_chat_send_container = (LinearLayout) findViewById(R.id.layout_chat_send_container);
        llParentMain = (RelativeLayout) findViewById(R.id.llParentMain);
        button_chat_send = (ImageView) findViewById(R.id.button_chat_send);
        button_chat_attachment = (ImageView) findViewById(R.id.button_chat_attachment);
        button_chat_smiley = (ImageView) findViewById(R.id.button_chat_smiley);
        icontact = (ImageView) findViewById(R.id.icontact);
        blocation_share = (Button) findViewById(R.id.blocation_share);
        ilocation = (ImageView) findViewById(R.id.ilocation);
        ichatyellow = (ImageView) findViewById(R.id.ichatyellow);
        ichatvideo = (ImageView) findViewById(R.id.ichatvideo);
        rvSuggetsionList = (RecyclerView) findViewById(R.id.rvSuggetsionList);
    }

    private void checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(chatActivity, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) chatActivity,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new android.support.v7.app.AlertDialog.Builder(chatActivity)
                        .setTitle("Storage Permission Needed")
                        .setMessage("This app needs the Storage permission, please accept to use storage functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions((Activity) chatActivity,
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        Constants.MY_PERMISSIONS_REQUEST_STORAGE);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions((Activity) chatActivity,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Constants.MY_PERMISSIONS_REQUEST_STORAGE);
            }
        }
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(chatActivity, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
                ) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) chatActivity,
                    Manifest.permission.CAMERA)
                    ) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new android.support.v7.app.AlertDialog.Builder(chatActivity)
                        .setTitle("Storage Permission Needed")
                        .setMessage("This app needs the Storage permission, please accept to use storage functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions((Activity) chatActivity,
                                        new String[]{Manifest.permission.CAMERA},
                                        Constants.MY_PERMISSIONS_REQUEST_CAMERA);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions((Activity) chatActivity,
                        new String[]{Manifest.permission.CAMERA},
                        Constants.MY_PERMISSIONS_REQUEST_CAMERA);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case Constants.MY_PERMISSIONS_REQUEST_STORAGE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        ) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {

                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
//                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }


                break;

            case MY_PERMISSIONS_RECORD_AUDIO: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Permissions Denied to record audio", Toast.LENGTH_LONG).show();
                }
                break;


                // other 'case' lines to check for other
                // permissions this app might request
            }
            case Constants.MY_PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Permissions Denied to record audio", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    public void onAttachmentsClick(View view) {
        new SandriosCamera(chatActivity, CAPTURE_MEDIA)
                .setShowPicker(false)
                .setMediaAction(CameraConfiguration.MEDIA_ACTION_BOTH)
                .enableImageCropping(false)
                .launchCamera();
    }

    public void openAttachmentSnackBar(View view) {
        if (Helper.isConnectingToInternet(chatActivity)) {
            Helper.slideUpDown(chatActivity, lpost_upload_option);
        } else {
            Toast.makeText(chatActivity, "No Network", Toast.LENGTH_LONG).show();

        }
    }

    @Override
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
    }

    @Override
    protected void onPause() {
        super.onPause();
        ChatHelper.getInstance().removeConnectionListener(chatConnectionListener);
    }


    private void sendDialogId() {
        Intent result = new Intent();
        result.putExtra(EXTRA_DIALOG_ID, qbChatDialog.getDialogId());
        setResult(RESULT_OK, result);
    }

    private void leaveGroupChat() {
        ProgressDialogFragment.show(getSupportFragmentManager());
        ChatHelper.getInstance().exitFromDialog(qbChatDialog, new QBEntityCallback<QBChatDialog>() {
            @Override
            public void onSuccess(QBChatDialog qbDialog, Bundle bundle) {
                ProgressDialogFragment.hide(getSupportFragmentManager());
                QbDialogHolder.getInstance().deleteDialog(qbDialog);
                Intent i = new Intent(chatActivity, ChatModule.class);
                startActivity(i);
                finish();
            }

            @Override
            public void onError(QBResponseException e) {
                ProgressDialogFragment.hide(getSupportFragmentManager());

                Helper.showSnackBar(llParentMain, "Something went wrong");
                Intent i = new Intent(chatActivity, ChatModule.class);
                startActivity(i);
                finish();


            }
        });
    }

//    private Map<File, QBAttachment> fileQBAttachmentMap;
//    private Map<File, Integer> fileUploadProgressMap;
//
//    private AttachmentPreviewAdapter.OnAttachmentCountChangedListener onAttachmentCountChangedListener;
//    private AttachmentPreviewAdapter.OnAttachmentUploadErrorListener onAttachmentUploadErrorListener;
//    private static final Handler mainThreadHandler = new Handler(Looper.getMainLooper());


    @SuppressWarnings("unchecked")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_PEOPLE) {
            if (resultCode == RESULT_OK) {
                group_name = data.getStringExtra("group_name");
                group_pic = data.getStringExtra("group_pic");

                ArrayList<QBUser> selectedUsers = (ArrayList<QBUser>) data.getSerializableExtra(
                        Create_group.EXTRA_QB_USERS);

                updateDialog(selectedUsers, group_name, group_pic);
            }

        }
//        else // handle result of CropImageActivity
        else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            try {
                final CropImage.ActivityResult result = CropImage.getActivityResult(data);

                if (resultCode == RESULT_OK && result.getUri() != null && result.getUri() != null) {


                    Uri uri =/*data.getData()*/ result.getUri();
                    final File myFile = new File(uri.getPath());

//                        Uri selectedImageUri = getImageContentUri(chatActivity, myFile);

                    attachmentPreviewAdapter.add(myFile, Constants.PHOTO);


                    //                uploadFile(selectedImageUri);
                    //                Toast.makeText(this, "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG).show();
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Toast.makeText(chatActivity, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(chatActivity, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (requestCode == CAPTURE_MEDIA && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(CameraConfiguration.Arguments.FILE_PATH);
            String extension = filePath.substring(filePath.lastIndexOf("."));
            if (extension.equalsIgnoreCase(".jpg")) {
                final File myFile = new File(filePath);
                attachmentPreviewAdapter.add(myFile, Constants.PHOTO);
                QBContent.uploadFileTask(myFile, true, null).performAsync(new QBEntityCallback<QBFile>() {
                    @Override
                    public void onSuccess(QBFile qbFile, Bundle bundle) {
                        qbChatDialog.setPhoto(qbFile.getId().toString());
                    }

                    @Override
                    public void onError(QBResponseException e) {

                    }
                });


            } else {
                final File myFile = new File(filePath);
                attachmentPreviewAdapter.add(myFile, Constants.VIDEO);
            }
//            Toast.makeText(this, "Media captured.", Toast.LENGTH_SHORT).show();
        }
    }



    public void onSelectImageClick(Uri imageUri) {

        Intent intent = CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .getIntent(chatActivity);
        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
//
//        CropImage.activity()
//                .setGuidelines(CropImageView.Guidelines.ON)
//                .setActivityTitle("My Crop")
//                .setCropShape(CropImageView.CropShape.RECTANGLE)
//                .setRequestedSize(400, 400)
//                .start((Activity) chatActivity);
    }

    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }


    public void onSendChatClick(String text) {

        if (!TextUtils.isEmpty(text)) {
            sendChatMessage(text, null);
        }
        else
        {
            int totalAttachmentsCount = attachmentPreviewAdapter.getCount();
            Collection<QBAttachment> uploadedAttachments = attachmentPreviewAdapter.getUploadedAttachments();
            if (!uploadedAttachments.isEmpty()) {
                if (uploadedAttachments.size() == totalAttachmentsCount) {
                    for (QBAttachment attachment : uploadedAttachments) {
                        sendChatMessage(null, attachment);
                    }
                } else {
                    Helper.showSnackBar(llParentMain, getResources().getString(R.string.chat_wait_for_attachments_to_upload));
                }
            }
        }

    }

//    public void onAttachmentsClick() {
//        new ImagePickHelper().pickAnImage(this, REQUEST_CODE_ATTACHMENT);
//    }

    public void showMessage(QBChatMessage message) {
        if (chatAdapter != null)
        {
            chatAdapter.add(message, null);
            scrollMessageListDown();
        }
        else
        {
            if (unShownMessages == null)
            {
                unShownMessages = new ArrayList<>();
            }
            unShownMessages.add(message);
        }
    }

    private void initViews() {

        messagesListView = (StickyListHeadersListView) findViewById(R.id.list_chat_messages);
        messageEditText = (EditText) findViewById(R.id.edit_chat_message);
        attachmentPreviewContainerLayout = (LinearLayout) findViewById(R.id.layout_attachment_preview_container);

        attachmentPreviewAdapter = new AttachmentPreviewAdapter(chatActivity,
                new AttachmentPreviewAdapter.OnAttachmentCountChangedListener() {
                    @Override
                    public void onAttachmentCountChanged(int count) {
                        attachmentPreviewContainerLayout.setVisibility(count == 0 ? View.GONE : View.VISIBLE);
                    }
                },
                new AttachmentPreviewAdapter.OnAttachmentUploadErrorListener() {
                    @Override
                    public void onAttachmentUploadError(QBResponseException e) {

                        Helper.showSnackBar(llParentMain, "Something went wrong");
                        onSelectImageClick(null);

                    }
                });
        AttachmentPreviewAdapterView previewAdapterView = (AttachmentPreviewAdapterView) findViewById(R.id.adapter_view_attachment_preview);
        previewAdapterView.setAdapter(attachmentPreviewAdapter);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(chatActivity);
        rvSuggetsionList.setLayoutManager(mLayoutManager);
    }

    private void sendChatMessage(String text, QBAttachment attachment) {

        QBChatMessage chatMessage = new QBChatMessage();
        if (attachment != null) {
            chatMessage.addAttachment(attachment);
        } else {
            chatMessage.setBody(text);
        }
        chatMessage.setProperty(PROPERTY_SAVE_TO_HISTORY, "1");
        chatMessage.setDateSent(System.currentTimeMillis() / 1000);
//        chatMessage.setMarkable(true);

        if (!QBDialogType.PRIVATE.equals(qbChatDialog.getType()) && !qbChatDialog.isJoined()) {
//            Toaster.shortToast("You're still joining a group chat, please wait a bit");
            return;
        }

        try {
            qbChatDialog.sendMessage(chatMessage);
//            if(attachment.getType().equals(Constants.AUDIO))
//            {
//                mRecorder.stop();
//                mRecorder.release();
//                mRecorder = null;
//            }


            if (QBDialogType.PRIVATE.equals(qbChatDialog.getType())) {
                showMessage(chatMessage);
            }

            if (attachment != null) {
                attachmentPreviewAdapter.remove(attachment);
            } else {
                messageEditText.setText("");
            }

//            ProgressDialogFragment.hide(getSupportFragmentManager());
        } catch (SmackException.NotConnectedException e) {
            Log.w(TAG, e);
//            ProgressDialogFragment.hide(getSupportFragmentManager());

            Helper.hideSoftKeyboard(chatActivity);

            Helper.showSnackBar(llParentMain, "Can't send a message, You are not connected to chat");
            Intent i = new Intent(ChatBox.this, ChatModule.class);
            startActivity(i);
            finish();

//            Toaster.shortToast("Can't send a message, You are not connected to chat");
        } catch (Exception e) {
//            ProgressDialogFragment.hide(getSupportFragmentManager());

            e.printStackTrace();
        }
    }

    private void initChat() {
        switch (qbChatDialog.getType()) {
            case GROUP:
            case PUBLIC_GROUP:
                joinGroupChat();
                break;

            case PRIVATE:
                loadDialogUsers();
                break;

            default:
                Helper.showSnackBar(llParentMain, getResources().getString(R.string.chat_unsupported_type));
                finish();
                break;
        }
    }

    private void joinGroupChat() {
        progressBar.setVisibility(View.VISIBLE);
        ChatHelper.getInstance().join(qbChatDialog, new QBEntityCallback<Void>() {
            @Override
            public void onSuccess(Void result, Bundle b) {

                loadDialogUsers();
            }

            @Override
            public void onError(QBResponseException e) {
                progressBar.setVisibility(View.GONE);
                Helper.showSnackBar(llParentMain, "Something went wrong");
                Intent i =new Intent(chatActivity,ChatModule.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void leaveGroupDialog() {
        try {
            ChatHelper.getInstance().leaveChatDialog(qbChatDialog);
        } catch (XMPPException | SmackException.NotConnectedException e) {
            Log.w(TAG, e);
        }
    }

    private void releaseChat() {
        try {
            qbChatDialog.removeMessageListrener(chatMessageListener);
            if (!QBDialogType.PRIVATE.equals(qbChatDialog.getType())) {
                leaveGroupDialog();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updateDialog(final ArrayList<QBUser> selectedUsers, final String group_name,String groupPhoto) {


        ChatHelper.getInstance().updateDialogUsers(qbChatDialog, selectedUsers,
                new QBEntityCallback<QBChatDialog>() {
                    @Override
                    public void onSuccess(QBChatDialog dialog, Bundle args) {
                        qbChatDialog = dialog;
                        loadDialogUsers();
                    }

                    @Override
                    public void onError(QBResponseException e) {

                        Helper.showSnackBar(llParentMain, getResources().getString(R.string.chat_info_add_people_error));
                        updateDialog(selectedUsers, group_name,group_pic);

                    }
                },group_name,groupPhoto
        );
    }

    private void loadDialogUsers() {
        ChatHelper.getInstance().getUsersFromDialog(qbChatDialog, new QBEntityCallback<ArrayList<QBUser>>() {
            @Override
            public void onSuccess(ArrayList<QBUser> users, Bundle bundle) {
                setChatNameToActionBar();
                loadChatHistory();
            }

            @Override
            public void onError(QBResponseException e) {
                Helper.showSnackBar(llParentMain, getResources().getString(R.string.chat_load_users_error));


            }
        });
    }

    private void setChatNameToActionBar() {
        String chatName = QbDialogUtils.getDialogName(qbChatDialog);
        title.setText(chatName);

//        ActionBar ab = getSupportActionBar();
//        if (ab != null) {
//            ab.setTitle(chatName);
//            ab.setDisplayHomeAsUpEnabled(true);
//            ab.setHomeButtonEnabled(true);
//        }
    }

    private void loadChatHistory() {
        try
        {
            ChatHelper.getInstance().loadChatHistory(qbChatDialog, skipPagination, new QBEntityCallback<ArrayList<QBChatMessage>>() {
                @Override
                public void onSuccess(ArrayList<QBChatMessage> messages, Bundle args)
                {
                    // The newest messages should be in the end of list,
                    // so we need to reverse list to show messages in the right order

                    try
                    {
                        Collections.reverse(messages);
                        if (chatAdapter == null)
                        {
                            chatAdapter = new ChatAdapter(ChatBox.this, qbChatDialog, messages);
                            chatAdapter.setPaginationHistoryListener(new PaginationHistoryListener()
                            {
                                @Override
                                public void downloadMore()
                                {
                                    loadChatHistory();
                                }
                            });
                            chatAdapter.setOnItemInfoExpandedListener(new ChatAdapter.OnItemInfoExpandedListener()
                            {
                                @Override
                                public void onItemInfoExpanded(final int position)
                                {
                                    if (isLastItem(position))
                                    {
                                        // HACK need to allow info textview visibility change so posting it via handler
                                        runOnUiThread(new Runnable()
                                        {
                                            @Override
                                            public void run()
                                            {
                                                messagesListView.setSelection(position);
                                            }
                                        });
                                    }
                                    else
                                    {
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
                            messagesListView.setAdapter(chatAdapter);
                            messagesListView.setAreHeadersSticky(false);
                            messagesListView.setDivider(null);
                        } else {
                            chatAdapter.addList(messages);
                            messagesListView.setSelection(messages.size());
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onError(QBResponseException e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                    skipPagination -= ChatHelper.CHAT_HISTORY_ITEMS_PER_PAGE;
//                MyUtility.showSnackBar(llParentMain, getResources().getString(R.string.connection_error));

//                snackbar = showErrorSnackbar(R.string.connection_error, e, null);
                }
            });
            skipPagination += ChatHelper.CHAT_HISTORY_ITEMS_PER_PAGE;
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                Helper.showSnackBar(llParentMain, getResources().getString(R.string.dialogs_deletion_error));

            }
        });
    }

    private void initChatConnectionListener() {
        chatConnectionListener = new VerboseQbChatConnectionListener(findViewById(R.id.list_chat_messages), chatActivity) {
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
                                joinGroupChat();
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


    @Override
    public void onBackPressed() {
        if (lpost_upload_option.getVisibility() == View.VISIBLE) {
            Animation bottomDown = AnimationUtils.loadAnimation(chatActivity,
                    R.anim.bottom_down);

            lpost_upload_option.startAnimation(bottomDown);
            lpost_upload_option.setVisibility(View.GONE);
        } else if (hidden_panel_sugegstion.getVisibility() == View.VISIBLE) {
            Animation bottomDown = AnimationUtils.loadAnimation(chatActivity,
                    R.anim.bottom_down);
            hidden_panel_sugegstion.startAnimation(bottomDown);
            hidden_panel_sugegstion.setVisibility(View.GONE);
        } else {
            releaseChat();
            sendDialogId();
        }


        Intent i = new Intent(chatActivity, ChatModule.class);
        startActivity(i);
        finish();
    }



    private void vibrate() {
        // TODO Auto-generated method stub
        try {
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int dp(float value) {
        return (int) Math.ceil(1 * value);
    }


    private void requestAudioPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            //When permission is not granted by user, show them message why this permission is needed.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECORD_AUDIO)) {
                Toast.makeText(this, "Please grant permissions to record audio", Toast.LENGTH_LONG).show();

                //Give user option to still opt-in the permissions
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_RECORD_AUDIO);

            } else {
                // Show user dialog to grant permission to record audio
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_RECORD_AUDIO);
            }
        }
        //If permission is granted, then go ahead recording
        else if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {

            //Go ahead with recording audio now
//            recordAudio();
        }
    }
}

