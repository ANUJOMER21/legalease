package client.legalease;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.florent37.androidslidr.Slidr;

import java.util.HashMap;
import java.util.List;

import client.legalease.Adapter.ListOfMethodAdapter;
import client.legalease.Model.AddAccountDetailsModel.AddAccountDetailsModel;
import client.legalease.Model.ListOfMethodModel.ListOfMethodData;
import client.legalease.Model.ListOfMethodModel.ListOfMethodModel;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.WebServices.ApiService;
import client.legalease.WebServices.CallApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RedeemActivtiy extends AppCompatActivity {
    String methodType = "";
    String link = "";
    RecyclerView rv_redeemMethodList;
    ProgressBar progressBar;
    Button btn_redeem;
    TextView tv_addAccount;
    ListOfMethodAdapter listOfMethodAdapter;

    ImageView iv_back;
    TextView tv_hint;

    List<ListOfMethodData> listOfMethodData ;
    CommonSharedPreference commonSharedPreference;
    String token = "";
    Dialog dialog,dialogRedeem;
    CallApi callApi = new CallApi();
    ProgressDialog pDialog,pDialodRedeem;

    TextView sendOtp,verifyOtp;
    EditText et_number,et_upiNumber;
    String mobile ="";
    String ifsc = "";
    ImageView iv_close;
    TextView tv;
    TextView sendRedeem;
    ImageView iv_closeredeem;
    String redeem_amount = "";
    String itemId = "";
    String cashMoney = "";
    ImageView iv_redeemType ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_redeem_activtiy);
        initializeViews();
        commonSharedPreference = new CommonSharedPreference(getApplicationContext());

        try {

            if (commonSharedPreference.loggedin()) {
                token = commonSharedPreference.getLoginSharedPref(getApplicationContext()).getToken();

            } else {
                token = "";

            }
        }catch (NullPointerException ignored){
        }

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        Intent intent = getIntent();
        methodType = intent.getStringExtra("methodId");
        link = intent.getStringExtra("link");
        cashMoney = intent.getStringExtra("cashMoney");

        if (methodType.equals("1")){
            iv_redeemType.setImageResource(R.drawable.paytm_wallet);
        }else if (methodType.equals("2")){
            iv_redeemType.setImageResource(R.drawable.upi_icon);
        }else {
            iv_redeemType.setImageResource(R.drawable.bank_icon);
        }

        getData();

        tv_addAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        btn_redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemId.equals("")){
                    Toast.makeText(getApplicationContext(), "Please select an option to redeem your cash", Toast.LENGTH_SHORT).show();
                }else {
                    openRedeemDialog();
                }
            }
        });
    }

    private void initializeViews() {
        rv_redeemMethodList  =(RecyclerView)findViewById(R.id.rv_redeemMethodList);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        btn_redeem = (Button)findViewById(R.id.btn_redeem);
        tv_addAccount = (TextView)findViewById(R.id.tv_addAccount);
        iv_back = (ImageView)findViewById(R.id.iv_back);
        tv_hint = (TextView)findViewById(R.id.tv_hint);
        iv_redeemType =(ImageView)findViewById(R.id.iv_redeemType);
    }


    private void getData() {

        ApiService api = UserMoreDetailActivity.RetroClient.getApiService();
        Call<ListOfMethodModel> call = api.getmyMethodList(token,methodType);
        call.enqueue(new Callback<ListOfMethodModel>() {
            @Override
            public void onResponse(Call<ListOfMethodModel> call, Response<ListOfMethodModel> response) {
                progressBar .setVisibility(View.GONE);
                try {
                    listOfMethodData = response.body().getData();
                    if (listOfMethodData.size()==0){
                        tv_hint.setVisibility(View.VISIBLE);
                        rv_redeemMethodList.setVisibility(View.GONE);
                    }else {

                        tv_hint.setVisibility(View.GONE);
                        rv_redeemMethodList.setVisibility(View.VISIBLE);

                    }
                }catch (NullPointerException ignored){

                }catch (IndexOutOfBoundsException ignore){

                }


                listOfMethodAdapter = new ListOfMethodAdapter(getApplicationContext(),listOfMethodData);
                RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL, false);
                rv_redeemMethodList.setLayoutManager(eLayoutManager);
//                recyclerView.setItemAnimator(new DefaultItemAnimator());
                rv_redeemMethodList.setAdapter(listOfMethodAdapter);

            }

            @Override
            public void onFailure(Call<ListOfMethodModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();

            }
        });
    }



    private void openRedeemDialog() {

        dialogRedeem = new Dialog(RedeemActivtiy.this);
        dialogRedeem.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogRedeem.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogRedeem.setContentView(R.layout.dialog_redeem);
        dialogRedeem.setCanceledOnTouchOutside(false);
        dialogRedeem.setCancelable(false);
        dialogRedeem.show();
        sendRedeem = (TextView) dialogRedeem.findViewById(R.id.sendRedeem);
        iv_closeredeem = (ImageView) dialogRedeem.findViewById(R.id.iv_closeredeem);

        final Slidr slidr = (Slidr)dialogRedeem.findViewById(R.id.slider);
        float a = 0;
        try {
            a = Float.parseFloat(cashMoney);
        }catch (NullPointerException ignored){
            
        }catch (NumberFormatException ignore){
            
        }

        try {
            slidr.setMax(a);
//        slidr.addStep(new Slidr.Step("test", 250, Color.parseColor("#007E90"), Color.RED));
            slidr.setTextMax("max\namount");
            slidr.setCurrentValue(0);
            slidr.setListener(new Slidr.Listener() {
                @Override
                public void valueChanged(Slidr slidr, float currentValue) {
                    String as = String.valueOf(currentValue);
                    redeem_amount =as;
                    Toast.makeText(getApplicationContext(),redeem_amount,Toast.LENGTH_SHORT).show();

                }

                @Override
                public void bubbleClicked(Slidr slidr) {

                }

            });
        }catch (NullPointerException ignored){

        }catch (ClassCastException ignore){

        }

        sendRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!itemId.equals("")){
                    pDialog = new ProgressDialog(RedeemActivtiy.this);
                    String pleaseWait = "Please wait... we are getting your details to redeem your point";

                    pDialog.setMessage(pleaseWait);
                    pDialog.setIndeterminate(false);
                    pDialog.setCancelable(false);

                    pDialog.show();

                    HashMap<String,String> redeemCredential =new HashMap<>();
                    redeemCredential.put("token",token);
                    redeemCredential.put("redeem_amount",redeem_amount);
                    redeemCredential.put("methodType",itemId);

                    try {
                        callApi.requestRedeem(RedeemActivtiy.this, redeemCredential);
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Please select an account in which your want to redeem your money",Toast.LENGTH_LONG).show();
                }


            }
        });


          iv_closeredeem.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialogRedeem.dismiss();
        }
    });

    }





    private void openDialog() {

        dialog = new Dialog(RedeemActivtiy.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.add_account_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();

         sendOtp = (TextView) dialog.findViewById(R.id.sendOtp);
        verifyOtp = (TextView) dialog.findViewById(R.id.verifyOtp);
        et_number = (EditText) dialog.findViewById(R.id.et_number);
        et_upiNumber = (EditText) dialog.findViewById(R.id.et_upiNumber);

        iv_close = (ImageView) dialog.findViewById(R.id.iv_close);

        tv = (TextView)dialog.findViewById(R.id.tv);
        if (methodType.equals("1")){
            sendOtp.setText("Send Otp");
            et_number.setVisibility(View.VISIBLE);
            et_upiNumber.setVisibility(View.GONE);
            tv.setText("Enter PayTm Number");
        }else if (methodType.equals("2")){
            sendOtp.setText("Register");
            et_number.setVisibility(View.GONE);
            et_upiNumber.setVisibility(View.VISIBLE);
            tv.setText("Enter UPI Id Number");
        }else if (methodType.equals("3")){
            tv.setText("Add Bank Account Details");
            sendOtp.setText("Add Account");
            et_number.setVisibility(View.VISIBLE);
            et_upiNumber.setVisibility(View.VISIBLE);
            et_number.setHint("Account Number");
            et_upiNumber.setHint("IFSC Code");

        }

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        verifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = et_number.getText().toString().trim();
                if (otp.length()>4||otp.length()<4|otp.length()==0){
                    Toast.makeText(getApplicationContext(),"Please enter correct OTP",Toast.LENGTH_SHORT).show();
                }else {
                    pDialog = new ProgressDialog(RedeemActivtiy.this);
                    String pleaseWait = getResources().getString(R.string.please_wait);

                    pDialog.setMessage(pleaseWait);
                    pDialog.setIndeterminate(false);
                    pDialog.setCancelable(false);

                    pDialog.show();

                    HashMap<String,String> sendOtpCredential = new HashMap<String, String>();
                    sendOtpCredential.put("mobile",mobile);
                    sendOtpCredential.put("methodType",methodType);
                    sendOtpCredential.put("token",token);
                    sendOtpCredential.put("otp",otp);



                    try {
                        callApi.requestVerifyOtp(RedeemActivtiy.this, sendOtpCredential);
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }

            }
        });

        sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (methodType.equals("1")){
                    mobile = et_number.getText().toString().trim();

                }else if (methodType.equals("2")){
                    mobile =et_upiNumber.getText().toString().trim();
                }else if (methodType.equals("3")){
                    mobile = et_number.getText().toString().trim();
                    ifsc = et_upiNumber.getText().toString().trim();
                }
                 if (methodType.equals("1")){
                     if (mobile.length()>10||mobile.length()<10||mobile.length()==0){
                         Toast.makeText(getApplicationContext(),"Please enter valid phone number",Toast.LENGTH_SHORT).show();
                     }else {
                         pDialog = new ProgressDialog(RedeemActivtiy.this);
                         String pleaseWait = getResources().getString(R.string.please_wait);

                         pDialog.setMessage(pleaseWait);
                         pDialog.setIndeterminate(false);
                         pDialog.setCancelable(false);

                         pDialog.show();

                         HashMap<String,String> sendOtpCredential = new HashMap<String, String>();
                         sendOtpCredential.put("mobile",mobile);
                         sendOtpCredential.put("methodType",methodType);
                         sendOtpCredential.put("token",token);


                         try {
                             callApi.requestSendOtp(RedeemActivtiy.this, sendOtpCredential);
                         } catch (Exception e) {
                             e.getStackTrace();
                         }
                     }
                 }else if (methodType.equals("2")){
                     if (mobile.length()==0){
                         Toast.makeText(getApplicationContext(),"Please enter valid UPI Id",Toast.LENGTH_SHORT).show();
                     }else {
                         pDialog = new ProgressDialog(RedeemActivtiy.this);
                         String pleaseWait = getResources().getString(R.string.please_wait);

                         pDialog.setMessage(pleaseWait);
                         pDialog.setIndeterminate(false);
                         pDialog.setCancelable(false);

                         pDialog.show();

                         HashMap<String,String> sendOtpCredential = new HashMap<String, String>();
                         sendOtpCredential.put("mobile",mobile);
                         sendOtpCredential.put("methodType",methodType);
                         sendOtpCredential.put("token",token);


                         try {
                             callApi.requestSendOtp(RedeemActivtiy.this, sendOtpCredential);
                         } catch (Exception e) {
                             e.getStackTrace();
                         }
                     }
                 }else if (methodType.equals("3")){
                     if (mobile.length()==0){
                         Toast.makeText(getApplicationContext(),"Please enter valid Account Number",Toast.LENGTH_SHORT).show();
                     }else if (ifsc.length()==0){
                         Toast.makeText(getApplicationContext(),"Please enter valid IFSC Number",Toast.LENGTH_SHORT).show();
                     }else {
                         pDialog = new ProgressDialog(RedeemActivtiy.this);
                         String pleaseWait = getResources().getString(R.string.please_wait);

                         pDialog.setMessage(pleaseWait);
                         pDialog.setIndeterminate(false);
                         pDialog.setCancelable(false);

                         pDialog.show();

                         HashMap<String,String> sendOtpCredential = new HashMap<String, String>();
                         sendOtpCredential.put("mobile",mobile);
                         sendOtpCredential.put("methodType",methodType);
                         sendOtpCredential.put("token",token);
                         sendOtpCredential.put("ifsc",ifsc);


                         try {
                             callApi.requestSendOtp(RedeemActivtiy.this, sendOtpCredential);
                         } catch (Exception e) {
                             e.getStackTrace();
                         }
                     }
                 }


            }
        });

    }


    public void Response_Login(AddAccountDetailsModel body) {
        String status = "";
        pDialog.dismiss();

        try {
             status = body.getStatus();
             if (status.equals("success")){

                 if (methodType.equals("1")){
                     et_number.setText("");
                     et_number.setHint("Enter Otp");
                     sendOtp.setVisibility(View.GONE);
                     verifyOtp.setVisibility(View.VISIBLE);
                     Toast.makeText(getApplicationContext(),"Otp is sent to your entered mobile number",Toast.LENGTH_SHORT).show();
                 }else if (methodType.equals("2")){
                     getData();
                     Toast.makeText(getApplicationContext(),"Your entered UPI Id is succesfully regidtered to your account",Toast.LENGTH_SHORT).show();
                     dialog.dismiss();
                 }else if (methodType.equals("3")){
                     getData();
                     Toast.makeText(getApplicationContext(),"Your bank account is successfully added",Toast.LENGTH_SHORT).show();
                     dialog.dismiss();
                 }
             }else{
                 if (methodType.equals("1")) {
                     Toast.makeText(getApplicationContext(), "Please get an another OTP", Toast.LENGTH_SHORT).show();
                 }else{
                     Toast.makeText(getApplicationContext(), "We are not able to add your account now...Please try after sometime", Toast.LENGTH_SHORT).show();
                 }
                 }


        }catch (NullPointerException ignored){
            Toast.makeText(getApplicationContext(),"Something went wrong... Please try after sometimes",Toast.LENGTH_SHORT).show();

        }

    }

    public void clossDialog() {
        pDialog.dismiss();
        Toast.makeText(getApplicationContext(),"Something went wrong... Please try after sometimes",Toast.LENGTH_SHORT).show();
    }

    public void Response_verifyOtp(AddAccountDetailsModel body) {
        String status ="";
        if (body.getStatus().equals("success")){
            getData();
            Toast.makeText(getApplicationContext(),"Your account is successfully created",Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            pDialog.dismiss();
        }
    }

    public void Response_redeem(AddAccountDetailsModel body) {
        String status = "";
        if (body.getStatus().equals("success")){
            Toast.makeText(getApplicationContext(),"You have successfully REDEEM your selected amount",Toast.LENGTH_SHORT).show();
            pDialog.dismiss();
            dialogRedeem.dismiss();
        }else {
            Toast.makeText(getApplicationContext(),"Something went wrong... please try after sometimes",Toast.LENGTH_SHORT).show();

        }

    }


    public class ListOfMethodAdapter extends RecyclerView.Adapter<ListOfMethodAdapter.ViewHolder> {

        private RadioButton lastCheckedRB;

        public  List<ListOfMethodData> listOfMethodData;
        private Context context;

        public ListOfMethodAdapter(Context context, List<ListOfMethodData> listOfMethodData) {
            this.context = context;
            this.listOfMethodData = listOfMethodData;
        }



        @NonNull
        @Override
        public ListOfMethodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_of_method_type, viewGroup, false);


            return new ListOfMethodAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ListOfMethodAdapter.ViewHolder viewHolder, final int i) {

            String item ="";




            try {

                item = listOfMethodData.get(i).getAccount();

                viewHolder.tv_method.setText(item);


            }catch (NullPointerException e){

            }catch (IndexOutOfBoundsException ignore){

            }


        }



        @Override
        public int getItemCount() {
            return listOfMethodData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView tv_method;
            RadioButton radio_method;
            ImageView iv_delete;

            public ViewHolder(@NonNull final View itemView) {
                super(itemView);

                tv_method = (TextView) itemView.findViewById(R.id.tv_method);
                radio_method  =(RadioButton)itemView.findViewById(R.id.radio_method);
                iv_delete = (ImageView)itemView.findViewById(R.id.iv_delete);


                radio_method.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        ListOfMethodData myData = null;
                        try {
                            myData = listOfMethodData.get(position);
                            itemId = myData.getId();
                            Toast.makeText(getApplicationContext(),itemId,Toast.LENGTH_SHORT).show();
                        }catch (NullPointerException ignored){

                        }
                        radio_method.setChecked(true);
                        if(lastCheckedRB != null){
                            lastCheckedRB.setChecked(false);
                        }
                        lastCheckedRB = radio_method;
                    }
                });

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        ListOfMethodData myData = null;
                        try {
                            myData = listOfMethodData.get(position);
                            itemId = myData.getId();
                            Toast.makeText(getApplicationContext(),itemId,Toast.LENGTH_SHORT).show();
                        }catch (NullPointerException ignored){

                        }
                        radio_method.setChecked(true);
                        if(lastCheckedRB != null){
                            lastCheckedRB.setChecked(false);
                        }
                        lastCheckedRB = radio_method;
                    }
                });

                iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"duhas sauhbsajk",Toast.LENGTH_SHORT).show();
                    int pos =getAdapterPosition();
                    ListOfMethodData myData =null;
                    String methodType = "";
                    String id = "";
                    try {
                        myData = listOfMethodData.get(pos);
                        methodType  =myData.getMethodType();
                        id = myData.getId();


                        ApiService api = UserMoreDetailActivity.RetroClient.getApiService();
                        Call<ListOfMethodModel> call = api.getMyDeletedData(token,methodType,id);
                        call.enqueue(new Callback<ListOfMethodModel>() {
                            @Override
                            public void onResponse(Call<ListOfMethodModel> call, Response<ListOfMethodModel> response) {

                                try {
                                    if (response.body().getStatus().equals("success")){
                                        progressBar .setVisibility(View.GONE);
                                        try {
                                            listOfMethodData = response.body().getData();
                                            if (listOfMethodData.size()==0){
                                                tv_hint.setVisibility(View.VISIBLE);
                                                rv_redeemMethodList.setVisibility(View.GONE);
                                            }else {

                                                tv_hint.setVisibility(View.GONE);
                                                rv_redeemMethodList.setVisibility(View.VISIBLE);

                                            }
                                        }catch (NullPointerException ignored){

                                        }catch (IndexOutOfBoundsException ignore){

                                        }


                                        listOfMethodAdapter = new ListOfMethodAdapter(getApplicationContext(),listOfMethodData);
                                        RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL, false);
                                        rv_redeemMethodList.setLayoutManager(eLayoutManager);
//                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                        rv_redeemMethodList.setAdapter(listOfMethodAdapter);
                                    }else {
                                        Toast.makeText(getApplicationContext(),"Something went wrong... please try after sometimes",Toast.LENGTH_SHORT).show();
                                    }
                                }catch (NullPointerException ignored){
                                    getData();
                                }

                            }

                            @Override
                            public void onFailure(Call<ListOfMethodModel> call, Throwable t) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();

                            }
                        });



                    }catch (NullPointerException ignored){

                    }catch (Exception ignore){

                    }


                }
            });


            }
        }
    }

}

