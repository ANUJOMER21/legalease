package client.legalease;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import client.legalease.Model.PayTmModel.PayTmModel;
import client.legalease.Model.UserPurchaseModel.PurchaseUserDetails;
import client.legalease.Model.UserPurchaseModel.User;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.RetrofitClient.RetrofitClient;
import client.legalease.WebServices.ApiService;
import client.legalease.WebServices.CallApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaceServiceActicty extends AppCompatActivity {


    String id = "";
    String name = "";
    EditText et_name,et_email,et_phone,et_state,et_company;
    Button submit_form;
    public static final String MID = "doupFO21714400182304";
    public  static  final String CHANNEL_ID = "WAP";
    public static final String WEBSITE= "DEFAULT";
    public static final String CALLBACK_URL = "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp";

    String token = "";
    String INDUSTRY_TYPE_ID = "Retail";
    String TXN_AMOUNT = "";

    String ORDER_ID = "";
    String checkSum  ="";
    CommonSharedPreference commonSharedPreference;
    CallApi callApi = new CallApi();
    ProgressDialog pDialog;
    String customerId="";
    String nameCompany ="";
    String state = "";
    String serviceId = "";
    String Email="";
    String PHONE = "";
    ImageView back;
    List<User> purchaseUserDetails = new ArrayList<>();
    ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchace_service_acticty);

        initializeViews();







        try {
            Intent intent = getIntent();
            id =intent.getStringExtra("id");
            name = intent.getStringExtra("name");
            TXN_AMOUNT =intent.getStringExtra("price");

        }catch (NullPointerException ignored){

        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                Intent intent = new Intent(PurchaceServiceActicty.this,ServicesDetails.class);
//                intent.putExtra("parentId", id);
//                intent.putExtra("name", name);
//                intent.putExtra("price", TXN_AMOUNT);
//                startActivity(intent);

            }
        });

        commonSharedPreference = new CommonSharedPreference(getApplicationContext());

        try {
            if (commonSharedPreference.getLoginSharedPref(getApplicationContext()).getToken() != null) {
                token = commonSharedPreference.getLoginSharedPref(getApplicationContext()).getToken();
                customerId= String.valueOf(commonSharedPreference.getLoginSharedPref(getApplicationContext()).getId());
//                nameCompany =commonSharedPreference.getLoginSharedPref(getApplicationContext()).getName();
//                Email=commonSharedPreference.getLoginSharedPref(getApplicationContext()).getEmail();
                PHONE=commonSharedPreference.getLoginSharedPref(getApplicationContext()).getMobile();
//                state=commonSharedPreference.getLoginSharedPref(getApplicationContext()).getStateName();


            }else {
                token = "";
            }
        }catch (NullPointerException ignored){

        }


        serDetails();

//
        et_company.setText(name);
//        et_email.setText(Email);
//        et_phone.setText(PHONE);
//        et_state.setText(state);
        submit_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    private void serDetails() {
        ApiService api = RetrofitClient.getApiService();
        Call<PurchaseUserDetails> call = api.getPurchaseUserDetails(token);
        call.enqueue(new Callback<PurchaseUserDetails>() {
            @Override
            public void onResponse(Call<PurchaseUserDetails> call, Response<PurchaseUserDetails> response) {
                progressBar.setVisibility(View.GONE);
                String name = "";
                String email ="";
                String phone = "";
                String state = "";
                try {
                    purchaseUserDetails =response.body().getUser();
                    Log.d("fds", String.valueOf(purchaseUserDetails));
                    name = purchaseUserDetails.get(0).getName();
                    email = purchaseUserDetails.get(0).getEmail();
                    phone = purchaseUserDetails.get(0).getMobile();
                    et_name.setText(name);
                    et_email.setText(email);
                    et_phone.setText(phone);
                    state = purchaseUserDetails.get(0).getStateName();



                    et_state.setText(state);
                    Log.d("Dsf",name);
                    Log.d("email",email);
                    Log.d("phone",phone);
                    Log.d("state",state);
                    Log.d("stdsfsxate",state);



                }catch (NullPointerException ignored){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Please enter the remaining details manually",Toast.LENGTH_SHORT).show();

                }



            }

            @Override
            public void onFailure(Call<PurchaseUserDetails> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"Data loading failed... Please enter the details manually",Toast.LENGTH_SHORT).show();

            }
        });

    }

    //This is to refresh the order id: Only for the Sample App's purpose.
    @Override
    protected void onStart() {
        super.onStart();
        //initOrderId();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }


    private void submitForm() {


        GenerateCheckSum();

//        FragmentTransaction transaction = PurchaceServiceActicty.this.getSupportFragmentManager().beginTransaction();
//        Fragment fragment = new MyOrder();
//        transaction.replace(R.id.frame_container,fragment);
//        transaction.commit();
    }

    private void GenerateCheckSum() {


        String name = "";
        String email = "";
        String mobile = "";
        String state = "";


        Random r = new Random(System.currentTimeMillis());
        ORDER_ID = "" + (1 + r.nextInt(2)) * 10000 + r.nextInt(10000);


        name = et_name.getText().toString().trim();
        email = et_email.getText().toString().trim();

        state = et_state.getText().toString().trim();
        PHONE =et_phone.getText().toString().trim();
        serviceId = id;
        if (name.equals("")) {
            et_name.setError("Please enter your name");
            et_name.requestFocus();
        }else if (email.equals("")) {
            et_email.setError("Please enter emaiL id");
            et_email.requestFocus();
        } else if (state.equals("")) {
            et_state.setError("Please enter your state");
            et_state.requestFocus();
        } else {

            pDialog = new ProgressDialog(PurchaceServiceActicty.this);
            String pleaseWait = getResources().getString(R.string.please_wait);

            pDialog.setMessage(pleaseWait);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            HashMap<String, String> paytmCredential = new HashMap<String, String>();
            paytmCredential.put("token", token);
            paytmCredential.put("name", name);
            paytmCredential.put("EMAIL", email);
            paytmCredential.put("PHONE", PHONE);
            paytmCredential.put("state", state);
            paytmCredential.put("serviceId", serviceId);
            paytmCredential.put("ORDER_ID", ORDER_ID);
            paytmCredential.put("TXN_AMOUNT", TXN_AMOUNT);



            try {
                callApi.paytmRequest(PurchaceServiceActicty.this, paytmCredential);
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
    }


    private void initializeViews() {
        et_name= (EditText)findViewById(R.id.et_name);
        et_email= (EditText)findViewById(R.id.et_email);
        et_phone= (EditText)findViewById(R.id.et_phone);
        et_state= (EditText)findViewById(R.id.et_state);
        et_company= (EditText)findViewById(R.id.et_company);
        submit_form =(Button)findViewById(R.id.submit_form);
        back =(ImageView)findViewById(R.id.back);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

    }

    public void clossDialog() {
        pDialog.dismiss();
        Toast.makeText(getApplicationContext(),"Something went wrong... please try after sometime",Toast.LENGTH_SHORT).show();
    }

    public void Response_MakeNote(PayTmModel body) {
        pDialog.dismiss();
        checkSum = body.getCHECKSUMHASH();
        Toast.makeText(getApplicationContext(),"Your Order has been succesfully generated",Toast.LENGTH_SHORT).show();


        Intent intent = new Intent(PurchaceServiceActicty.this,HomeActivity.class);
        intent.putExtra("fragChange","11");

        startActivity(intent);
//            onStartTransaction(checkSum);

    }







    // Paytm section



    public void onStartTransaction(String checkSum) {

        final PaytmPGService Service = PaytmPGService.getProductionService();
        HashMap<String, String> paramMap = new HashMap<String, String>();

        // these are mandatory parameters

        paramMap.put("CALLBACK_URL",CALLBACK_URL);
        paramMap.put("CHANNEL_ID",CHANNEL_ID);
        paramMap.put("CHECKSUMHASH", checkSum);
        paramMap.put("CUST_ID",customerId);
        paramMap.put("INDUSTRY_TYPE_ID",INDUSTRY_TYPE_ID);
        paramMap.put("MID",MID);
        paramMap.put("ORDER_ID",ORDER_ID);
        paramMap.put("TXN_AMOUNT","1");
        paramMap.put("WEBSITE",WEBSITE);
//        paramMap.put("PHONE", phone);
//        paramMap.put("EMAIL", email);






        PaytmOrder Order = new PaytmOrder(paramMap);


        Service.initialize(Order, null);

        Service.startPaymentTransaction(this, true, true,
                new PaytmPaymentTransactionCallback() {
                    @Override
                    public void someUIErrorOccurred(String inErrorMessage) {
                        Log.d("sdf fn",inErrorMessage);

                    }


                    @Override
                    public void onTransactionResponse(Bundle inResponse) {



                        Log.d("LOG", "Payment Transaction is successful " + inResponse);

                        sendResponse(inResponse);
//                        Toast.makeText(getApplicationContext(), "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void networkNotAvailable() { // If network is not
                        // available, then this
                        // method gets called.
                    }

                    @Override
                    public void clientAuthenticationFailed(String inErrorMessage) {
                        Log.d("vhvh",inErrorMessage);

                    }

                    @Override
                    public void onErrorLoadingWebPage(int iniErrorCode,
                                                      String inErrorMessage, String inFailingUrl) {

                    }

                    // had to be added: NOTE
                    @Override
                    public void onBackPressedCancelTransaction() {
                        Toast.makeText(PurchaceServiceActicty.this,"Back pressed. Transaction cancelled",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                        Log.d("LOG", "Payment Transaction Failed " + inErrorMessage);
                        Toast.makeText(getBaseContext(), "Payment Transaction Failed ", Toast.LENGTH_LONG).show();
                    }

                });
    }

    private void sendResponse(Bundle inResponse) {
        String orderId = inResponse.getString("ORDERID");

        HashMap<String,String> transactionResponse = null;

        try {
            transactionResponse = new HashMap<String, String>();

            transactionResponse.put("token", token);
            transactionResponse.put("STATUS", inResponse.getString("STATUS"));
            transactionResponse.put("CHECKSUMHASH", inResponse.getString("CHECKSUMHASH"));
            transactionResponse.put("BANKNAME", inResponse.getString("BANKNAME"));
            transactionResponse.put("ORDERID", inResponse.getString("ORDERID"));
            transactionResponse.put("TXNAMOUNT", inResponse.getString("TXNAMOUNT"));
            transactionResponse.put("MID", inResponse.getString("MID"));
            transactionResponse.put("TXNID", inResponse.getString("TXNID"));
            transactionResponse.put("BANKTXNID", inResponse.getString("BANKTXNID"));


            callApi.transsactionResponseData(PurchaceServiceActicty.this, transactionResponse);

        } catch (NullPointerException ignored) {

        }catch (IndexOutOfBoundsException ignored){

        }



    }

    public void Response_transaction(PayTmModel body) {
        Toast.makeText(getApplicationContext(),"Payment Succes",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(PurchaceServiceActicty.this,ServicesDetails.class);
        intent.putExtra("parentId", id);
        intent.putExtra("name", name);
        intent.putExtra("price", TXN_AMOUNT);
        startActivity(intent);
        finishAffinity();
    }
}
