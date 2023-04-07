package client.legalease;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import client.legalease.Adapter.DownloaddocumentAdapter;
import client.legalease.Model.Downloaddocumentmodel;
import client.legalease.Model.Uploadeddocmodel;
import client.legalease.Model.uploaddocumentlistModel.Datum;
import client.legalease.Model.uploadedDocuments.Datum3;
import client.legalease.Model.uploadedDocuments.Uploaddocuments;
import retrofit2.Call;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import client.legalease.Model.uploaddocumentlistModel.Uploaddocumentlist;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.WebServices.ApiService;
import retrofit2.Callback;
import retrofit2.Response;

public class Download_Uploaded_Document extends AppCompatActivity {
ImageView back;
    URL url;
TextView orderid;
RecyclerView rv;
    ProgressDialog mProgressDialog;
    HashMap<Integer,String> documentidname;
ProgressBar pv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_uploaded_document);
        initview();

        String orderId=getIntent().getStringExtra("orderid");
        orderid.setText("Order No. :-" +orderId);
        CommonSharedPreference commonSharedPreference=new CommonSharedPreference(Download_Uploaded_Document.this);
        String token="Bearer "+commonSharedPreference.getToken();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ApiService apiService=UserMoreDetailActivity.RetroClient.getApiService();
        Call<Uploaddocumentlist>call =apiService.uploaddocumentlist(token);
        call.enqueue(new Callback<Uploaddocumentlist>() {
            @Override
            public void onResponse(Call<Uploaddocumentlist> call, Response<Uploaddocumentlist> response) {
                if(response.body().getStatus().equals("success")){
                    if(response.body().getData().size()!=0){
                        for(Datum datum:response.body().getData()){
                            documentidname.put(datum.getId(),datum.getRequireDoc());
                        }
                        getuploadeddoc(documentidname,token,orderId);
                    }
                }
            }

            @Override
            public void onFailure(Call<Uploaddocumentlist> call, Throwable t) {

            }
        });
    }


    private void getuploadeddoc(HashMap<Integer, String> documentidname, String token, String orderId) {
    if(documentidname.size()!=0){
        ApiService apiService=UserMoreDetailActivity.RetroClient.getApiService();
        Call<Uploaddocuments> uploaddocumentsCall=apiService.geuptdoc(token,orderId);
        uploaddocumentsCall.enqueue(new Callback<Uploaddocuments>() {
            @Override
            public void onResponse(Call<Uploaddocuments> call, Response<Uploaddocuments> response) {
               if(response.body().getStatus().equals("success")){
                   pv.setVisibility(View.GONE);
                   ArrayList<Downloaddocumentmodel> downloaddocumentmodelArrayList=new ArrayList<>();
                   List<Datum3> datum3s=new ArrayList<>();
                   datum3s=response.body().getData();
                   for (Datum3 data:datum3s
                   ) {
                       String nameofdoc="";
                       if(documentidname.containsKey(data.getRequireDocId())){
                           nameofdoc=documentidname.get(data.getRequireDocId());
                         downloaddocumentmodelArrayList.add(new Downloaddocumentmodel(nameofdoc,data.getUploadedDoc(),data.getUploadedType()));


                       }
                       Log.d(data.getId().toString(), "onResponse: "+data.getUploadedDoc());

                   }
           if(downloaddocumentmodelArrayList.size()!=0){
               RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
               rv.setLayoutManager(eLayoutManager);
               DownloaddocumentAdapter downloaddocumentAdapter=new DownloaddocumentAdapter(downloaddocumentmodelArrayList,Download_Uploaded_Document.this);
               rv.setAdapter(downloaddocumentAdapter);

           }

               }
            }

            @Override
            public void onFailure(Call<Uploaddocuments> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();
            }
        });
    }
    }

    private void initview() {
    back=findViewById(R.id.back);
    orderid=findViewById(R.id.order_id1);
    pv=findViewById(R.id.progressBar_viewall);
    rv=findViewById(R.id.rv_downloaddocument);
    documentidname=new HashMap<>();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        {

        }

    }

}
