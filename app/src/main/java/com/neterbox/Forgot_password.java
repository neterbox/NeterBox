package com.neterbox;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.neterbox.jsonpojo.Forgot_Password.ForgotPassword;
import com.neterbox.retrofit.APIClient;
import com.neterbox.retrofit.APIInterface;
import com.neterbox.utils.Constants;
import com.neterbox.utils.Helper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Forgot_password extends AppCompatActivity {

    private EditText edt_email;
    private TextView tv_submit;
    private Context context;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        context = this;
        apiInterface = APIClient.getClient().create(APIInterface.class);

        idMapping();
        listener();

    }

    private void idMapping()
    {
        edt_email = (EditText) findViewById(R.id.edt_email);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
    }

    private void listener()
    {
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Helper.isNetworkAvailable(context))
                {
                    if (ValidationChecked())
                    {
                        callApi_ForgotPassword(edt_email.getText().toString());
                    }
                }
                else
                {
                    Helper.showToast(context,getString(R.string.network_not_available));
                }
            }
        });
    }
    private boolean ValidationChecked() {
        if (Constants.isEmpty(edt_email.getText().toString().trim()))
        {
            Helper.showToast(context , "Please Enter Email Address");
            return false;
        }

        if (!Constants.isValidEmail(edt_email.getText().toString().trim()))
        {
            Helper.showToast(context ,"Please Enter Valid Email Address");
            return false;
        }
        return true;
    }

    //TODO For Forgot Password .
    public void callApi_ForgotPassword(String email) {
        final ProgressDialog progressDialog = Helper.showProgressDialog(context);

        Call<ForgotPassword> removeCardCall = apiInterface.forgotPasswordCall(email);
        removeCardCall.enqueue(new Callback<ForgotPassword>() {
            @Override
            public void onResponse(Call<ForgotPassword> registrationCall, Response<ForgotPassword> response) {
                if (response.body().getStatus().equals("sucess")) {
                    progressDialog.dismiss();
                    finish();
                } else {
                    progressDialog.dismiss();
                    Helper.showToast(context, response.body().getMessage());
                }
            }
            @Override
            public void onFailure(Call<ForgotPassword> call, Throwable t) {
                progressDialog.dismiss();
                Helper.showToast(context, t.getMessage());
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(context,LoginPage.class);
        startActivity(i);
        finish();
    }
}

   /* EditText edForgotEmail;
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
}*/