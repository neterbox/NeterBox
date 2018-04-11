package com.neterbox;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.gson.Gson;
import com.neterbox.customadapter.RecyclerUserListingAdapter;
import com.neterbox.customadapter.Search_Friend_Adapter;
import com.neterbox.jsonpojo.friend_list.FriendListDatum;
import com.neterbox.jsonpojo.friend_list.FriendListPojo;
import com.neterbox.jsonpojo.register.RegistrationDatum;
import com.neterbox.jsonpojo.register.RegistrationPojo;
import com.neterbox.qb.ChatHelper;
import com.neterbox.qb.QbChatDialogMessageListenerImp;
import com.neterbox.qb.QbDialogHolder;
import com.neterbox.qb.adapter.DialogsAdapter;
import com.neterbox.qb.managers.DialogsManager;
import com.neterbox.retrofit.APIClient;
import com.neterbox.retrofit.APIInterface;
import com.neterbox.utils.AlphabetItem;
import com.neterbox.utils.Sessionmanager;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.QBIncomingMessagesManager;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.chat.QBSystemMessagesManager;
import com.quickblox.chat.exception.QBChatException;
import com.quickblox.chat.listeners.QBChatDialogMessageListener;
import com.quickblox.chat.listeners.QBSystemMessageListener;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.chat.model.QBDialogType;
import com.quickblox.chat.utils.DialogUtils;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.sample.core.ui.dialog.ProgressDialogFragment;
import com.quickblox.users.model.QBUser;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.security.cert.CRLReason;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import in.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Create_group extends AppCompatActivity implements DialogsManager.ManagingDialogsCallbacks {

    ImageView ileft, iright;
    TextView title;
    public static final String EXTRA_QB_USERS = "qb_users";
    public static final int MINIMUM_CHAT_OCCUPANTS_SIZE = 2;

    private static final long CLICK_DELAY = TimeUnit.SECONDS.toMillis(2);

    private static final String EXTRA_QB_DIALOG = "qb_dialog";
    private static final String EXTRA_USER_IDS = "userId";

    private ProgressBar progressBar;
    private long lastClickTime = 0l;
    String group_pic, login_id;

    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    public static final int REQUEST_DIALOG_ID_FOR_UPDATE = 165;
    Context context;


    TextView tvNoDataFound;

    private DialogsManager dialogsManager;
    private QBSystemMessagesManager systemMessagesManager;
    private QBIncomingMessagesManager incomingMessagesManager;
    private QBChatDialogMessageListener allDialogsMessagesListener;
    private SystemMessagesListener systemMessagesListener;
    private DialogsAdapter dialogsAdapter;

    String contextStr;

    IndexFastScrollRecyclerView rvUserList;
    List<FriendListDatum> userDataList = new ArrayList<>();
    private List<AlphabetItem> mAlphabetItems;

    RecyclerUserListingAdapter recyclerUserListingAdapter;


    CircleImageView ivGroupPic;
    EditText tvGroupName;

    Uri selectedImageUri;

    FriendListDatum currentUser ;
    Sessionmanager sessionmanager;

    public static void startForResult(Activity activity, int code) {
        startForResult(activity, code, null);
    }

    public static void startForResult(Activity activity, int code, QBChatDialog dialog) {
        Intent intent = new Intent(activity, Create_group.class);
        intent.putExtra(EXTRA_QB_DIALOG, dialog);
        activity.startActivityForResult(intent, code);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        context = this;
        sessionmanager = new Sessionmanager(this);
        contextStr = getIntent().getStringExtra("context");

        currentUser = new FriendListDatum();
        login_id = sessionmanager.getValue(Sessionmanager.Id);


        final QBChatDialog dialog = (QBChatDialog) getIntent().getSerializableExtra(EXTRA_QB_DIALOG);

        dialogsManager = new DialogsManager();
        dialogsManager = new DialogsManager();

        idMapping();
        Listner();

        allDialogsMessagesListener = new AllDialogsMessageListener();
        systemMessagesListener = new SystemMessagesListener();


        registerQbChatListeners();
        dialogsAdapter = new DialogsAdapter(this, new ArrayList<>(QbDialogHolder.getInstance().getDialogs().values()));


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(Create_group.this);
        rvUserList.setLayoutManager(mLayoutManager);

        progressBar.setVisibility(View.VISIBLE);

        if (dialog != null) {
            tvGroupName.setText(dialog.getName());
            Glide.with(Create_group.this).load(dialog.getPhoto()).asBitmap().centerCrop().placeholder(R.drawable.dummy).into(new BitmapImageViewTarget(ivGroupPic) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    ivGroupPic.setImageDrawable(circularBitmapDrawable);
                }
            });

        }

        final Call<FriendListPojo> friendListPojoCall = apiInterface.friendlistpojo(login_id);
        friendListPojoCall.enqueue(new Callback<FriendListPojo>()
        {
            @Override
            public void onResponse(Call<FriendListPojo> call, Response<FriendListPojo> response) {
                if (response.body().getStatus().equals("Success"))
                {

                    userDataList.clear();
                    progressBar.setVisibility(View.GONE);
                    userDataList.add(currentUser);

                    for (FriendListDatum friendListDatum : response.body().getData()) {
                        userDataList.add(friendListDatum);
                    }


//                    Log.e("FRIEND LIS DATUM",new Gson().toJson(userDataList));
//                    Collections.sort(userDataList, new Comparator<FriendListDatum>() {
//                        @Override
//                        public int compare(final FriendListDatum object1, final FriendListDatum object2)
//                        {
//                            Log.e("OBJECT 1",new Gson().toJson(object1));
//                            Log.e("OBJECT 2",new Gson().toJson(object2));
//                            return object1.getReceiver().getName().compareTo(object2.getReceiver().getName());
//                        }
//                    });

                    mAlphabetItems = new ArrayList<>();
                    List<String> strAlphabets = new ArrayList<>();
                    for (int i = 0; i < userDataList.size(); i++) {
                        String name = userDataList.get(1).getReceiver().getName();
                        if (!name.equals(null))
                            continue;

                        String word = name.substring(0, 1);
                        if (!strAlphabets.contains(word)) {
                            strAlphabets.add(word);
                            mAlphabetItems.add(new AlphabetItem(i, word, false));
                        }
                    }
                    if (dialog != null) {
//                        recyclerUserListingAdapter.addSelectedUsers(dialog.getOccupants());
//
                        if (dialog.getOccupants().size() > 0) {

                            for (Integer integer : dialog.getOccupants()) {
                                for (int i = 0; i < userDataList.size(); i++) {
                                    if (!userDataList.get(i).getReceiver().getQuickbloxId().equals(""))
                                        if (integer == Integer.parseInt(userDataList.get(i).getReceiver().getQuickbloxId())) {
                                            userDataList.get(i).getReceiver().setSelected(true);
                                        }
                                }
                            }
                        }

                        recyclerUserListingAdapter = new RecyclerUserListingAdapter(context, userDataList, dialog.getOccupants());
                    }
                    else
                        {
                        for (int i = 0; i < userDataList.size(); i++) {
                            if (isUserMe(userDataList.get(1))) {
                                userDataList.get(1).getReceiver().setSelected(true);

                            }


                        }
                        recyclerUserListingAdapter = new RecyclerUserListingAdapter(context, userDataList);

                    }


                    rvUserList.setAdapter(recyclerUserListingAdapter);


                    rvUserList.setIndexTextSize(12);
                    rvUserList.setIndexBarColor("#ed941f");
                    rvUserList.setIndexBarCornerRadius(0);
                    rvUserList.setIndexBarTransparentValue((float) 0.4);
                    rvUserList.setIndexbarMargin(0);
                    rvUserList.setIndexbarWidth(60);
                    rvUserList.setPreviewPadding(0);
                    rvUserList.setIndexBarTextColor("#000000");

                    rvUserList.setIndexBarVisibility(true);
                    rvUserList.setIndexbarHighLateTextColor("#ffffff");
                    rvUserList.setIndexBarHighLateTextVisibility(true);

                }
                else
                    {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<FriendListPojo> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void Listner() {

        ileft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Create_group.this, ChatModule.class);
                startActivity(i);
                finish();
            }
        });

        ivGroupPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelectImageClick(null);
            }
        });

        iright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (tvGroupName.getText().toString().trim().length() == 0) {
                        Toast.makeText(context, "Please enter group name", Toast.LENGTH_SHORT).show();
                    } else {
                        if (recyclerUserListingAdapter != null) {
                            List<FriendListDatum> users = recyclerUserListingAdapter.getSelectedUsers();

                            List<QBUser> selectedUsers = new ArrayList<QBUser>();
                            selectedUsers.clear();

                            if (users.size() >= MINIMUM_CHAT_OCCUPANTS_SIZE) {
                                ArrayList<Integer> occupantIdsList = new ArrayList<Integer>();

                                for (FriendListDatum datum : users) {
                                    if (!(datum.getReceiver().getQuickbloxId().equals(""))) {
                                        occupantIdsList.add(Integer.parseInt(datum.getReceiver().getQuickbloxId()));
                                        QBUser qbUser = new QBUser();
                                        qbUser.setId(Integer.parseInt(datum.getReceiver().getQuickbloxId()));
                                        qbUser.setLogin(datum.getReceiver().getName());
                                        qbUser.setEmail(datum.getReceiver().getEmail());
                                        qbUser.setFullName(datum.getReceiver().getUsername());
                                        selectedUsers.add(qbUser);
                                    }

                                }

                                Log.e("qbuser_selected group", ":" + new Gson().toJson(selectedUsers));

                                if (contextStr != null) {

                                    if (isPrivateDialogExist((ArrayList<QBUser>) selectedUsers)) {
                                        selectedUsers.remove(ChatHelper.getCurrentUser());
                                        QBChatDialog existingPrivateDialog = QbDialogHolder.getInstance().getPrivateDialogWithUser(selectedUsers.get(0),
                                                tvGroupName.getText().toString().trim());

                                        for (int i = 0; i < userDataList.size(); i++) {
                                            ChatBox.startForResult(Create_group.this, REQUEST_DIALOG_ID_FOR_UPDATE, existingPrivateDialog, userDataList.get(i).getReceiver().getName(), userDataList.get(i).getReceiver().getProfilePic());
                                            finish();
                                        }

                                    } else {
                                        ProgressDialogFragment.show(getSupportFragmentManager(), R.string.create_chat);
                                        createDialog(occupantIdsList);

                                    }
                                } else {
                                    passResultToCallerActivity((ArrayList<QBUser>) selectedUsers);
                                }

                            } else {
                                Toast.makeText(context, getResources().getString(R.string.select_users_choose_users), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void idMapping() {

        progressBar = (ProgressBar) findViewById(R.id.progress_select_users);
        tvNoDataFound = (TextView) findViewById(R.id.tvNoDataFound);
        rvUserList = (IndexFastScrollRecyclerView) findViewById(R.id.rvUserList);
        ivGroupPic = (CircleImageView) findViewById(R.id.ivGroupPic);
        tvGroupName = (EditText) findViewById(R.id.tvGroupName);

        ileft = (ImageView) findViewById(R.id.ileft);
        iright = (ImageView) findViewById(R.id.iright);
        title = (TextView) findViewById(R.id.title);
        ileft.setImageResource(R.drawable.back);
        iright.setImageResource(R.drawable.correctgroup);
        title.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Create_group.this, ChatModule.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onDialogCreated(QBChatDialog chatDialog) {
        updateDialogsAdapter();
    }

    @Override
    public void onDialogUpdated(String chatDialog) {
        updateDialogsAdapter();
    }

    @Override
    public void onNewDialogLoaded(QBChatDialog chatDialog) {
        updateDialogsAdapter();
    }

    private void updateDialogsAdapter() {
        dialogsAdapter.updateList(new ArrayList<>(QbDialogHolder.getInstance().getDialogs().values()));
    }

    protected boolean isUserMe(FriendListDatum user) {
         boolean data = false;

        for (int i = 0; i < userDataList.size(); i++) {
            Log.e("Current USER ", new Gson().toJson(userDataList));
            data= userDataList != null && userDataList.get(1).getReceiver().getId().equals(user.getReceiver().getId());
        }
        return data;
    }
//        return currentUser != null && currentUser.getReceiver().getId().equals(user.getReceiver().getId());

    

    private class SystemMessagesListener implements QBSystemMessageListener {
        @Override
        public void processMessage(final QBChatMessage qbChatMessage) {
            dialogsManager.onSystemMessageReceived(qbChatMessage);
        }

        @Override
        public void processError(QBChatException e, QBChatMessage qbChatMessage) {

        }
    }

    private class AllDialogsMessageListener extends QbChatDialogMessageListenerImp {
        @Override
        public void processMessage(final String dialogId, final QBChatMessage qbChatMessage, Integer senderId) {
            if (!senderId.equals(ChatHelper.getCurrentUser().getId())) {
                dialogsManager.onGlobalMessageReceived(dialogId, qbChatMessage);
            }
        }
    }

    private boolean isPrivateDialogExist(ArrayList<QBUser> allSelectedUsers) {
        ArrayList<QBUser> selectedUsers = new ArrayList<>();
        selectedUsers.addAll(allSelectedUsers);
        selectedUsers.remove(ChatHelper.getCurrentUser());
        boolean b = selectedUsers.size() == 1 && QbDialogHolder.getInstance().hasPrivateDialogWithUser(selectedUsers.get(0));
        Log.e("selcteduser", ":" + b);
        return selectedUsers.size() == 1 && QbDialogHolder.getInstance().hasPrivateDialogWithUser(selectedUsers.get(0));
    }

    public void createDialog(ArrayList<Integer> occupantIdsList) {
        QBChatDialog dialog = new QBChatDialog();
        dialog.setName(tvGroupName.getText().toString().trim());
        if (selectedImageUri != null) {
            group_pic = getPath(selectedImageUri);
            dialog.setPhoto(group_pic);
        }

        dialog.setType(QBDialogType.GROUP);
        dialog.setOccupantsIds(occupantIdsList);

        final StringBuilder occupantList = new StringBuilder();

        for (Integer integer : occupantIdsList) {
            occupantList.append(integer);

        }
        Log.e("occupantList", ":" + new Gson().toJson(occupantList));

        //for creating GROUP dialog
        QBChatDialog dialog1 = DialogUtils.buildDialog(tvGroupName.getText().toString().trim(), QBDialogType.GROUP, occupantIdsList);

        QBRestChatService.createChatDialog(dialog1).performAsync(new QBEntityCallback<QBChatDialog>() {
            @Override
            public void onSuccess(QBChatDialog result, Bundle params) {
                QbDialogHolder.getInstance().addDialog(result);

                Log.e("result", ":" + new Gson().toJson(result));
                ProgressDialogFragment.hide(getSupportFragmentManager());
                for (int i = 0; i < userDataList.size(); i++) {
                    ChatBox.startForResult(Create_group.this, REQUEST_DIALOG_ID_FOR_UPDATE, result, userDataList.get(i).getReceiver().getName(), userDataList.get(i).getReceiver().getProfilePic());
                    finish();
                }
            }

            @Override
            public void onError(QBResponseException responseException) {

            }
        });
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = ((Activity) context).managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void passResultToCallerActivity(ArrayList<QBUser> selectedUsers) {
        Intent result = new Intent();
        result.putExtra(EXTRA_QB_USERS, selectedUsers);
        result.putExtra("group_name", tvGroupName.getText().toString());
        if (selectedImageUri != null) {
            group_pic = getPath(selectedImageUri);
            if (group_pic != null)
                result.putExtra("group_pic", group_pic);
        }

        setResult(RESULT_OK, result);
        finish();
    }

    private void registerQbChatListeners() {
        incomingMessagesManager = QBChatService.getInstance().getIncomingMessagesManager();
        systemMessagesManager = QBChatService.getInstance().getSystemMessagesManager();

        if (incomingMessagesManager != null) {
            incomingMessagesManager.addDialogMessageListener(allDialogsMessagesListener != null
                    ? allDialogsMessagesListener : new AllDialogsMessageListener());
        }

        if (systemMessagesManager != null) {
            systemMessagesManager.addSystemMessageListener(systemMessagesListener != null
                    ? systemMessagesListener : new SystemMessagesListener());
        }

        dialogsManager.addManagingDialogsCallbackListener(this);
    }

    public void onSelectImageClick(Uri imageUri) {

        Intent intent = CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .setCropShape(CropImageView.CropShape.OVAL)
                .getIntent(Create_group.this);

        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            try {
                final CropImage.ActivityResult result = CropImage.getActivityResult(data);

//                Log.e("data", ":" + data + "=======" + data.getData());
                if (resultCode == RESULT_OK && result.getUri() != null && result.getUri() != null) {
//                    imageUrl= String.valueOf(result.getUri());

                    Uri uri =/*data.getData()*/ result.getUri();

                    File myFile = new File(uri.getPath());

                    selectedImageUri = getImageContentUri(Create_group.this, myFile);

                    Glide.with(Create_group.this).load(getPath(selectedImageUri))
                            .asBitmap().centerCrop().placeholder(R.drawable.dummy).into(new BitmapImageViewTarget(ivGroupPic) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            ivGroupPic.setImageDrawable(circularBitmapDrawable);
                        }
                    });


                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Toast.makeText(Create_group.this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Create_group.this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
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
}
