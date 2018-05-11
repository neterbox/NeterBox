package com.neterbox;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
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
import com.neterbox.jsonpojo.addmember.AddMember;
import com.neterbox.jsonpojo.create_group.AddGroup;
import com.neterbox.jsonpojo.friend_list.FriendListDatum;
import com.neterbox.jsonpojo.friend_list.FriendListPojo;
import com.neterbox.jsonpojo.uploadpic.Uploadpic;
import com.neterbox.qb.ChatHelper;
import com.neterbox.qb.QbChatDialogMessageListenerImp;
import com.neterbox.qb.QbDialogHolder;
import com.neterbox.qb.adapter.DialogsAdapter;
import com.neterbox.qb.managers.DialogsManager;
import com.neterbox.retrofit.APIClient;
import com.neterbox.retrofit.APIInterface;
import com.neterbox.utils.AlphabetItem;
import com.neterbox.utils.Constants;
import com.neterbox.utils.Helper;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import in.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.neterbox.utils.Sessionmanager.dialog_Id;

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
    SharedPreferences sharedPreferences;

    TextView tvNoDataFound;

    private DialogsManager dialogsManager;
    private QBSystemMessagesManager systemMessagesManager;
    private QBIncomingMessagesManager incomingMessagesManager;
    private QBChatDialogMessageListener allDialogsMessagesListener;
    private SystemMessagesListener systemMessagesListener;
    private DialogsAdapter dialogsAdapter;
    public static final int GALLARY_REQUEST = 2;
    public static final int CAMERA_REQUEST = 1;
    public static final int MY_PERMISSIONS_REQUEST_GALLARY = 11;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 12;

    String contextStr;
    IndexFastScrollRecyclerView rvUserList;
    List<FriendListDatum> userDataList = new ArrayList<>();
    private List<AlphabetItem> mAlphabetItems;

    RecyclerUserListingAdapter recyclerUserListingAdapter;
    FriendListDatum dtUser;


    CircleImageView ivGroupPic;
    EditText tvGroupName;

    Bitmap bitmap;

    FriendListDatum currentUser;
    List<FriendListDatum> frndlist;
    Sessionmanager sessionmanager;
    ArrayList<Integer> occupantIdsList = new ArrayList<Integer>();
    ArrayList<String> receiverIdsList = new ArrayList<String>();
    ArrayList<QBUser> selectedUsers;
    File fileCamera;

    public static List<Integer> frnd_id = new ArrayList<>();
    public static List<Integer> frnd_qb_id = new ArrayList<>();

    public static void startForResult(Activity activity, int code) {
        startForResult(activity, code, null,dialog_Id);
    }

    public static void startForResult(Activity activity, int code, QBChatDialog dialog,String Dialog_id) {
        Intent intent = new Intent(activity, Create_group.class);
        intent.putExtra(EXTRA_QB_DIALOG, dialog);
        intent.putExtra("action", "add");
        activity.startActivityForResult(intent, code);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        context = this;
        sessionmanager = new Sessionmanager(this);
        contextStr = getIntent().getStringExtra("action");

        dtUser = new FriendListDatum();
        currentUser = new FriendListDatum();
        login_id = sessionmanager.getValue(Sessionmanager.Id);
        sharedPreferences = context.getSharedPreferences(Constants.mypreference, Context.MODE_PRIVATE);
        currentUser = Sessionmanager.getfrnd(context);
        dtUser = Sessionmanager.getfrnd(context);

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

        final ProgressDialog progressDialog = Helper.showProgressDialog(Create_group.this);
        Call<FriendListPojo> providerListCall = apiInterface.friendlistpojo(sessionmanager.getValue(Sessionmanager.Id));
        providerListCall.enqueue(new Callback<FriendListPojo>() {
            @Override
            public void onResponse(Call<FriendListPojo> call, Response<FriendListPojo> response) {
                if (response.body().getStatus().equals("Success")) {
                    Log.e("RQSUSERDATALIST", new Gson().toJson(response.body().getData()));
//                    for (int i=0;i<response.body().getData().size();i++)
//                    {
//                        Sessionmanager.saveDtUserInPreference(context,response.body().getData());
//                    }
                    progressDialog.dismiss();

                    userDataList.clear();
                    progressBar.setVisibility(View.GONE);
//                    userDataList.add(dtUser);

                    for (FriendListDatum friendListDatum : response.body().getData()) {

                        userDataList.add(friendListDatum);
                    }
                    frndlist = new ArrayList<FriendListDatum>();
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        frndlist.add(response.body().getData().get(i));
                    }

                    mAlphabetItems = new ArrayList<>();
                    List<String> strAlphabets = new ArrayList<>();
                    Log.e("USERDATALIST", new Gson().toJson(userDataList));
                    for (int i = 0; i < userDataList.size(); i++) {
                        String name = userDataList.get(i).getReceiver().getName();
                        if (name == null || name.trim().isEmpty())
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
                                    if (!userDataList.get(i).getSender().getQuickbloxId().equals(""))
                                        if (integer == Integer.parseInt(userDataList.get(i).getSender().getQuickbloxId())) {
                                            userDataList.get(i).getSender().setSelected(true);
                                        }
                                }
                            }
                        }

                        recyclerUserListingAdapter = new RecyclerUserListingAdapter(context, userDataList, dialog.getOccupants());
                    } else {
                        for (int i = 0; i < userDataList.size(); i++) {
                            if (isUserMe(userDataList.get(i))) {
                                userDataList.get(i).getSender().setSelected(true);

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

                } else {
                    progressDialog.dismiss();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<FriendListPojo> call, Throwable t) {
                progressDialog.dismiss();
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        showPictureDialog();

                    } else {
                        //Request Location Permission
                        checkCameraPermission();
                        checkStoragePermission();
                    }
                } else {
                    showPictureDialog();
                }
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


                            List<String> qbIDList=null;
                            for (FriendListDatum datum : users) {
                                if (!(datum.getReceiver().getQuickbloxId().equals(""))) {
                                    QBUser qbUser = new QBUser();
                                    qbUser.setId(Integer.parseInt(datum.getReceiver().getQuickbloxId()));
                                    qbUser.setLogin(datum.getReceiver().getName());
                                    qbUser.setEmail(datum.getReceiver().getEmail());
                                    qbUser.setFullName(datum.getReceiver().getUsername());
                                    selectedUsers.add(qbUser);
                                    qbIDList.add(datum.getReceiver().getId());
                                }

                            }

                            if(contextStr.equalsIgnoreCase("add"))
                            {
                                String list = TextUtils.join(",", qbIDList);
                                Log.e("=====List====",":"+list);
                            }


                            Log.e("qbuser_selected group", ":" + new Gson().toJson(selectedUsers));

                            // TODO : CREATE GROUP
                            ProgressDialogFragment.show(getSupportFragmentManager(), R.string.create_chat);
                            final String list = TextUtils.join(",", frnd_id);
                            final String list_qb = TextUtils.join(",", frnd_qb_id);

                            QBChatDialog dialog = new QBChatDialog();
                            dialog.setName(tvGroupName.getText().toString().trim());
                            dialog.setPhoto(String.valueOf(bitmap));

                            dialog.setType(QBDialogType.GROUP);
                            dialog.setOccupantsIds(frnd_qb_id);

                            QBChatDialog dialog1 = DialogUtils.buildDialog(tvGroupName.getText().toString().trim(), QBDialogType.GROUP, frnd_qb_id);

                            QBRestChatService.createChatDialog(dialog1).performAsync(new QBEntityCallback<QBChatDialog>()
                            {
                                @Override
                                public void onSuccess(QBChatDialog result, Bundle params) {
                                    QbDialogHolder.getInstance().addDialog(result);

                                    sharedPreferences.edit().putString(sessionmanager.dialogId, (String.valueOf(result.getDialogId()))).apply();
                                    Log.e("DIALOGID", sessionmanager.getValue(Sessionmanager.dialogId));
                                    String user_id = sessionmanager.getValue(Sessionmanager.Id);
                                    String dialog_id = sessionmanager.getValue(Sessionmanager.dialogId);
                                    String sender_qb_id = sessionmanager.getValue(Sessionmanager.Quickbox_Id);
                                    String group_name = tvGroupName.getText().toString().trim();

                                    ProgressDialogFragment.hide(getSupportFragmentManager());
                                    if (Helper.isConnectingToInternet(context)) {


                                        addgroup(user_id, list, sender_qb_id, list_qb, dialog_id, "group", true, group_name, fileCamera);

                                        ChatBox.startForResult(Create_group.this, REQUEST_DIALOG_ID_FOR_UPDATE, result, result.getName(), result.getPhoto());
                                    }
                                }

                                @Override
                                public void onError(QBResponseException responseException) {
                                    Log.e("responseException", ":" + responseException);
                                    ProgressDialogFragment.hide(getSupportFragmentManager());
                                    Toast.makeText(context,responseException.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
                catch (Exception e)
                {

                    Log.e("e", ":" + e);
                    e.printStackTrace();
                }
            }
        });

        registerQbChatListeners();
        dialogsAdapter = new DialogsAdapter(this, new ArrayList<>(QbDialogHolder.getInstance().getDialogs().values()));

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
            data = userDataList != null && userDataList.get(i).getReceiver().getId().equals(user.getReceiver().getId());
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

        selectedUsers.addAll(allSelectedUsers);
        selectedUsers.remove(ChatHelper.getCurrentUser());
        boolean b = selectedUsers.size() == 1 && QbDialogHolder.getInstance().hasPrivateDialogWithUser(selectedUsers.get(0));
        Log.e("selcteduser", ":" + b);
        return selectedUsers.size() == 1 && QbDialogHolder.getInstance().hasPrivateDialogWithUser(selectedUsers.get(0));
    }

    public void createDialog(final ArrayList<Integer> frnd_qb) {


    }

    private void passResultToCallerActivity(ArrayList<QBUser> selectedUsers) {
        Intent result = new Intent();
        result.putExtra(EXTRA_QB_USERS, selectedUsers);
        result.putExtra("group_name", tvGroupName.getText().toString());
        result.putExtra("group_pic", bitmap);
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

    private void checkStoragePermission() {


        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_GALLARY);

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_GALLARY);
            }
        }
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
                ) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                    android.Manifest.permission.CAMERA)
                    ) {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{android.Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{android.Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
            }
        }
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLARY_REQUEST);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLARY_REQUEST) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);

//                    Toast.makeText(context, "Image Saved!", Toast.LENGTH_SHORT).show();
                    ivGroupPic.setImageBitmap(bitmap);
                    if(fileCamera != null) {
                        fileCamera = null;
                    }
                        fileCamera = new File(path);
                    if (Helper.isConnectingToInternet(context)) {
                        Uploadpic(new Sessionmanager(context).getValue(Sessionmanager.Id), fileCamera);
                    } else {
                        Helper.showToastMessage(context, "No Internet Connection");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == CAMERA_REQUEST) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ivGroupPic.setImageBitmap(thumbnail);
            String imagePath = saveImage(thumbnail);
            if (Helper.isConnectingToInternet(context)) {
                if(fileCamera != null) {
                    fileCamera = null;
                }
                fileCamera = new File(imagePath);

                Uploadpic(new Sessionmanager(context).getValue(Sessionmanager.Id), fileCamera);
            } else {
                Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private String saveImage(Bitmap thumbnail) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + "IMAGE_DIRECTORY");
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File f = new File(path, "DemoPicture.jpg");
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.e("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    public void Uploadpic(String Id, File fileCamera) {
        final ProgressDialog progressDialog = Helper.showProgressDialog(context);

        RequestBody loginIdReqBody = RequestBody.create(MediaType.parse("text/plain"), Id);
        Log.e("login_id", "" + Id);
        if (fileCamera != null) {
            final RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), fileCamera);
            MultipartBody.Part userProfile = MultipartBody.Part.createFormData("profile_pic", fileCamera.getName(), requestFile);

            Call<Uploadpic> uploadProfileCall = apiInterface.uploadPic(loginIdReqBody, userProfile);
            uploadProfileCall.enqueue(new Callback<Uploadpic>() {
                @Override
                public void onResponse(Call<Uploadpic> uploadImageCall, Response<Uploadpic> response) {
                    if (response.body().getStatus().equals("Success")) {
                        new Sessionmanager(Create_group.this).putSessionValue(Sessionmanager.profile, response.body().getData().getUser().getProfilePic());

                        progressDialog.dismiss();

                        Glide.with(context).load(response.body().getData().getUser().getProfilePic()).into(ivGroupPic);
                    } else {
                        progressDialog.dismiss();

                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<Uploadpic> call, Throwable t) {
                    progressDialog.dismiss();

//                    Helper.showToastMessage(context,t.getMessage());
                }
            });
        }
    }
    public void addgroup(String Id, String members, String sender_qb_id, String receiver_qb_id, String dialog_id, String dialog_type, boolean dialog_status, String group_name, File icon) {
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), Id);
        RequestBody Members = RequestBody.create(MediaType.parse("text/plain"), members);
        RequestBody Sender_qb_id = RequestBody.create(MediaType.parse("text/plain"), sender_qb_id);
        RequestBody Receiver_qb_id = RequestBody.create(MediaType.parse("text/plain"), receiver_qb_id);
        RequestBody Dialog_id = RequestBody.create(MediaType.parse("text/plain"), dialog_id);
        RequestBody Dialog_type = RequestBody.create(MediaType.parse("text/plain"), dialog_type);
        Boolean Dialog_status  = dialog_status;
        RequestBody Group_name = RequestBody.create(MediaType.parse("text/plain"), group_name);

        if (icon != null) {
            final RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), icon);
            MultipartBody.Part userProfile = MultipartBody.Part.createFormData("profile_pic", icon.getName(), requestFile);

            Call<AddGroup> chatCall = apiInterface.addgrouppojo(user_id, Members, Sender_qb_id, Receiver_qb_id, Dialog_id, Dialog_type, Dialog_status, Group_name, userProfile);

            chatCall.enqueue(new Callback<AddGroup>()
            {
                @Override
                public void onResponse(Call<AddGroup> chatCall, Response<AddGroup> response)
                {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        finish();
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else
                        {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AddGroup> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public void addmember(String id, String members) {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Please Wait...");
        dialog.show();

        Call<AddMember> circlePostAddPCall = apiInterface.addmemberpojo(id, members);
        circlePostAddPCall.enqueue(new Callback<AddMember>() {
            @Override
            public void onResponse(Call<AddMember> registrationCall, Response<AddMember> response) {
                if (response.body().getStatus().equalsIgnoreCase("Success")) {
                    dialog.dismiss();
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddMember> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
