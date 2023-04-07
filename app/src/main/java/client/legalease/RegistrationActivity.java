package client.legalease;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.Settings;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

import client.legalease.Model.UserRegistration.Registration;
import client.legalease.WebServices.CallApi;

public class RegistrationActivity extends AppCompatActivity {

    Button registration;
    EditText et_name, et_email, et_mobile, et_password, et_confirmPassword;
    TextView tv_login;
    String name = "";
    String email = "";
    String mobile = "";
    String password = "";
    String confirmPassword = "";
    ProgressDialog pDialog;
    CallApi callApi = new CallApi();
    String privileges = "5";
    String referalcode= "";
    TextView tv_ref;
    TextInputLayout input_refer;
    EditText et_refer;
    String androidId="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);


         try {
             androidId = Settings.Secure.getString(getContentResolver(),
                     Settings.Secure.ANDROID_ID);
         }catch (NullPointerException ignored){

         }
        Toast.makeText(getApplicationContext(),androidId,Toast.LENGTH_SHORT).show();

        initializeView();
        tv_ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_ref.setVisibility(View.GONE);
                input_refer.setVisibility(View.VISIBLE);
            }
        });
        et_confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password =et_password.getText().toString().trim();
                if (et_confirmPassword.getText().toString().trim().equals(password)){
                    registration.setBackgroundColor(getResources().getColor(R.color.red));

                }else {
                    registration.setBackgroundColor(getResources().getColor(R.color.redlight));

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRegistration();

            }
        });

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });

    }

    private void userRegistration() {

            name = et_name.getText().toString().trim();
            email = et_email.getText().toString().trim();
            mobile = et_mobile.getText().toString().trim();
            password = et_password.getText().toString().trim();
            confirmPassword = et_confirmPassword.getText().toString().trim();
            referalcode = et_refer.getText().toString().trim();

            if (name.equals("")){
                et_name.setError("Please enter name");
                et_name.requestFocus();
            }else  if (email.equals("")){
                et_email.setError("Please enter EMAIL id");
                et_email.requestFocus();
            }else  if (mobile.equals("")){
                et_mobile.setError("Please enter MOBILE number");
                et_mobile.requestFocus();
            }else  if (password.equals("")){
                et_password.setError("Please enter password");
                et_password.requestFocus();
            }else  if (confirmPassword.equals("")){
                et_confirmPassword.setError("Please enter confirm password");
                et_confirmPassword.requestFocus();
            }else if (!password.equals(confirmPassword)){
                Toast.makeText(getApplicationContext(),"Password Mismatched",Toast.LENGTH_SHORT).show();
            }else {
                pDialog = new ProgressDialog(RegistrationActivity.this);
                String pleaseWait = getResources().getString(R.string.please_wait);

                pDialog.setMessage(pleaseWait);
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();
                HashMap<String, String> registrationCrediantial = new HashMap<>();
                registrationCrediantial.put("name",  name);
                registrationCrediantial.put("email",  email);
                registrationCrediantial.put("mobile",  mobile);
                registrationCrediantial.put("password",  password);
                registrationCrediantial.put("privileges",  privileges);
                registrationCrediantial.put("referalcode",  referalcode);
                registrationCrediantial.put("minumber",  androidId);





                try {
                    callApi.requestSignUp(RegistrationActivity.this, registrationCrediantial);
                } catch (Exception e) {
                    e.getStackTrace();
                }

            }

    }

    public void Responce_SignUp(Registration responce) {
        pDialog.dismiss();
        String id = "";
        String status = "";
        String message = "";
        try {
            status = responce.getStatus();
        }catch (NullPointerException ignored){

        }
        if (status.equals("failed")) {
            try {
                message=responce.getMessage();
            }catch (NullPointerException ignored){
            }
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        } else {
            try {
                id = String.valueOf(responce.getUser().getId());
                Intent intent = new Intent(RegistrationActivity.this, UserMoreDetailActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                finish();
            } catch (NullPointerException ignored) {

            } catch (IndexOutOfBoundsException ignore) {

            }
        }
    }

    private void initializeView() {
        registration = (Button) findViewById(R.id.registration);
        et_name = (EditText) findViewById(R.id.et_name);
        et_email = (EditText) findViewById(R.id.et_email);
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        et_password = (EditText) findViewById(R.id.et_password);
        et_confirmPassword = (EditText) findViewById(R.id.et_confirmPassword);

        tv_login = (TextView) findViewById(R.id.tv_login);
        tv_ref =(TextView)findViewById(R.id.tv_ref);
        input_refer = (TextInputLayout)findViewById(R.id.input_refer);
        et_refer = (EditText)findViewById(R.id.et_refer);


    }

    public void clossDialog() {
        pDialog.dismiss();
        Toast.makeText(getApplicationContext(),"Please check your internet connection or try after sometime",Toast.LENGTH_SHORT).show();

    }
}
