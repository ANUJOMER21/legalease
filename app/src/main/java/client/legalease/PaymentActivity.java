package client.legalease;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.util.HashMap;

import client.legalease.Model.CheckCoupanModel.CheckCoupanModel;
import client.legalease.Model.FinalPaymentModel.FinalPaymentModel;
import client.legalease.Model.LoginModel.LoginModel;
import client.legalease.Model.PaymentModel;
import client.legalease.Model.WalletModel;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.WebServices.ApiService;
import client.legalease.WebServices.CallApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {
    CommonSharedPreference commonSharedPreference;
    String token = "";
    String orderId = "";
    String id = "";
    String industryType = "";
    TextView tv_order,tv_company,tv_hint;
    ProgressBar progressBar,progressCoupan;
    int wallet = 0;
    int discount  =0;
    TextView tv_governmetnPrice;
    Button btn_pay;
    TextView tv_finalPrice;
    Button apply_coupan;
    EditText et_coupan;
    RelativeLayout relative_apply,relative_remove;
    ProgressBar progressCoupanremove;
    Button remove_coupan;
    ImageView iv_applied;
    TextView tv_promo;
    CheckBox cb_easyMoney;
    int walletPoint =0;
    int isWalletApplied=0;
    String productPrice =  "";
    TextView tv_wallet;



    String MID = "";
    String ORDER_ID= "";
    String CUST_ID ="";
    String INDUSTRY_TYPE_ID ="";
    String CHANNEL_ID ="";
    String checkSum ="";
    String WEBSITE = "";
    String MOBILE = "";
    String EMAIL = "";
    String CALLBACK_URL ="";
    String TXN_AMOUNT ="";
    ProgressDialog pDialog,pDialogConfirm;
    String walletMoney = "2";
    String coupon = "";
    String gov_fee = "";
    String professional_fee = "";



//    http://capanel.in/api/v1/getMainchecksum
String fprice ="";
    CallApi callApi = new CallApi();
    TextView tv_professionalPrice;


    String serviceid ="";
    String company = "";
    TextView tv_title;
    ImageView back_payment;
    int actualPrice =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initializeView();


        Typeface font2 = Typeface.createFromAsset(getApplicationContext().getAssets(),"AbrilFatface-Regular.otf");
        Typeface font1 = Typeface.createFromAsset(getApplicationContext().getAssets(),"Jipatha-Regular.ttf");

        commonSharedPreference = new CommonSharedPreference(getApplicationContext());

        try {
            if (commonSharedPreference.getLoginSharedPref(getApplicationContext()).getToken() != null) {
                token = commonSharedPreference.getLoginSharedPref(getApplicationContext()).getToken();
//                walletPoint = Integer.parseInt(commonSharedPreference.getLoginSharedPref(getApplicationContext()).getWallet());
            }
        }catch (NullPointerException ignored){

        }catch (NumberFormatException ignore){

        }

        getMyWallet();


        try {
            Intent intent  =getIntent();
            orderId = intent.getStringExtra("orderId");
            id = intent.getStringExtra("id");
            industryType = intent.getStringExtra("industryType");
            productPrice = intent.getStringExtra("price");
            gov_fee = intent.getStringExtra("gov_fee");
            professional_fee = intent.getStringExtra("professional_fee");
            company=intent.getStringExtra("company");
            serviceid =intent.getStringExtra("serviceid");
        }catch (NullPointerException ignored){

        }
        tv_finalPrice.setText(productPrice);
        tv_governmetnPrice.setText(gov_fee);
        tv_professionalPrice.setText(professional_fee);

        tv_order.setTypeface(font1);
        tv_company.setTypeface(font1);
        tv_hint.setTypeface(font1);
        tv_order.setText(orderId);
        tv_company.setText(industryType);
        tv_title.setTypeface(font1);

        back_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        apply_coupan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressCoupan.setVisibility(View.VISIBLE);

                veryfyCoupanCode();
            }
        });
        remove_coupan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualPrice = 0;
                disableCoupancode();


            }
        });


        cb_easyMoney.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                tv_finalPrice.setText("00");
                if (cb_easyMoney.isChecked()==true){
                    isWalletApplied=walletPoint;
                   int priceChecked = Integer.parseInt(tv_professionalPrice.getText().toString().trim());
                   tv_finalPrice.setText(String.valueOf((priceChecked-walletPoint-actualPrice)+Integer.parseInt(tv_governmetnPrice.getText().toString().trim())));
                    walletMoney="1";
                }else {
                    int priceChecked = Integer.parseInt(tv_professionalPrice.getText().toString().trim());
                    tv_finalPrice.setText(String.valueOf((priceChecked-actualPrice)+Integer.parseInt(tv_governmetnPrice.getText().toString().trim())));
                    isWalletApplied=0;
                    walletMoney="2";

                }

            }
        });
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fprice = tv_finalPrice.getText().toString().trim();
                progressBar.setVisibility(View.VISIBLE);
                getCheckSum(fprice);


            }
        });
    }

    private void getMyWallet() {

        ApiService api = UserMoreDetailActivity.RetroClient.getApiService();
        Call<WalletModel> call = api.getWalletData(token);
        call.enqueue(new Callback<WalletModel>() {
            @Override
            public void onResponse(Call<WalletModel> call, Response<WalletModel> response) {
                progressBar.setVisibility(View.GONE);
                try {
                  if (response.body().getWallet().equals("")){
                      walletPoint=0;
                      tv_wallet.setText("00/-");
                  }else {
                      walletPoint= Integer.parseInt(response.body().getWallet());
                      tv_wallet.setText(response.body().getWallet());
                  }
                }catch (NullPointerException ignored){

                }

            }

            @Override
            public void onFailure(Call<WalletModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);


            }
        });

    }

    private void disableCoupancode() {
            String coupon = "";
            String orderId = id;
            coupon = et_coupan.getText().toString().trim();
            if (coupon.equals("")){
                Toast.makeText(getApplicationContext(),"Please Enter Coupan Code",Toast.LENGTH_SHORT).show();
            }else {
                progressCoupanremove.setVisibility(View.VISIBLE);
                ApiService api = UserMoreDetailActivity.RetroClient.getApiService();
                Call<CheckCoupanModel> call = api.removeCoupon(token,coupon,orderId);
                call.enqueue(new Callback<CheckCoupanModel>() {
                    @Override
                    public void onResponse(Call<CheckCoupanModel> call, Response<CheckCoupanModel> response) {
                        progressCoupanremove.setVisibility(View.GONE);
                        String status = "";
                        String type = "";
                        String price = "";
                        try {
                            status  =response.body().getStatus();
                            if (!status.equals("success")) {
                                Toast.makeText(getApplicationContext(), "Oops something went wrong... please try after sometime", Toast.LENGTH_SHORT).show();
                            }else {

                                et_coupan.setEnabled(true);
                                relative_apply.setVisibility(View.VISIBLE);
                                relative_remove.setVisibility(View.GONE);
                                if (cb_easyMoney.isChecked()==true){
                                    price = String.valueOf((response.body().getPrice()-walletPoint)+Integer.parseInt(tv_governmetnPrice.getText().toString().trim()));

                                }else {
                                    if (isWalletApplied==0){
                                        price = String.valueOf(response.body().getPrice()+Integer.parseInt(tv_governmetnPrice.getText().toString().trim()));
                                    }else {
                                        price = String.valueOf((response.body().getPrice()-walletPoint)+Integer.parseInt(tv_governmetnPrice.getText().toString().trim()));

                                    }

                                }

                                tv_finalPrice.setText(price);
                                et_coupan.setEnabled(true);
                                tv_promo.setVisibility(View.GONE);
                                et_coupan.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black_hard));

                            }


                        }catch (NullPointerException ignored){

                        }

                    }

                    @Override
                    public void onFailure(Call<CheckCoupanModel> call, Throwable t) {
                        progressCoupanremove.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();

                    }
                });

            }
        }


        private void veryfyCoupanCode() {

        String orderId = id;
        coupon = et_coupan.getText().toString().trim();
        if (coupon.equals("")){
            Toast.makeText(getApplicationContext(),"Please Enter Coupan Code",Toast.LENGTH_SHORT).show();
        }else {
            ApiService api = UserMoreDetailActivity.RetroClient.getApiService();
            Call<CheckCoupanModel> call = api.getCoupanVerification(token,coupon,orderId);
            call.enqueue(new Callback<CheckCoupanModel>() {
                @Override
                public void onResponse(Call<CheckCoupanModel> call, Response<CheckCoupanModel> response) {
                    progressCoupan.setVisibility(View.GONE);
                    String status = "";
                    String type = "";
                    String price = "";
                    int mainPrice = 0;
                    int aftercoupanappliedprice = 0;
                    try {
                        status  =response.body().getStatus();
                        actualPrice = Integer.parseInt(response.body().getMainprice());
                        mainPrice = Integer.parseInt(response.body().getMainprice());
                        aftercoupanappliedprice = response.body().getPrice();
                        actualPrice= mainPrice-aftercoupanappliedprice;
                        if (!status.equals("success")) {
                            Toast.makeText(getApplicationContext(), "Invalid Coupan Code", Toast.LENGTH_SHORT).show();
                        }else {

                            relative_apply.setVisibility(View.GONE);
                            relative_remove.setVisibility(View.VISIBLE);
                            if (cb_easyMoney.isChecked()==true){
                                price = String.valueOf((response.body().getPrice()-walletPoint)+Integer.parseInt(tv_governmetnPrice.getText().toString().trim()));

                            }else {
                                if (isWalletApplied == 0) {
                                    price = String.valueOf(response.body().getPrice()+Integer.parseInt(tv_governmetnPrice.getText().toString().trim()));
                                } else {
                                    price = String.valueOf((response.body().getPrice() - walletPoint)+Integer.parseInt(tv_governmetnPrice.getText().toString().trim()));

                                }
                            }
                            tv_finalPrice.setText(price);
                            et_coupan.setEnabled(false);
                            et_coupan.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black_80));
                            tv_promo.setVisibility(View.VISIBLE);
                            tv_promo.setText("Promo code "+response.body().getData().get(0).getName()+" has been applied");
                        }


                    }catch (NullPointerException ignored){

                    }catch (NumberFormatException ignore){
                        Toast.makeText(getApplicationContext(),"Invalid coupan code",Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onFailure(Call<CheckCoupanModel> call, Throwable t) {
                    progressCoupan.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();

                }
            });

        }
    }


    private void getCheckSum(final String fprice) {
        String price =fprice;
            ApiService api = UserMoreDetailActivity.RetroClient.getApiService();
            Call<FinalPaymentModel> call = api.getCheckSumData(token,orderId,id,price);
            call.enqueue(new Callback<FinalPaymentModel>() {
                @Override
                public void onResponse(Call<FinalPaymentModel> call, Response<FinalPaymentModel> response) {
                    progressBar.setVisibility(View.GONE);
                    try {
                        MID = response.body().getData().getMID();
                        ORDER_ID = response.body().getData().getORDERID();
                        CUST_ID = String.valueOf(response.body().getData().getCUSTID());
                        INDUSTRY_TYPE_ID = response.body().getData().getINDUSTRYTYPEID();
                        CHANNEL_ID = response.body().getData().getCHANNELID();
                        WEBSITE = response.body().getData().getWEBSITE();
                        CALLBACK_URL = response.body().getData().getCALLBACKURL();
                        MOBILE = response.body().getData().getMOBILE();
                        checkSum = response.body().getCheckSum();
                        TXN_AMOUNT =fprice;


                        onStartTransaction(MID,ORDER_ID,CUST_ID,INDUSTRY_TYPE_ID,CHANNEL_ID,WEBSITE,CALLBACK_URL,MOBILE, checkSum,TXN_AMOUNT);

                    }catch (NullPointerException ignored){
                        progressBar.setVisibility(View.GONE);

                    }

                }

                @Override
                public void onFailure(Call<FinalPaymentModel> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();

                }
            });

        }




    public void onStartTransaction(String MID, final String ORDER_ID, final String CUST_ID, String INDUSTRY_TYPE_ID, String CHANNEL_ID, String WEBSITE, String CALLBACK_URL, String MOBILE, String checkSum, String TXN_AMOUNT) {

        final PaytmPGService Service = PaytmPGService.getProductionService();
        HashMap<String, String> paramMap = new HashMap<String, String>();

        // these are mandatory parameters

        paramMap.put("CALLBACK_URL", CALLBACK_URL);
        paramMap.put("CHANNEL_ID", CHANNEL_ID);
        paramMap.put("CHECKSUMHASH",checkSum);
        paramMap.put("CUST_ID",CUST_ID);
        paramMap.put("INDUSTRY_TYPE_ID",INDUSTRY_TYPE_ID);
        paramMap.put("MID",MID);
        paramMap.put("ORDER_ID",ORDER_ID);
        paramMap.put("TXN_AMOUNT",TXN_AMOUNT);
        paramMap.put("WEBSITE",WEBSITE);

        Log.d("Fds", String.valueOf(paramMap));

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

                        String STATUS = "";
                        String BANKTXNID = "";
                        STATUS = inResponse.getString("STATUS");
                        BANKTXNID = inResponse.getString("BANKTXNID");
                        Log.d("sf",STATUS);
                        Log.d("affsd",BANKTXNID);


//                        Log.d("LOG", "Payment Transaction is successful " + inResponse);

                        if (STATUS.equals("TXN_FAILURE")) {
                            progressBar.setVisibility(View.GONE);
                            UpdateOrder(STATUS, BANKTXNID);

                        }else {
                            progressBar.setVisibility(View.GONE);
                            confirmOrder();


                        }

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
                        Toast.makeText(PaymentActivity.this,"Transaction cancelled",Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                        Log.d("LOG", "Payment Transaction Failed " + inErrorMessage);
                        Toast.makeText(getBaseContext(), "Payment Transaction Failed ", Toast.LENGTH_LONG).show();

                    }

                });
    }

    private void confirmOrder() {
        progressBar .setVisibility(View.VISIBLE);
        String status = "success";
        String iswallet = walletMoney;
        String orderId =id;



        ApiService api = UserMoreDetailActivity.RetroClient.getApiService();

        Call<PaymentModel> call = api.uploadMyOrder(status,token,orderId,coupon,iswallet);

        call.enqueue(new Callback<PaymentModel>() {
                @Override
                public void onResponse(Call<PaymentModel> call, Response<PaymentModel> response) {
                    String a ="";

                    try {
                        a = response.body().getStatus();
                        if (a.equals("success")) {
                            progressBar.setVisibility(View.GONE);
                            Intent intent = new Intent(PaymentActivity.this, HomeActivity.class);
                            intent.putExtra("fragChange", "11");
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Your Order has been succesfully Placed...", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_SHORT).show();

                        }
                    }catch (NullPointerException ignored){

                    }
                }

                @Override
                public void onFailure(Call<PaymentModel> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                }
            });

        }


    private void UpdateOrder(String STATUS, String BANKTXNID) {
        pDialog = new ProgressDialog(PaymentActivity.this);
        String pleaseWait = getResources().getString(R.string.please_wait);

        pDialog.setMessage(pleaseWait);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        HashMap<String,String> updatePayment = new HashMap<>();
        updatePayment.put("STATUS",STATUS);
        updatePayment.put("BANKTXNID",BANKTXNID);
        updatePayment.put("token",token);
        updatePayment.put("id",id);
        updatePayment.put("coupon",coupon);
        updatePayment.put("walletMoney",walletMoney);



        updatePayment.put("CALLBACK_URL",CALLBACK_URL);
        updatePayment.put("CHANNEL_ID",CHANNEL_ID);
        updatePayment.put("CHECKSUMHASH",checkSum);
        updatePayment.put("CUST_ID",CUST_ID);
        updatePayment.put("INDUSTRY_TYPE_ID",INDUSTRY_TYPE_ID);
        updatePayment.put("MID",MID);
        updatePayment.put("ORDER_ID",ORDER_ID);
        updatePayment.put("TXN_AMOUNT",TXN_AMOUNT);
        updatePayment.put("WEBSITE",WEBSITE);


        try {
            callApi.updatePaytmOrder(PaymentActivity.this, updatePayment);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }




    private void initializeView() {
        tv_order = (TextView)findViewById(R.id.tv_order);
        tv_company = (TextView)findViewById(R.id.tv_company);
        tv_hint = (TextView)findViewById(R.id.tv_hint);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tv_governmetnPrice = (TextView)findViewById(R.id.tv_price);

        btn_pay  =(Button)findViewById(R.id.btn_pay);
        tv_finalPrice = (TextView)findViewById(R.id.tv_finalPrice);
        apply_coupan = (Button)findViewById(R.id.apply_coupan);
        et_coupan = (EditText)findViewById(R.id.et_coupan);
        progressCoupan = (ProgressBar) findViewById(R.id.progressCoupan);
        relative_remove = (RelativeLayout)findViewById(R.id.relative_remove);
        relative_apply = (RelativeLayout)findViewById(R.id.relative_apply);
        progressCoupanremove = (ProgressBar)findViewById(R.id.progressCoupanremove);
        remove_coupan  =(Button)findViewById(R.id.remove_coupan);
        tv_promo = (TextView)findViewById(R.id.tv_promo);
        cb_easyMoney =(CheckBox)findViewById(R.id.cb_easyMoney);
        tv_wallet = (TextView)findViewById(R.id.tv_wallet);
        tv_professionalPrice = (TextView)findViewById(R.id.tv_professionalPrice);
        tv_title = (TextView)findViewById(R.id.tv_title);
        back_payment = (ImageView)findViewById(R.id.back_payment);






    }




    public void Response_updatedOrder(LoginModel body) {
        String a = body.getStatus();
        Toast.makeText(getApplicationContext(),"Your order has been regenerated because you had cancelled your order" +
                " last time \n Please select your order again to purchase it...",Toast.LENGTH_SHORT).show();
        pDialog.dismiss();
        Intent intent = new Intent(PaymentActivity.this, HomeActivity.class);
        intent.putExtra("fragChange", "11");
        startActivity(intent);
    }

    public void clossDialog() {
        Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
        pDialog.dismiss();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(PaymentActivity.this,OpenActivityDetail.class);
//        intent.putExtra("orderId",orderId);
//        intent.putExtra("id",id);
//        intent.putExtra("industryType",industryType);
//        intent.putExtra("productPrice",productPrice);
//        intent.putExtra("gov_fee",gov_fee);
//        intent.putExtra("professional_fee",professional_fee);
//        intent.putExtra("company",company);
//        intent.putExtra("serviceid",serviceid);
//        startActivity(intent);
        finish();
    }
}
//
//     paramMap.put("PHONE",MOBILE);
//             paramMap.put("EMAIL", EMAIL);