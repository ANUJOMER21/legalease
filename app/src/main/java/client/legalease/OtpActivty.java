package client.legalease;

import static client.legalease.APIConstant.ApiConstant.IMAGEURL;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import client.legalease.Model.VERIFYOTP.OtpVerificationModel;
import client.legalease.Model.VERIFYOTP.User;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.WebServices.CallApi;

public class OtpActivty extends AppCompatActivity {
    Button click;

    EditText et1, et2, et3, et4;
    int editEextLength = 1;
    ProgressDialog pDialog1;
    CallApi callApi = new CallApi();
    CommonSharedPreference commonSharedPreference;
    String privileges = "";
    String mobile = "";
    String otpFromResponse = "";
    String fotp = "";
    User userdata;
    String referralcode= "";
    String name ="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_activty);
        commonSharedPreference = new CommonSharedPreference(OtpActivty.this);
        try {
            Intent intent = getIntent();
            mobile = intent.getStringExtra("mobile");
            otpFromResponse = intent.getStringExtra("otpFromResponse");
            referralcode = intent.getStringExtra("referralcode");
            privileges = intent.getStringExtra("privileges");
            name = intent.getStringExtra("name");


        } catch (NullPointerException ignored) {

        }
        initializeViews();
        textChanger();
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyMyOtp();
//                Intent intent = new Intent(OtpActivty.this,HomeActivity.class);
//                startActivity(intent);
            }
        });
    }

    private void initializeViews() {
        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        et3 = (EditText) findViewById(R.id.et3);
        et4 = (EditText) findViewById(R.id.et4);
        click = (Button) findViewById(R.id.verify);

    }


    private void textChanger() {
        // et1
        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (et1.getText().toString().length() == editEextLength) {
                    et2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // et2

        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (et2.getText().toString().length() == editEextLength) {
                    et3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        //et3

        et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (et3.getText().toString().length() == editEextLength) {
                    et4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // e4

        et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if(et4.getText().toString().length()==editEextLength)
//                {
//                    click.requestFocus();
//                    verifyMyOtp();
//
//                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    private void verifyMyOtp() {
        click.setEnabled(true);
        String otp1 = et1.getText().toString().trim();
        String otp2 = et2.getText().toString().trim();
        String otp3 = et3.getText().toString().trim();
        String otp4 = et4.getText().toString().trim();
        String finalOtp = otp1 + otp2 + otp3 + otp4;
        fotp = finalOtp.trim();
        Log.d("sdfbjds", fotp);

        if (otpFromResponse.equals(fotp)){


            pDialog1 = new ProgressDialog(OtpActivty.this);
            pDialog1.setMessage("Please wait, while we are connecting you with Legalease ");
            pDialog1.setIndeterminate(false);
            pDialog1.setCancelable(false);
            pDialog1.show();
            HashMap<String, String> otpCrediantial = new HashMap<>();
            otpCrediantial.put("otp", fotp);
            otpCrediantial.put("mobile", mobile);
            otpCrediantial.put("referralcode", referralcode);
            otpCrediantial.put("privileges", privileges);
            otpCrediantial.put("name", name);




            try {
                callApi.requestOtpVerification(OtpActivty.this, otpCrediantial);
            } catch (Exception e) {
                e.getStackTrace();
            }
        }else {
            Toast.makeText(getApplicationContext(),"OTP does not matched.",Toast.LENGTH_SHORT).show();
        }
    }


    public void Responce_otpVerification(OtpVerificationModel body) {
        pDialog1.dismiss();

        if (body.getStatus().equals("success")) {
            String myToken = body.getToken();
            String mobile = body.getMobile();
            String id = String.valueOf(body.getId());
            String type = String.valueOf(body.getType());
            String name = body.getUser().getName();
            String email = body.getUser().getEmail();
            String referalCode = body.getUser().getReferalcode();
            String wallet = body.getUser().getWallet();
            String image=body.getUser().getPhoto();
            String dob=body.getUser().getDob();
            int pincode=0;
            if(body.getUser().getPincode()!=null) {
               // pincode= (int) body.getUser().getPincode();
            }
            String city=body.getUser().getMobile();
            String state=body.getUser().getState();
            String gst=body.getUser().getGstNo();

   commonSharedPreference.tokenName(myToken);
            Log.d("token", "Token: "+body.getToken());


            if (type.equals("1")){
                Intent intent = new Intent(OtpActivty.this,UserMoreDetailActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("name",name);

                startActivity(intent);
            }else {
                userdata = new User();
                userdata.setToken(myToken);
                userdata.setId(Integer.valueOf(id));
                userdata.setMobile(mobile);
                userdata.setName(name);
                userdata.setEmail(email);
                userdata.setReferalcode(referalCode);
                userdata.setWallet(wallet);
                userdata.setPhoto(image);
                userdata.setDob(dob);
                userdata.setPincode(pincode);
                userdata.setCity(city);
                userdata.setState(state);
                userdata.setGstNo(gst);

                Log.d("dsfbf", String.valueOf(userdata));
                commonSharedPreference.setLoggedin(true);
                commonSharedPreference.setLoginSharedPref(this, userdata);
                commonSharedPreference.setProfilephoto(OtpActivty.this,IMAGEURL+userdata.getPhoto());
                commonSharedPreference.setname(userdata.getName());
                commonSharedPreference.setemail(userdata.getEmail());
                Log.d("image", "Responce_otpVerification: "+userdata.getPhoto());
                Intent intent = new Intent(OtpActivty.this,HomeActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
                finishAffinity();

            }




        } else {

        }
    }
}
