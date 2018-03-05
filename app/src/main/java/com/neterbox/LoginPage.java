package com.neterbox;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.neterbox.jsonpojo.Login.Login;
import com.neterbox.retrofit.APIClient;
import com.neterbox.retrofit.APIInterface;
import com.neterbox.utils.Constants;
import com.neterbox.utils.Helper;
import com.neterbox.utils.Sessionmanager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage extends AppCompatActivity {
    EditText login_email, login_epassword;
    TextView login_tlogin, login_tsignin, login_tforgot;
    ImageView login_user,login_password;
    Sessionmanager sessionmanager;
    Context context;
    public static final String Id = "idKey";
    public static final String Email = "emailKey";
    public static final String Name = "nameKey";
    APIInterface apiInterface= APIClient.getClient().create(APIInterface.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        context = this;
        sessionmanager=new Sessionmanager(this);
        login_email = (EditText) findViewById(R.id.login_email);
        login_epassword = (EditText) findViewById(R.id.login_epassword);
        login_password = (ImageView) findViewById(R.id.login_password);
        login_user = (ImageView) findViewById(R.id.login_user);
        login_tlogin = (TextView) findViewById(R.id.login_tlogin);
        login_tsignin = (TextView) findViewById(R.id.login_tsignin);
        login_tforgot = (TextView) findViewById(R.id.login_tforgot);

        login_tlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (login_email.getText().toString().length() == 0) {
                    login_email.setError("Enter Email");
                }
               else if (login_epassword.getText().toString().length() == 0) {
                    login_epassword.setError("Enter Password");
                }
                else if (login_epassword.getText().toString().length() < 8) {
                    login_epassword.setError("Enter Minimum 8 digit");
                }
                else {
                    if(Helper.isConnectingToInternet(context)){
                        LoginPage(login_email.getText().toString(), login_epassword.getText().toString());
                    }
                    else {
                        Helper.showToastMessage(context,"No Internet Connection");
                    }
                }
            }
        });
        login_tsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(LoginPage.this, Registration.class);
                startActivity(it);
                finish();
            }
        });
        login_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    login_user.setImageResource(R.drawable.usernameblue);
                }
                else {
                    login_user.setImageResource(R.drawable.usernamegray);
                }
            }
        });
        login_epassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    login_password.setImageResource(R.drawable.passwordblue);
                }
                else {
                    login_password.setImageResource(R.drawable.passwordgray);
                }
            }
        });

    }

    public void LoginPage(String email, String password) {
            final ProgressDialog dialog = new ProgressDialog(context);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("Please Wait...");
            dialog.show();

        Call<Login> logincall = apiInterface.loginpojocall(email, password);
        logincall.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response)
            {
                dialog.dismiss();
                if (response.body().getStatus().equalsIgnoreCase("Success"))
                {
                    sessionmanager.createSession_userLogin((response.body().getData()));
                    Sessionmanager.setPreferenceBoolean(LoginPage.this, Constants.IS_LOGIN,true);
                    Intent i = new Intent(LoginPage.this, HomePage.class);
                   // i.putExtra("login_name",response.body().getData().getUser().getName());
                    startActivity(i);
                    finish();

                    Log.e("LoginID",response.body().getData().getUser().getId());
                }
                else
                {
                    Toast.makeText(LoginPage.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                dialog.dismiss();
            }
        });

    }
    @Override
    public void onBackPressed() {
        System.exit(0);
    }

}