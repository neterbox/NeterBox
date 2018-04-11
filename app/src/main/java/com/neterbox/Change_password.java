package com.neterbox;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.cast.framework.SessionManager;
import com.google.android.gms.cast.framework.zzw;
import com.neterbox.jsonpojo.ChangePassword.ChangePassword;
import com.neterbox.jsonpojo.ChangePassword.Changepassword_Datum;
import com.neterbox.retrofit.APIClient;
import com.neterbox.retrofit.APIInterface;
import com.neterbox.utils.Helper;
import com.neterbox.utils.Sessionmanager;
import com.neterbox.utils.Validators;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Change_password extends AppCompatActivity {


    Context context;
    boolean isshow;
    Sessionmanager sessionmanager;
    private ImageView iv_back, iv_oldpwd_watch, iv_newpwd_watch, iv_confirmpwd_watch,iv_back_icon;
    private TextView top_strip_title,title;
    private EditText edt_oldpwd, edt_newpwd, edt_confirmpwd;
    private RelativeLayout rel_save;
    private LinearLayout ll_main;
    APIInterface apiInterface;
    String id,password;
    private int passwordNotVisible=1;
    Changepassword_Datum changepasswordDatum = new Changepassword_Datum();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        sessionmanager = new Sessionmanager(this);
        initialization();

        idMapping();
        listener();

    }
    private void initialization()
    {
        context = this;
        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    private void idMapping()
    {
        iv_back = (ImageView) findViewById(R.id.iv_back_icon);
        edt_oldpwd = (EditText) findViewById(R.id.edt_oldpwd);
        edt_newpwd = (EditText) findViewById(R.id.edt_newpwd);
        edt_confirmpwd = (EditText) findViewById(R.id.edt_confirmpwd);
        rel_save = (RelativeLayout) findViewById(R.id.rel_save);
        iv_oldpwd_watch = (ImageView) findViewById(R.id.iv_oldpwd_watch);
        iv_newpwd_watch = (ImageView) findViewById(R.id.iv_newpwd_watch);
        iv_back_icon = (ImageView) findViewById(R.id.iv_back_icon);
        iv_confirmpwd_watch = (ImageView) findViewById(R.id.iv_confirmpwd_watch);
        ll_main = (LinearLayout) findViewById(R.id.ll_main);
        top_strip_title = (TextView) findViewById(R.id.top_strip_title);
        title = (TextView) findViewById(R.id.title);
        top_strip_title.setVisibility(View.INVISIBLE);
        top_strip_title.setText("Change Password");

        // top_strip_title.setText(getString(R.string.));
    }

    private void listener()
    {
        rel_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Helper.isConnectingToInternet(context))
                {
                    if (validationChecked())
                    {
                        callApi_ChangePassword(sessionmanager.getValue(Sessionmanager.Id), edt_newpwd.getText().toString());
                    }
                }
                else
                {
                    Helper.showToastMessage(context,getString(R.string.no_internet));
                }

            }
        });
        iv_back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Change_password.this,Settings.class);
                startActivity(i);
                finish();
            }
        });
        ll_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.hideSoftKeyboard(context);
            }
        });

        iv_newpwd_watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordNotVisible == 1) {
                    edt_newpwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordNotVisible = 0;
                    iv_newpwd_watch.setImageResource(R.drawable.registrationeye);
                } else {

                    edt_newpwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordNotVisible = 1;
                    iv_newpwd_watch.setImageResource(R.drawable.eyeclosed);
                }
            }
        });
    }

    //TODO For Check Validations
    private boolean validationChecked() {
        if (Validators.isEmpty(edt_newpwd.getText().toString())) {
            Helper.showToastMessage(context, getString(R.string.empty_newpwd));
            return false;
        }
        if (edt_newpwd.getText().toString().length() < 8 )
        {
            Helper.showToastMessage(context ,getString(R.string.valid_password) );
            return false;
        }
        return true;
    }

    //TODO Call Api For Change Password
    public void callApi_ChangePassword(String id, final String password)
    {
        final ProgressDialog progressDialog = Helper.showProgressDialog(context);

        final Call<ChangePassword> changePassword=apiInterface.callApi_ChangePassword(id,password);

        changePassword.enqueue(new Callback<ChangePassword>() {
            @Override
            public void onResponse(Call<ChangePassword> changePassword1, Response<ChangePassword> response) {

                if(response.body().getStatus().equals("sucess"))
                {
                    changepasswordDatum = response.body().getData();
                    Toast.makeText(context,response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(context,response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChangePassword> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(Change_password.this,Settings.class);
        startActivity(i);
        finish();
    }
}
