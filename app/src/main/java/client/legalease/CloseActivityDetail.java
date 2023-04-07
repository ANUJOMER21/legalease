package client.legalease;

import android.content.Intent;
import android.graphics.Typeface;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

import client.legalease.Model.InvoiceDetails.InvoiceData;
import client.legalease.Model.OrderModel.OrderData;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.WebServices.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CloseActivityDetail extends AppCompatActivity {
    OrderData orderData = null;
    String company = "";
    String orderId = "";
    TextView tv_order,tv_company,tv_hint;
    CommonSharedPreference commonSharedPreference;
    String token="";
    ProgressBar progressBar;
    String order = "";
    String actualPrice = "";
    String walletPrice ="";
    String finalPrice = "";
    TextView tv_finalPrice,tv_walletPrice,tv_actualPrice;
    TextView tv_aPrice,tv_wPrice,tv_fPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close_detail);
        Typeface font2 = Typeface.createFromAsset(getApplicationContext().getAssets(),"Jipatha-Regular.ttf");
        initializeView();


        try {
            Intent intent  =getIntent();

            company = intent.getStringExtra("company");
            orderId = intent.getStringExtra("orderId");
            order = intent.getStringExtra("order");

        }catch (Exception ignored){
            Log.d("fsd", String.valueOf(ignored));

        }
        commonSharedPreference = new CommonSharedPreference(getApplicationContext());

        try {

            if (commonSharedPreference.loggedin()) {
                token = commonSharedPreference.getLoginSharedPref(getApplicationContext()).getToken();

            } else {
                token = "";

            }
        }catch (NullPointerException ignored){

        }


        tv_order.setTypeface(font2);
        tv_company.setTypeface(font2);
        tv_hint.setTypeface(font2);
//        tv_aPrice.setTypeface(font2);
//        tv_wPrice.setTypeface(font2);
//        tv_finalPrice.setTypeface(font2);
//        tv_walletPrice.setTypeface(font2);
//        tv_actualPrice.setTypeface(font2);

//        tv_order.setText(order);
//        tv_company.setText(company);
        getInvoiceData();

    }



    private void getInvoiceData() {
        ApiService api = UserMoreDetailActivity.RetroClient.getApiService();
        Call<InvoiceData> call = api.myInvoice(token,orderId);
        call.enqueue(new Callback<InvoiceData>() {
            @Override
            public void onResponse(Call<InvoiceData> call, Response<InvoiceData> response) {
                progressBar.setVisibility(View.GONE);
                int fPrice = 0;
                int wPrice = 0;
                String fAmount = "";
                try {
                    tv_order.setText(response.body().getData().get(0).getOrderId());
                    tv_company.setText(response.body().getData().get(0).getService().getTitle());
                    finalPrice=response.body().getData().get(0).getAmount();
                    walletPrice = response.body().getData().get(0).getOffer().getWalletAmt();
                    tv_actualPrice.setText("  "+finalPrice);
                    tv_walletPrice.setText("- "+walletPrice);
                    fPrice= Integer.parseInt(finalPrice);
                    wPrice = Integer.parseInt(walletPrice);
                    fAmount = String.valueOf(fPrice-wPrice);
                    tv_finalPrice.setText("  "+fAmount);

                }catch (NullPointerException ignored){

                }catch (IndexOutOfBoundsException ignor){

                }



            }

            @Override
            public void onFailure(Call<InvoiceData> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void initializeView() {
        tv_order = (TextView)findViewById(R.id.tv_order);
        tv_company = (TextView)findViewById(R.id.tv_company);
        tv_hint = (TextView)findViewById(R.id.tv_hint);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        tv_actualPrice = (TextView)findViewById(R.id.tv_actualPrice);
        tv_walletPrice = (TextView)findViewById(R.id.tv_walletPrice);
        tv_finalPrice = (TextView)findViewById(R.id.tv_finalPrice);
//        tv_aPrice = (TextView)findViewById(R.id.tv_aPrice);
//        tv_wPrice = (TextView)findViewById(R.id.tv_wPrice);





    }

}
