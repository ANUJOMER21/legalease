package client.legalease;

import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import client.legalease.Adapter.SubServicesAdapter;
import client.legalease.Model.Services.SubServices.SubServiceModel;
import client.legalease.Model.Services.SubServices.Subservice;
import client.legalease.WebServices.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubServicesActivity extends AppCompatActivity {
    ImageView back;
    TextView serviceName;
    String parentId = "";
    String serviceTitle = "";
    RecyclerView rv_subServices;
    ProgressBar progressBar;
    List<Subservice> subservices;
    SubServicesAdapter subServicesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_services);
        initializeView();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
//                Intent  intent = new Intent(SubServicesActivity.this,HomeActivity.class);
//                startActivity(intent);
            }
        });
        try {
            Intent intent = getIntent();
            parentId = intent.getStringExtra("parentId");
            serviceTitle = intent.getStringExtra("serviceTitle");
            serviceName.setText(serviceTitle);

        }catch (NullPointerException ignored){

        }catch (Exception ignore){

        }

        getSubServiceData();

    }



    private void getSubServiceData() {

        ApiService api = UserMoreDetailActivity.RetroClient.getApiService();
        Call<SubServiceModel> call = api.getSubSevicesData(parentId);
        call.enqueue(new Callback<SubServiceModel>() {
            @Override
            public void onResponse(Call<SubServiceModel> call, Response<SubServiceModel> response) {
                progressBar .setVisibility(View.GONE);
                String status = "";
                try {
                    subservices = response.body().getSubservice();
                }catch (NullPointerException ignored){

                }catch (IndexOutOfBoundsException ignore){

                }
                Log.d("dsfsdb", String.valueOf(subservices));


                subServicesAdapter = new SubServicesAdapter(SubServicesActivity.this, subservices);

                LinearLayoutManager eLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL, false);
                rv_subServices.setLayoutManager(eLayoutManager);

//                recyclerView.setItemAnimator(new DefaultItemAnimator());
                rv_subServices.setAdapter(subServicesAdapter);

            }

            @Override
            public void onFailure(Call<SubServiceModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void initializeView() {
        back = (ImageView)findViewById(R.id.back);
        serviceName = (TextView)findViewById(R.id.serviceTitle);
        rv_subServices = (RecyclerView)findViewById(R.id.rv_subServices);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

    }
}
