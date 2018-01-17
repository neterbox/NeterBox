package com.neterbox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.neterbox.jsonpojo.Login.Login;
import com.neterbox.retrofit.APIClient;
import com.neterbox.retrofit.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage extends AppCompatActivity {
    EditText login_email, login_epassword;
    TextView login_tlogin, login_tsignin, login_tforgot;
    APIInterface apiInterface= APIClient.getClient().create(APIInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        login_email = (EditText) findViewById(R.id.login_email);
        login_epassword = (EditText) findViewById(R.id.login_epassword);
        login_tlogin = (TextView) findViewById(R.id.login_tlogin);
        login_tsignin = (TextView) findViewById(R.id.login_tsignin);
        login_tforgot = (TextView) findViewById(R.id.login_tforgot);


        login_tlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (login_email.getText().toString().length() == 0) {
                    login_email.setError("Enter Email");
                    return;
                }
                if (login_epassword.getText().toString().length() == 0) {
                    login_epassword.setError("Enter Password");
                    return;
                }
                if (login_epassword.getText().toString().length() < 8) {
                    login_epassword.setError("Enter Minimum 8 digit");
                    return;
                }
                LoginPage(login_email.getText().toString(), login_epassword.getText().toString());
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
    }

    public void LoginPage(String email, String password) {
        Call<Login> logincall = apiInterface.loginpojocall(email, password);
        logincall.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response)
            {
                if (response.body().getStatus().equalsIgnoreCase("Success"))
                {
                    Intent i = new Intent(LoginPage.this, HomePage.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Toast.makeText(LoginPage.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {

            }
        });
    }
}
