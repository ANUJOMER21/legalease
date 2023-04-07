package client.legalease;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import client.legalease.Model.LoginModel.User;
import client.legalease.Model.QualificationModel.QualificationData;
import client.legalease.Model.QualificationModel.QualificationModel;
import client.legalease.Model.StateListModel.StateData;
import client.legalease.Model.StateListModel.StateModel;
import client.legalease.Model.UpdateUserProfile.UserUpdate;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.WebServices.ApiService;
import client.legalease.WebServices.CallApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserMoreDetailActivity extends AppCompatActivity {

    Spinner spinnerState;
    TextView skip;
    String id = "";

    String email   = "";
    String name = "";
    String state ="4";
    TextView tv_dob;

    String dob = "";
    Button update;
    ProgressDialog pDialog;
    CallApi callApi = new CallApi();
    User userdata;
    CommonSharedPreference commonSharedPreference;
    private int mYear, mMonth, mDay, mHour, mMinute;
    EditText et_name,et_email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_more_detail);
        initializeViews();
        commonSharedPreference  = new CommonSharedPreference(getApplicationContext());

        try {
            Intent intent = getIntent();
            id = intent.getStringExtra("id");

            et_name.setText(name);
        }catch (NullPointerException ignored){

        }catch (IndexOutOfBoundsException ignore){

        }
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(UserMoreDetailActivity.this,LoginActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });


        tv_dob.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(UserMoreDetailActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                tv_dob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dob = tv_dob.getText().toString().trim();
                name =et_name.getText().toString().trim();
                email=et_email.getText().toString().trim();


                pDialog = new ProgressDialog(UserMoreDetailActivity.this);
                String pleaseWait = getResources().getString(R.string.please_wait);

                pDialog.setMessage(pleaseWait);
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();
                HashMap<String, String> updateCrediantial = new HashMap<>();
                updateCrediantial.put("id", id);
                updateCrediantial.put("dob", dob);
                updateCrediantial.put("name", name);
                    updateCrediantial.put("email", email);
                    updateCrediantial.put("state", state);



                    try {
                    callApi.updateUserProfile(UserMoreDetailActivity.this, updateCrediantial);
                } catch (Exception e) {
                    e.getStackTrace();


                }

            }
        });
    }




        public void Responce_Update(UserUpdate responce) {
        pDialog.dismiss();
        String id = "";

            String pincode = "";
            String address = "";

            try {
            id = String.valueOf(responce.getUser().get(0).getId());

             pincode = responce.getUser().get(0).getPincode();
             address = responce.getUser().get(0).getAddress();

        }catch (NullPointerException ignored){

        }catch (IndexOutOfBoundsException ignore){

        }

            userdata = new User();

            userdata.setPincode(pincode);
            userdata.setAddress(address);

            Log.d("dsfbf", String.valueOf(userdata));
//            commonSharedPreference.setLoginSharedPref(this, userdata);
            Log.d("sdfbvhgdsf", String.valueOf(commonSharedPreference));
            Intent intent = new Intent(UserMoreDetailActivity.this,LoginActivity.class);
            startActivity(intent);
            finishAffinity();
    }

    private void initializeViews() {
        skip = (TextView)findViewById(R.id.skip);
        update = (Button)findViewById(R.id.update);
        et_name = (EditText)findViewById(R.id.name);
        et_email = (EditText)findViewById(R.id.et_email);

        tv_dob = (TextView)findViewById(R.id.tv_dob);




    }


    public static class RetroClient {

        private static final String ROOT_URL = "https://professionalsaathi.com/api/v1/";
        private static Retrofit getRetrofitInstance() {
            return new Retrofit.Builder()
                    .baseUrl(ROOT_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        public static ApiService getApiService() {
            return getRetrofitInstance().create(ApiService.class);
        }
    }

    public void clossDialog() {
        pDialog.dismiss();
        Toast.makeText(getApplicationContext(),"Please check your internet connection or try after sometime",Toast.LENGTH_SHORT).show();

    }
    }
