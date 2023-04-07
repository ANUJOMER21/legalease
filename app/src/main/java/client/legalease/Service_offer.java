package client.legalease;

import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import client.legalease.Adapter.ServiceOfferAdapter;
import client.legalease.Adapter.ServiceOfferFragmentAdapter;
import client.legalease.Model.ServiceOfferModel.Datum3;
import client.legalease.Model.ServiceOfferModel.ServiceOffermodel;
import client.legalease.Model.Servicerequestmodel.ServiceRequestModel;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.RetrofitClient.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Service_offer extends AppCompatActivity {
ImageView back;
/**Button prev,next;
ProgressBar progressBar;
List<Datum3> datum3List;
RecyclerView rv_Service_offer;
;**/
TabLayout tabLayout;
ViewPager viewPager;
ServiceOfferFragmentAdapter serviceOfferFragmentAdapter;
int page=1;
int lastpage=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_offer);
    initview();
    back.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    });
        serviceOfferFragmentAdapter=new ServiceOfferFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(serviceOfferFragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
 /**   getservicedata(1);
   prev.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           if(page>1){
               getservicedata(--page);
           }
       }
   });

   next.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           if(page<lastpage&&lastpage!=1)
           {
           getservicedata(++page);
           }
       }
   });**/

    }

    @Override
    protected void onResume() {
        super.onResume();

        serviceOfferFragmentAdapter=new ServiceOfferFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(serviceOfferFragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**  private void getservicedata(int i) {
        CommonSharedPreference commonSharedPreference=new CommonSharedPreference(this);
        String token = "Bearer " + commonSharedPreference.getToken();

        int client_id=commonSharedPreference.getLoginSharedPref(getApplicationContext()).getId();
        Log.d("client_id", "getallrequest: "+client_id);
        final String p=String.valueOf(i);
        Call<ServiceOffermodel> call= RetrofitClient.getApiService().getServiceOffermodel(p,token);
        call.enqueue(new Callback<ServiceOffermodel>() {
            @Override
            public void onResponse(Call<ServiceOffermodel> call, Response<ServiceOffermodel> response) {
                if(response.body()==null){
                    Toast.makeText(Service_offer.this, "null", Toast.LENGTH_SHORT).show();
                }
                else {
                    lastpage=response.body().getOfferList().getLastPage();
                    progressBar.setVisibility(View.GONE);
                    datum3List=response.body().getOfferList().getData();
                    LinearLayoutManager manager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
                    rv_Service_offer.setLayoutManager(manager);
                    ServiceOfferAdapter serviceOfferAdapter=new ServiceOfferAdapter(getApplicationContext(),datum3List);
                    rv_Service_offer.setAdapter(serviceOfferAdapter);
                    serviceOfferAdapter.notifyDataSetChanged();



                }
            }

            @Override
            public void onFailure(Call<ServiceOffermodel> call, Throwable t) {

            }
        });


    }**/

    private void initview() {
        tabLayout=findViewById(R.id.tablayoutaso);
    back=findViewById(R.id.back);
    viewPager=findViewById(R.id.viewpager2);
 /**   prev=findViewById(R.id.previouspage);
    next=findViewById(R.id.nextspage);
    progressBar=findViewById(R.id.progressBar_service_rorder);
    rv_Service_offer=findViewById(R.id.rv_service_offer);
    datum3List=new ArrayList<>();**/
    }
}