package client.legalease;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import client.legalease.Adapter.serviceviewAdapter;
import client.legalease.Model.ClientOrderModel;
import client.legalease.Model.OrderPAYReqModel;
import client.legalease.Model.PaymentCredentialModel.Paymentcredentialmodel;
import client.legalease.Model.ServiceOfferModel.Datum3;
import client.legalease.Model.ServiceOfferModel.OrderService;
import client.legalease.Model.VERIFYOTP.User;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.RetrofitClient.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceOfferDetail extends AppCompatActivity implements PaymentResultWithDataListener {
TextView name,email,amount,payment_status,order_due_date,mobile,address;
Button Accept,Reject;
RecyclerView Servicerv;
ImageView back,call,map;

LinearLayout orderacceptreject;
CommonSharedPreference commonSharedPreference;
Datum3 datum3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_offer_detail);
        Checkout.preload(getApplicationContext());
        initView();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Gson gson=new Gson();
        String data=getIntent().getStringExtra("data");
      datum3=gson.fromJson(data,Datum3.class);
        try {
            setData(datum3);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        if(datum3.getStatus().equals("1")||datum3.getStatus().equals("4")||datum3.getStatus().equals("5")){
    orderacceptreject.setVisibility(View.VISIBLE);
}
        final Integer orderid = datum3.getId();
        Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //
                  orderaccept("3",orderid);
              //    sendatatodb(datum3);
               /** User user=commonSharedPreference.getLoginSharedPref(ServiceOfferDetail.this);
                String token= "Bearer "+user.getToken();
            Call<Paymentcredentialmodel> call=RetrofitClient.getApiService().getpaymentcredential(token);
            call.enqueue(new Callback<Paymentcredentialmodel>() {
                @Override
                public void onResponse(Call<Paymentcredentialmodel> call, Response<Paymentcredentialmodel> response) {
                    if(response.body().getStatus().equals("success")){


                     //   Payment(response.body().getPaymentcredentials().get(0).getKey());

                        Toast.makeText(ServiceOfferDetail.this, "Payment Success", Toast.LENGTH_SHORT).show();

                        Log.d("key", "onResponse:"+response.body().getPaymentcredentials().get(0).getKey());
                    }
                }

                @Override
                public void onFailure(Call<Paymentcredentialmodel> call, Throwable t) {

                }
            });**/
            }
        });

        Reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderaccept("2",orderid);
            }
        });
    }

    private void sendatatodb(Datum3 datum3) {
        String token = "Bearer" + commonSharedPreference.getToken();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("asspocate_id", String.valueOf(datum3.getAssoicate().getId()));
        hashMap.put("order_id", String.valueOf(datum3.getId()));
        hashMap.put("payment_status", "success");
        hashMap.put("payment_method", "upi");
        hashMap.put("razorpay_payment_id", "RZA1123456");


        hashMap.put("merchant_order_id", datum3.getMerchantOrderId());

        hashMap.put("card_holder_name_id", "test 1");
        hashMap.put("currency_code_id", "40");
        Log.d("token", "sendatatodb: " + datum3.getAssoicate().getName());
        Call<OrderPAYReqModel> orderPAYReqModelCall = RetrofitClient.getApiService().orderpaymentrequest(token, hashMap);
        orderPAYReqModelCall.enqueue(new Callback<OrderPAYReqModel>() {
            @Override
            public void onResponse(Call<OrderPAYReqModel> call, Response<OrderPAYReqModel> response) {
              if(response.body().getStatus().equals("success")){
                  Toast.makeText(ServiceOfferDetail.this, "Payment Done", Toast.LENGTH_SHORT).show();
              }
                Log.d("response", "onResponse: "+response.body().getMessage());
            }

            @Override
            public void onFailure(Call<OrderPAYReqModel> call, Throwable t) {
                Log.d("orderPayRequest", "onResponse: " + t);
            }
        });
    }


    private void Payment(String key) {
        String samount=amount.getText().toString();
        if(!samount.isEmpty()&&!key.isEmpty()){
            int amount = Math.round(Float.parseFloat(samount) * 100);
User user= commonSharedPreference.getLoginSharedPref(ServiceOfferDetail.this);

            Checkout checkout=new Checkout();
            checkout.setKeyID(key);
            JSONObject object=new JSONObject();
            try {
           /**     object.put("name",user.getName());
                object.put("description", "Test payment");
                object.put("currency", "INR");
                object.put("key",key);
                object.put("send_sms_hash",true);

             //   object.put("order_id","**razorpay_order_id**");

                object.put("amount", amount);
                object.put("prefill.contact", user.getMobile());
                object.put("prefill.email", user.getEmail());
                JSONObject retryObj = new JSONObject();
                retryObj.put("enabled", true);
                retryObj.put("max_count", 4);
                object.put("retry", retryObj);
                checkout.open(ServiceOfferDetail.this,object);**/
                JSONObject options = new JSONObject();
                options.put("name", "Razorpay Corp");
                options.put("description", "Demoing Charges");
                options.put("send_sms_hash",true);
                options.put("allow_rotation", true);
                //You can omit the image option to fetch the image from dashboard
                options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
                options.put("currency", "INR");
                options.put("amount", "100");
                options.put("reference_id","#aasasw8");
                JSONObject method = new JSONObject();
                method.put("netbanking",1);
                method.put("card",1);
                method.put("upi",1);
                method.put("wallet",1);
                JSONObject checkou = new JSONObject();
                checkou.put("method",method);
                options.put("checkout",checkout);
                JSONObject preFill = new JSONObject();
                preFill.put("email", "test@razorpay.com");
                preFill.put("contact", "9876543210");

                options.put("prefill", preFill);
checkout.open(ServiceOfferDetail.this,options);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void orderaccept(String accept, Integer orderid) {
        orderacceptreject.setVisibility(View.INVISIBLE);
        CommonSharedPreference commonSharedPreference=new CommonSharedPreference(this);
        String token="Bearer "+commonSharedPreference.getToken();
        HashMap<String ,String > hashMap=new HashMap<>();
        hashMap.put("order_id",String.valueOf(orderid));
        hashMap.put("approval_status",accept);
        Call<ClientOrderModel> clientOrderModelCall= RetrofitClient.getApiService().orderacceptreject(token,hashMap);
        clientOrderModelCall.enqueue(new Callback<ClientOrderModel>() {
            @Override
            public void onResponse(Call<ClientOrderModel> call, Response<ClientOrderModel> response) {
                Log.d("orderacceptreject", "onResponse: "+response);
               if(response.body().getStatus().equals("success")) {
                   Toast.makeText(ServiceOfferDetail.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                       showAlert(ServiceOfferDetail.this,"Order is Accepted By You. It Shows on My Orders ");

               }
            }

            @Override
            public void onFailure(Call<ClientOrderModel> call, Throwable t) {

            }
        });
    }
    public static void showAlert(Activity activity, String message) {
        TextView title = new TextView(activity);

        title.setText("Success ");
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCustomTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                activity.finish();
            }
        });

        AlertDialog alert = builder.show();
        TextView messageText = (TextView)alert.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);
        messageText.setTextColor(Color.RED);
    }
    private void setData(Datum3 datum3) throws ParseException {
        name.setText(datum3.getAssoicate().getName());
        email.setText(datum3.getAssoicate().getCompanyName());

       // number.setText(datum3.getAssoicate().getMobile());
        String sta=new Status().getStatus(Integer.valueOf(datum3.getStatus()));
        payment_status.setText( sta);
        String amt=String.valueOf(datum3.getAmount());
     /**   if(datum3.getStatus().equals("pending")){
            orderacceptreject.setVisibility(View.VISIBLE);
        }
        else {
            orderacceptreject.setVisibility(View.GONE);
        }**/
amount.setText(amt);
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = dt.parse(datum3.getOrderCompleteAt());
        SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");
        order_due_date.setText(dt1.format(date));
mobile.setText(datum3.getAssoicate().getMobile());
if(datum3.getAssoicate().getAddress()!=null) {
    address.setText((CharSequence) datum3.getAssoicate().getAddress());
}
else {
    address.setText("");
}
     call.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             String phone_num=mobile.getText().toString();
             Intent intent1=new Intent(Intent.ACTION_DIAL);
             intent1.setData(Uri.parse("tel:"+phone_num));

             startActivity(intent1);
         }
     });

map.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String map = "http://maps.google.co.in/maps?q=" + address.getText().toString();
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
        startActivity(i);
    }
});
        List<OrderService> orderServiceList=new ArrayList<>();
        LinearLayoutManager manager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        Servicerv.setLayoutManager(manager);
        serviceviewAdapter serviceviewAdapter=new serviceviewAdapter(datum3.getOrderServiceList(),getApplicationContext());
        Log.d("data", "setData: "+datum3.getOrderServiceList().size());
        Servicerv.setAdapter(serviceviewAdapter);
        serviceviewAdapter.notifyDataSetChanged();
    }

    private void initView() {
        name=findViewById(R.id.partner_name);
        email=findViewById(R.id.partner_email);
      //  number=findViewById(R.id.partner_number);
        amount=findViewById(R.id.totalamt);
        call=findViewById(R.id.call);
        map=findViewById(R.id.map);
        back=findViewById(R.id.back);
        Accept=findViewById(R.id.acceptButton);
        Reject=findViewById(R.id.rejectButton);
        mobile=findViewById(R.id.partner_mob);
        address=findViewById(R.id.partner_address);
        Servicerv=findViewById(R.id.rv_servicelist);
        payment_status=findViewById(R.id.partner_payment);
orderacceptreject=findViewById(R.id.ll1);
commonSharedPreference=new CommonSharedPreference(this);
order_due_date=findViewById(R.id.partner_date);


    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        Toast.makeText(this, "Payment is successful : " + s, Toast.LENGTH_SHORT).show();
        sendatatodb(datum3);
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Toast.makeText(this, "Payment Failed due to error : " + s, Toast.LENGTH_SHORT).show();
        Log.d("paymenterror", "onPaymentError: "+s);

    }
}