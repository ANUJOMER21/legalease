package client.legalease;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.HashMap;

import client.legalease.Model.Acceptedordermodel.Datum;
import client.legalease.Model.Bookmeetmodel;
import client.legalease.Model.OrderModel.*;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.RetrofitClient.RetrofitClient;
import retrofit2.*;

public class OpenActivityDetail extends AppCompatActivity {
    OrderData orderData = null;
    Datum  datum=null;
    String company = "";
    String orderId = "";
    String price = "";
    String serviceid = "";
    TextView tv_order,tv_company,tv_hint;
    Button button_status,button_uploadDocument,btn_submitorder,btn_download_document;
    String id = "";
    String industryType = "";
    String gov_fee="";
    String professional_fee ="";
    ImageView back;
            String dat;
    Button approvedorder,rejectorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_detail);
        initializeView();
        Typeface font2 = Typeface.createFromAsset(getApplicationContext().getAssets(),"AbrilFatface-Regular.otf");
        Typeface font1 = Typeface.createFromAsset(getApplicationContext().getAssets(),"Jipatha-Regular.ttf");


        try {
            Intent intent  =getIntent();

            company = intent.getStringExtra("company");
             dat=intent.getStringExtra("data");
            Gson gson=new Gson();
            datum=gson.fromJson(dat,Datum.class);
            orderId = intent.getStringExtra("orderId");
            serviceid = intent.getStringExtra("serviceid");
            price = intent.getStringExtra("price");
            id = intent.getStringExtra("id");
            industryType = intent.getStringExtra("industryType");
            gov_fee = intent.getStringExtra("gov_fee");
            professional_fee = intent.getStringExtra("professional_fee");

        }catch (Exception ignored){
            Log.d("fsd", String.valueOf(ignored));

        }

        tv_order.setTypeface(font1);
        tv_company.setTypeface(font1);
        tv_hint.setTypeface(font1);
        tv_order.setText(orderId);
        tv_company.setText(company);
btn_download_document.setTypeface(font2);
        button_status.setTypeface(font2);
        button_uploadDocument.setTypeface(font2);
        btn_submitorder.setTypeface(font2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

     /**   btn_makePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

          try {

              Intent intent  = new Intent(OpenActivityDetail.this,PaymentActivity.class);
              intent.putExtra("id",id);
              intent.putExtra("orderId",orderId);
              intent.putExtra("industryType",industryType);
              intent.putExtra("price",price);
              intent.putExtra("serviceid",serviceid);
              intent.putExtra("gov_fee", gov_fee);
              intent.putExtra("professional_fee", professional_fee);
              intent.putExtra("serviceid", serviceid);
              intent.putExtra("company", company);



              startActivity(intent);
              finish();
          }catch (NullPointerException ignored){

          }

            }
        });
**/

     if(datum.getStatus().equals("8")){
         btn_submitorder.setVisibility(View.GONE);
         button_uploadDocument.setVisibility(View.GONE);
     }
     else if(datum.getStatus().equals("7")) {
         btn_submitorder.setVisibility(View.VISIBLE);
     }
     else {
         btn_submitorder.setVisibility(View.GONE);
     }


        Dialog dialog = new Dialog(OpenActivityDetail.this);
     btn_submitorder.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
           dialog.setContentView(R.layout.openactivitydetaildialog);
             dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
             dialog.setCancelable(true);
             dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
         approvedorder=dialog.findViewById(R.id.acceptbtn);
         rejectorder=dialog.findViewById(R.id.rejectbtn);
         approvedorder.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                // Toast.makeText(OpenActivityDetail.this, "Order Aprroved", Toast.LENGTH_SHORT).show();
                 dialog.dismiss();
                 changeorderstatus(8);
             }
         });
         rejectorder.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
           //      Toast.makeText(OpenActivityDetail.this, "Order Rejected", Toast.LENGTH_SHORT).show();
                 dialog.dismiss();
                // changeorderstatus(3);
             }
         });
         dialog.show();
         }
     });
        button_uploadDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    Intent intent  = new Intent(OpenActivityDetail.this,UploadDocument2.class);
                    intent.putExtra("id",id);
                    intent.putExtra("orderId",orderId);
                    intent.putExtra("industryType",industryType);
                    intent.putExtra("price",price);
                    intent.putExtra("serviceid",serviceid);
                    intent.putExtra("gov_fee", gov_fee);
                    intent.putExtra("professional_fee", professional_fee);
                    intent.putExtra("serviceid", serviceid);
                    intent.putExtra("company", company);

                    startActivity(intent);
                }catch (NullPointerException ignored){

                }
            }
        });



        btn_download_document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent i=new Intent(getApplicationContext(),Download_Uploaded_Document.class);
            i.putExtra("orderid",orderId);
            startActivity(i);
            }
        });
        button_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent i=new Intent(OpenActivityDetail.this,OrderDetailpage.class);

                i.putExtra("data",dat);
                startActivity(i);
            }
        });
    }

    private void changeorderstatus(int i) {
        CommonSharedPreference commonSharedPreference=new CommonSharedPreference(OpenActivityDetail.this);
        String token="Bearer "+commonSharedPreference.getToken();
        HashMap<String ,String> data=new HashMap<>();
        data.put("order_id",orderId);
        data.put("approval_status",String.valueOf(i));
        Call<Bookmeetmodel> call= RetrofitClient.getApiService().orderapproval(token,data);
        call.enqueue(new Callback<Bookmeetmodel>() {
            @Override
            public void onResponse(Call<Bookmeetmodel> call, Response<Bookmeetmodel> response) {
                if(response.body().getStatus().equals("success")){

                        Toast.makeText(OpenActivityDetail.this,"Order is Completed", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(OpenActivityDetail.this, "Failed to update Order", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Bookmeetmodel> call, Throwable t) {

            }
        });
    }

    private void initializeView() {
        tv_order = (TextView)findViewById(R.id.tv_order);
        tv_company = (TextView)findViewById(R.id.tv_company);
        tv_hint = (TextView)findViewById(R.id.tv_hint);
        button_status = (Button)findViewById(R.id.button_status);
        button_uploadDocument = (Button)findViewById(R.id.button_uploadDocument);
        btn_submitorder = (Button)findViewById(R.id.btn_submitorder);
        back=(ImageView)findViewById(R.id.back);
        btn_download_document=findViewById(R.id.btn_download_document);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    finish();
    }
}

