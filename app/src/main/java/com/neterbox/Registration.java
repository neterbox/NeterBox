package com.neterbox;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.neterbox.retrofit.APIInterface;
import com.neterbox.retrofit.APIClient;
import com.neterbox.jsonpojo.register.RegistrationPojo;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity {

    EditText name, username, register_eday, register_emonth, register_eyear, register_eemail, register_epassword;
    ImageView register_iday, register_imonth, register_iyear;
    Button btnRegistration;
    TextView tbirthday;
    int mYear, mMonth, mDay;
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        name = (EditText) findViewById(R.id.name);
        username = (EditText) findViewById(R.id.username);
//        register_eday = (EditText) findViewById(R.id. register_eday);
        register_eemail = (EditText) findViewById(R.id.register_eemail);
        register_epassword = (EditText) findViewById(R.id.register_epassword);
        tbirthday = (TextView) findViewById(R.id.tbirthday);


//        register_emonth = (EditText) findViewById(R.id. register_emonth);
//        register_eyear = (EditText) findViewById(R.id. register_efname);
//        register_iday= (ImageView) findViewById(R.id.register_iday);
//        register_imonth= (ImageView) findViewById(R.id.register_imonth);
//        register_iyear= (ImageView) findViewById(R.id.register_iyear);

        btnRegistration = (Button) findViewById(R.id.btnRegistration);


        btnRegistration.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if ((name.getText().toString().length() == 0)) {
                    name.setError("Enter Name");
                }
                if (username.getText().toString().length() == 0) {
                    username.setError("Enter Last Name");

                }
                if (tbirthday.getText().toString().length() == 0) {
                    tbirthday.setError("Enter BirthDay");

                }
                if (register_eemail.getText().toString().length() == 0) {
                    register_eemail.setError("Enter Email Address");

                }
                if (register_epassword.getText().toString().length() == 0) {
                    register_epassword.setError("Enter Password");
                } else {
                    Toast.makeText(Registration.this, "sucessfully register", Toast.LENGTH_SHORT).show();
                    RegistrationMethod(name.getText().toString(), username.getText().toString(), tbirthday.getText().toString(), register_eemail.getText().toString(), register_epassword.getText().toString(), "1234567890", "Female", "surat", "hcuboid", "hello", "5",
                            "9", "0");
                }
            }

        });
    }

    public void onClick(View v) {
        if (v == tbirthday) {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            tbirthday.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }


    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


    public void RegistrationMethod(String name, String username, String tbirthday, String register_eemail,
                                   String register_epassword, String phone_number , String gender,
                                   String address , String company , String title  , String latitude  ,
                                   String longitude ,String type  ) {
        {

            Call<RegistrationPojo> registercall = apiInterface.registerPojoCall(name ,username, tbirthday,
                                    register_eemail , register_epassword, phone_number , gender, address ,
                                    company ,title , latitude , longitude ,0);

            registercall.enqueue(new Callback<RegistrationPojo>() {
                @Override
                public void onResponse(Call<RegistrationPojo> call, Response<RegistrationPojo> response) {
                    if (response.body().getStatus().equals("Success")) {
                        Intent it = new Intent(getApplicationContext(), HomePage.class);
                        startActivity(it);
                    }
                    else
                    {
                        Toast.makeText(Registration.this,response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }


                @Override
                public void onFailure(Call<RegistrationPojo> call, Throwable t) {

                }
            });
        }
    }
}

