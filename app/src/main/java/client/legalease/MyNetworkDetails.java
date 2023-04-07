package client.legalease;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import client.legalease.Adapter.NetworkAdapter;
import client.legalease.Adapter.NetworkDetailsAdapter;
import client.legalease.Model.NetrorkDetailsModel.NetworkDetailsData;
import client.legalease.Model.NetrorkDetailsModel.NetworkDetailsModel;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.WebServices.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyNetworkDetails extends AppCompatActivity {

    String userId = "";
    RecyclerView rv_networkDetails;
    ProgressBar progressBar;
    TextView tv_hint;
    List<NetworkDetailsData> networkDetailsData=new ArrayList<>();
    CommonSharedPreference commonSharedPreference;
    String token ="";
    NetworkDetailsAdapter networkDetailsAdapter;
    TextView tv_title;
    ImageView back_payment;
    String name = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_network_details);

        commonSharedPreference = new CommonSharedPreference(getApplicationContext());

        try {

            if (commonSharedPreference.loggedin()) {
                token = commonSharedPreference.getLoginSharedPref(getApplicationContext()).getToken();

            } else {
                token = "";

            }
        }catch (NullPointerException ignored){
        }


        initializeViews();
        try {
            Intent intent = getIntent();
            userId = intent.getStringExtra("userId");
            name = intent.getStringExtra("name");
            tv_title.setText(name);
        }catch (NullPointerException ignored){

        }

        back_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getData();
    }

    private void initializeViews() {
        rv_networkDetails = (RecyclerView)findViewById(R.id.rv_networkDetails);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        tv_hint  =(TextView)findViewById(R.id.tv_hint);
        tv_title=(TextView)findViewById(R.id.tv_title);
        back_payment = (ImageView)findViewById(R.id.back_payment);
    }


    private void getData() {

        ApiService api = UserMoreDetailActivity.RetroClient.getApiService();
        Call<NetworkDetailsModel> call = api.getnetworkDetails(token,userId);
        call.enqueue(new Callback<NetworkDetailsModel>() {
            @Override
            public void onResponse(Call<NetworkDetailsModel> call, Response<NetworkDetailsModel> response) {
                progressBar .setVisibility(View.GONE);
                try {
                    networkDetailsData = response.body().getData();
                    if (networkDetailsData.size()==0){
                        tv_hint.setVisibility(View.VISIBLE);
                        rv_networkDetails.setVisibility(View.GONE);
                    }else {

                        tv_hint.setVisibility(View.GONE);
                        rv_networkDetails.setVisibility(View.VISIBLE);

                    }
                }catch (NullPointerException ignored){

                }catch (IndexOutOfBoundsException ignore){

                }


                networkDetailsAdapter = new NetworkDetailsAdapter(getApplicationContext(),networkDetailsData);
                RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL, false);
                rv_networkDetails.setLayoutManager(eLayoutManager);
//                recyclerView.setItemAnimator(new DefaultItemAnimator());
                rv_networkDetails.setAdapter(networkDetailsAdapter);

            }

            @Override
            public void onFailure(Call<NetworkDetailsModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();

            }
        });
    }

}
