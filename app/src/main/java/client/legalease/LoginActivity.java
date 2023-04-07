package client.legalease;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.HashMap;

import br.com.joinersa.oooalertdialog.Animation;
import br.com.joinersa.oooalertdialog.OnClickListener;
import br.com.joinersa.oooalertdialog.OoOAlertDialog;
import client.legalease.Model.LoginModel.LoginModel;
import client.legalease.Model.LoginModel.User;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.WebServices.CallApi;

public class LoginActivity extends AppCompatActivity {
    EditText et_email,et_password;
    String mobile = "";
    Button login;
    TextView register;
    ProgressDialog pDialog;
    CallApi callApi = new CallApi();
    CommonSharedPreference commonSharedPreference;
    User userdata;
    String privileges = "5";

    String[] permissionsRequired = new String[]{ Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_CONTACTS};

    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    int STORAGE_PERMISSION_CODE = 1;

    private static final int MY_CAMERA_REQUEST_CODE = 100;
    TextView tv_forgetPassword;
    EditText et_referalCode,et_name;
    String name ="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_login);

        commonSharedPreference =new CommonSharedPreference(this);
        initializeViews();
        tv_forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ForgetPassword.class);
                startActivity(intent);
            }
        });



        if (Build.VERSION.SDK_INT >= 23) {
            givePermission();
//        askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, WRITE_EXST);
//        askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE, READ_EXST);
//            getPermissionToReadUserContacts();


        }



        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_CAMERA_REQUEST_CODE);
            }
        }








        if (commonSharedPreference.loggedin()){
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();


            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void login() {
        mobile = et_email.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
         name =et_name.getText().toString().trim();


        if (name.equals("")){
            et_name.setError("Please enter name");
            et_name.requestFocus();
        }else if (mobile.equals("")||mobile.length()<10||mobile.length()>10){
            et_email.setError("Please enter correct phone number");
            et_email.requestFocus();
        }else {
            pDialog = new ProgressDialog(LoginActivity.this);
            String pleaseWait = getResources().getString(R.string.please_wait);

            pDialog.setMessage(pleaseWait);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);

            pDialog.show();
            HashMap<String,String> loginCredential = new HashMap<>();
            loginCredential.put("mobile",mobile);
            loginCredential.put("referralcode",et_referalCode.getText().toString().trim());
            loginCredential.put("privileges",privileges);
            loginCredential.put("name",name);



            try {
                callApi.requestLogIn(LoginActivity.this, loginCredential);
            } catch (Exception e) {
                e.getStackTrace();
            }
        }

    }

    public void Response_Login(LoginModel responce) {
        pDialog.dismiss();
        String status = "";
        try {
            Log.d("Fas", String.valueOf(responce));
            Log.d("Fas", String.valueOf(responce));

            status = responce.getStatus();
            Log.d("ads",status);
        } catch (NullPointerException ignored) {

        }
        if (status.equals("success")) {

            String otpFromResponse ="";
            try {
                otpFromResponse = String.valueOf(responce.getOtp());
                Log.d("OTP",otpFromResponse);
            }catch (NullPointerException ignored){

            }


            Intent intent =new Intent(LoginActivity.this,OtpActivty.class);
            intent.putExtra("mobile",mobile);
            intent.putExtra("referralcode",et_referalCode.getText().toString().trim());
            intent.putExtra("otpFromResponse",otpFromResponse);
            intent.putExtra("privileges",privileges);
            intent.putExtra("name",name);


            startActivity(intent);
                finish();


//
//        int id = 0;
//        String myToken = "";
//        String name = "";
//        String email = "";
//        String id_cms_privileges = "";
//        String mobile = "";
//        String country = "";
//        String state = "";
//        String city = "";
//        String pincode = "";
//        String address = "";
//        String qualification = "";
//        String imageUrl = "";
//        String referal = "";
//        String walletpoint = "";
//        String StateName = "";
//        try {
//            myToken = responce.getToken();
//            id = responce.getUser().get(0).getId();
//            name = responce.getUser().get(0).getName();
//            email = responce.getUser().get(0).getEmail();
//            id_cms_privileges = responce.getUser().get(0).getIdCmsPrivileges();
//            mobile = responce.getUser().get(0).getMobile();
//            country = responce.getUser().get(0).getCountry();
//            state = responce.getUser().get(0).getState();
//            city = responce.getUser().get(0).getCity();
//            pincode = responce.getUser().get(0).getPincode();
//            address = responce.getUser().get(0).getAddress();
//            qualification = responce.getUser().get(0).getQualification();
//            referal=responce.getUser().get(0).getReferalcode();
//            walletpoint = responce.getUser().get(0).getWallet();
//            StateName = responce.getUser().get(0).getStateName();
//
//
//            userdata = new User();
//            userdata.setId(id);
//            userdata.setToken(myToken);
//            userdata.setName(name);
//            userdata.setEmail(email);
//            userdata.setIdCmsPrivileges(id_cms_privileges);
//            userdata.setMobile(mobile);
//            userdata.setCountry(country);
//            userdata.setState(state);
//            userdata.setCity(city);
//            userdata.setPincode(pincode);
//            userdata.setAddress(address);
//            userdata.setQualification(qualification);
//            userdata.setPhoto(imageUrl);
//            userdata.setReferalcode(referal);
//            userdata.setWallet(walletpoint);
//            userdata.setStateName(StateName);
//
//
//
//
//            Log.d("dsfbf", String.valueOf(userdata));
//            commonSharedPreference.setLoggedin(true);
//            commonSharedPreference.setLoginSharedPref(this, userdata);
//
//        } catch (NullPointerException ignored) {
//
//        } catch (IndexOutOfBoundsException ignore) {
//
//        }
//
//
//        try {
//            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//            intent.putExtra("id", id);
//            startActivity(intent);
//            finish();
//        } catch (NullPointerException ignored) {
//
//        } catch (IndexOutOfBoundsException ignore) {
//
//        }
    }else {
            new OoOAlertDialog.Builder(LoginActivity.this)
                    .setTitle("Incoreect Mobile Number")
                    .setMessage("Please enter correct Mobile number to login")
                    .setAnimation(Animation.POP)
                    .setNegativeButton("Login Again", null)
                    .build();
        }
    }

    private void initializeViews() {
        et_email = (EditText)findViewById(R.id.et_email);
        et_name = (EditText)findViewById(R.id.et_name);
        et_referalCode  =(EditText)findViewById(R.id.et_referalCode);
        login = (Button)findViewById(R.id.login);
        register = (TextView)findViewById(R.id.register);
        tv_forgetPassword = (TextView)findViewById(R.id.tv_forgetPassword);


    }

    public void clossDialog() {
        pDialog.dismiss();
        Toast.makeText(getApplicationContext(),"Something went wrong... please try after some time",Toast.LENGTH_SHORT).show();

    }
















    private void givePermission() {
        try {


            if(ActivityCompat.checkSelfPermission(LoginActivity.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(LoginActivity.this, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(LoginActivity.this, permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, permissionsRequired[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, permissionsRequired[1])
                        || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, permissionsRequired[2])) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("Please Give Following Permissions");
                    builder.setMessage("Camera \n Write External Storage \n Read Contect");
                    builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(LoginActivity.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                        }
                    });
                    builder.setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else {
                    //You already have the permission, just go ahead.
                    proceedAfterPermission();
                }
            }

        }catch (Exception e){

        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_CALLBACK_CONSTANT){
            //check if all permissions are granted
            boolean allgranted = false;
            for(int i=0;i<grantResults.length;i++){
                if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            if(allgranted){
                proceedAfterPermission();
            } else if(ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,permissionsRequired[2])){
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Please provide all the permissions");
                builder.setMessage("Camera \n Write External Storage \n Read Contect");
                builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(LoginActivity.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                Toast.makeText(getBaseContext(),"Unable to get Permission",Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(LoginActivity.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }

    private void proceedAfterPermission() {
//        Toast.makeText(getBaseContext(), "We got All Permissions", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
