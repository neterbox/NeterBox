package com.neterbox;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.neterbox.jsonpojo.Login.Login;
import com.neterbox.jsonpojo.Login.LoginDatum;
import com.neterbox.jsonpojo.Login.Loginuser;
import com.neterbox.jsonpojo.editprofile.Editpage;
import com.neterbox.retrofit.APIClient;
import com.neterbox.retrofit.APIInterface;
import com.neterbox.utils.Helper;
import com.neterbox.utils.Sessionmanager;

import java.util.Calendar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends AppCompatActivity implements View.OnClickListener{

    Button edit_bsave;
    Sessionmanager sessionmanager;
    TextView edit_tbirthday,title;
    int mYear, mMonth, mDay;
    ImageView ileft,iright;
    Activity activity;
    LoginDatum userdetails;
    EditText edit_ename,edit_euname,edit_ephone,edit_eemail,edit_egender,edit_eaddress,edit_ecompany,edit_etitle;

    APIInterface apiInterface= APIClient.getClient().create(APIInterface.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        activity=this;
        sessionmanager=new Sessionmanager(this);
        edit_bsave = (Button) findViewById(R.id.edit_bsave);
        edit_ename = (EditText) findViewById(R.id.edit_ename);
        edit_euname = (EditText) findViewById(R.id.edit_euname);
        edit_tbirthday = (TextView) findViewById(R.id.edit_tbirthday);
        edit_ephone = (EditText) findViewById(R.id.edit_ephone);
        edit_eemail = (EditText) findViewById(R.id.edit_eemail);
        edit_egender = (EditText) findViewById(R.id.edit_egender);
        edit_eaddress = (EditText) findViewById(R.id.edit_eaddress);
        edit_ecompany = (EditText) findViewById(R.id.edit_ecompany);
        edit_etitle = (EditText) findViewById(R.id.edit_etitle);

        edit_tbirthday.setOnClickListener(this);
        ileft = (ImageView) findViewById(R.id.ileft);
        iright = (ImageView) findViewById(R.id.iright);
        title = (TextView) findViewById(R.id.title);
        ileft.setImageResource(R.drawable.back);
        iright.setVisibility(View.INVISIBLE);
        title.setText("Edit Profile");

        edit_ename.setText(sessionmanager.getValue(Sessionmanager.Name));
        edit_euname.setText(sessionmanager.getValue(Sessionmanager.Username));
        edit_ephone.setText(sessionmanager.getValue(Sessionmanager.Phone_number));
        edit_eemail.setText(sessionmanager.getValue(Sessionmanager.Email));
        edit_egender.setText(sessionmanager.getValue(Sessionmanager.Gender));
        edit_eaddress.setText(sessionmanager.getValue(Sessionmanager.Address));
        edit_ecompany.setText(sessionmanager.getValue(Sessionmanager.Company));
        edit_etitle.setText(sessionmanager.getValue(Sessionmanager.Title));
        edit_tbirthday.setText(sessionmanager.getValue(Sessionmanager.Birthdate));


        edit_bsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit_ename.getText().toString().length() == 0) {
                    edit_ename.setError("Enter name");
                    return;
                }
                if (edit_euname.getText().toString().length() == 0) {
                    edit_euname.setError("Enter username");
                    return;
                }
                if (edit_ephone.getText().toString().length() == 0) {
                    edit_ephone.setError("Enter Phone Number");
                    return;
                }
                if (edit_ephone.getText().toString().length() != 10) {
                    edit_ephone.setError("Enter Valid Number");
                    return;
                }
                if (edit_eemail.getText().toString().length() == 0) {
                    edit_eemail.setError("Enter Email");
                    return;
                }
                if (!(isValidEmail(edit_eemail.getText().toString()))) {
                    edit_eemail.setError("Enter Valid Email ID");
                }
                if (edit_egender.getText().toString().length() == 0) {
                    edit_egender.setError("Enter gender");
                    return;
                }
                if (edit_eaddress.getText().toString().length() == 0) {
                    edit_eaddress.setError("Enter address");
                    return;
                }
                if (edit_ecompany.getText().toString().length() == 0) {
                    edit_ecompany.setError("Enter Company name");
                    return;
                }
                if (edit_etitle.getText().toString().length() == 0) {
                    edit_etitle.setError("Enter Title");
                    return;
                }
                if (edit_tbirthday.getText().toString().length() == 0) {
                    edit_tbirthday.setError("Select Birthdate");
                    return;
                }
                if(Helper.isConnectingToInternet(activity)){
                    EditProfile("110", edit_ename.getText().toString(), edit_euname.getText().toString(), "123456789", edit_tbirthday.getText().toString(), edit_ephone.getText().toString(), edit_eemail.getText().toString(), edit_egender.getText().toString(), edit_eaddress.getText().toString(), edit_ecompany.getText().toString(), edit_etitle.getText().toString());
                }
                else {
                    Helper.showToastMessage(activity,"No Internet Connection");
                }

                Intent i=new Intent(EditProfile.this,HomePage.class);
                startActivity(i);
                finish();
            }
        });
    }
    public void onClick(View v) {
        if (v == edit_tbirthday) {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
//                            edit_tbirthday.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            edit_tbirthday.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
            datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        }



        ileft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(EditProfile.this,HomePage.class);
                startActivity(i);
                finish();
            }
        });
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
        public void EditProfile(String id,String name,String username,String password,String birhday,String phone,String email,String gender,String address,String company,String title)
        {
            Call<Editpage> editcall = apiInterface.editpojocall(id,name,username,password,birhday,phone,email,gender,address,company,title);
            editcall.enqueue(new Callback<Editpage>() {
                @Override
                public void onResponse(Call<Editpage> call, Response<Editpage> response) {
                    if (response.body().getStatus().equalsIgnoreCase("Success"))
                    {
                        sessionmanager.createSession_userEdit((response.body().getData()));
//                        Intent i = new Intent(EditProfile.this, HomePage.class);
//                        startActivity(i);
//                        finish();
                    }
                    else
                    {
                        Toast.makeText(EditProfile.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Editpage> call, Throwable t) {
                }
            });

        }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(EditProfile.this,Settings.class);
        startActivity(i);
        finish();
    }
}
