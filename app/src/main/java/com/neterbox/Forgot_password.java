package com.neterbox;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.neterbox.jsonpojo.Forgot_Password.ForgotPassword;
import com.neterbox.retrofit.APIClient;
import com.neterbox.retrofit.APIInterface;
import com.neterbox.utils.Constants;
import com.neterbox.utils.Helper;
import com.neterbox.utils.Sessionmanager;
import com.neterbox.utils.Validators;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Forgot_password extends AppCompatActivity {


    EditText edForgotEmail;
    ImageView ivUpdate;
    LinearLayout llParentForgot;

    Forgot_password activity;

    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        init();
        listener();
    }

    public void init()
    {
        activity =this;

        ivUpdate=(ImageView)findViewById(R.id.ivUpdate);

        llParentForgot=(LinearLayout)findViewById(R.id.llParentForgot);

        edForgotEmail=(EditText)findViewById(R.id.edForgotEmail);

    }

    public void listener()
    {
        ivUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edForgotEmail.getText().toString().trim().length()==0 )
                {
                    Helper.showSnackBar(llParentForgot,"Please Enter all details");
                }
                else
                {
                    forgot();
                }
            }
        });
    }

    public void forgot()
    {
        final ProgressDialog progressDialog=Helper.showProgressDialog(activity);

        Call<ForgotPassword> loginCall=apiInterface.forgotPojoCall(edForgotEmail.getText().toString().trim());
        loginCall.enqueue(new Callback<ForgotPassword>() {
            @Override
            public void onResponse(Call<ForgotPassword> call, Response<ForgotPassword> response) {
                if(response.body().getStatus().equals("sucess"))
                {
                    progressDialog.dismiss();
                    Intent i=new Intent(activity,LoginPage.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    progressDialog.dismiss();
                    Helper.showSnackBar(llParentForgot,response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ForgotPassword> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(activity,LoginPage.class);
        startActivity(i);
        finish();
    }
}