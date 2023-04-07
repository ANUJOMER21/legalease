package client.legalease;

import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import client.legalease.Adapter.GetServiceRequestAdapter;
import client.legalease.Model.Servicerequestmodel.Datum2;
import client.legalease.Model.Servicerequestmodel.ServiceRequestModel;
import client.legalease.Model.customerRequestListModel.CustomerRequestListmodel;
import client.legalease.Model.customerRequestListModel.Datum;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.RetrofitClient.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Service_request extends AppCompatActivity {
ImageView back;
ProgressBar progressBar;
Button next,prev;
int page=1,lastpage=1;
TextView pageview;
Boolean isscrolling=false;
LinearLayout ll1;
List<Datum> datum2List;
RecyclerView rv_serviceRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_request);
         initview();
         ll1.setVisibility(View.GONE);
        LinearLayoutManager manager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        rv_serviceRequest.setLayoutManager(manager);
         datum2List=new ArrayList<>();
         back.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 onBackPressed();
             }
         });

         getallrequest(page);
         
         next.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(page<=lastpage&&lastpage!=1){

                     getallrequest(++page);
                     Log.d("next", "onClick: "+page+"last page "+lastpage);
                 }
             }
         });
         prev.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(page>1){
                     getallrequest(--page);
                     Log.d("prev", "onClick: "+page+"last page"+lastpage);
                 }
             }
         });

        rv_serviceRequest.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState==AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isscrolling=true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(lastpage!=1){
                    if(isscrolling&& lastpage>=page){
                        getallrequest(page++);

                    }
                }
            }
        });


    }

    private void getallrequest(int page) {
        CommonSharedPreference commonSharedPreference=new CommonSharedPreference(this);
        String token = "Bearer " + commonSharedPreference.getToken();
        Log.d("page", "getallrequest: "+page);
        int client_id=commonSharedPreference.getLoginSharedPref(getApplicationContext()).getId();

        final String p=String.valueOf(page);
       /** Call<ServiceRequestModel> call= RetrofitClient.getApiService().getServiceRequest(p,token,String.valueOf(client_id));
        call.enqueue(new Callback<ServiceRequestModel>() {
            @Override
            public void onResponse(Call<ServiceRequestModel> call, Response<ServiceRequestModel> response) {
             //   Log.d("response service", String.valueOf("onResponse: "+response.body().getOrderlist().getData().get(0).getAssoicate2()));
                if(response.body()==null){
                    Toast.makeText(Service_request.this, "null", Toast.LENGTH_SHORT).show();
                    Log.d("service_request", "onResponse: "+response);
                }
                else {
                    progressBar.setVisibility(View.GONE);
                    datum2List=response.body().getOrderlist().getData();
                    Log.d("datum2list", "onResponse: "+response.body().getOrderlist().getData().get(0).getAssoicate2().getName());
                    lastpage=response.body().getOrderlist().getLastPage();
                    String p=String.valueOf(response.body().getOrderlist().getCurrentPage());
                    pageview.setText(p);
                    LinearLayoutManager manager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
                    rv_serviceRequest.setLayoutManager(manager);
                    if(datum2List==null){

                    }
                    else
                    {        GetServiceRequestAdapter getServiceRequestAdapter=new GetServiceRequestAdapter(getApplicationContext(),datum2List);
                    rv_serviceRequest.setAdapter(getServiceRequestAdapter);
                    getServiceRequestAdapter.notifyDataSetChanged();}
                }
            }

            @Override
            public void onFailure(Call<ServiceRequestModel> call, Throwable t) {

            }
        });
**/
       Call<CustomerRequestListmodel> call =RetrofitClient.getApiService().customerreqli(p,token);
       call.enqueue(new Callback<CustomerRequestListmodel>() {
           @Override
           public void onResponse(Call<CustomerRequestListmodel> call, Response<CustomerRequestListmodel> response) {
               if(response.body()==null){
                   Toast.makeText(Service_request.this, "null", Toast.LENGTH_SHORT).show();
                   Log.d("service_request", "onResponse: "+response);
               }
               else {
                   progressBar.setVisibility(View.GONE);
                   List<Datum> data=new ArrayList<>();
                   for (Datum d: response.body().getRequestlist().getData()
                        ) {
                       if(d.getStatus()!=7||d.getStatus()!=8||d.getStatus()!=5){
                           datum2List.add(d);
                       }

                   }
               //  datum2List.addAll(response.body().getRequestlist().getData());
             //      Log.d("datum2list", "onResponse: "+response.body().getOrderlist().getData().get(0).getAssoicate2().getName());
                   lastpage=response.body().getRequestlist().getLastPage();

                   String p=String.valueOf(response.body().getRequestlist().getCurrentPage());
                   pageview.setText(p);


                   if(datum2List==null){

                   }
                   else
                   {        GetServiceRequestAdapter getServiceRequestAdapter=new GetServiceRequestAdapter(getApplicationContext(),datum2List);
                       rv_serviceRequest.setAdapter(getServiceRequestAdapter);
                       Log.d("last page", "onResponse: "+lastpage+"size "+datum2List.get(datum2List.size()-1).getId());
                       getServiceRequestAdapter.notifyDataSetChanged();}
               }
           }

           @Override
           public void onFailure(Call<CustomerRequestListmodel> call, Throwable t) {

           }
       });

    }

    private void initview() {
    back=findViewById(R.id.back);
    progressBar=findViewById(R.id.progressBar_service_rorder);
    next=findViewById(R.id.nextspage);
        prev=findViewById(R.id.previouspage);
        pageview=findViewById(R.id.pagenum2);
        rv_serviceRequest=findViewById(R.id.rv_service_offer);

ll1=findViewById(R.id.ll1);


    }
}