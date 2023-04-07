package client.legalease;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import client.legalease.Adapter.AccountantDetailsAdapter;
import client.legalease.Model.AccountModel.Account;
import client.legalease.Model.AccountantDetails.AccountantData;
import client.legalease.Model.AccountantDetails.AccountantDetail;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.WebServices.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountantDetails extends AppCompatActivity {
CommonSharedPreference commonSharedPreference;
String name = "";
String accountantid = "";
String token = "";

    TextView clientName,tv_cancel;
    ImageView back;
    RecyclerView rv_clientActivity;
    ProgressBar progressBar;
    AccountantDetailsAdapter accountantDetailsAdapter;
    List<AccountantData> accountantDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountant_details);

        accountantDataList=new ArrayList<>();

        try {
            Intent intent =getIntent();
            name = intent.getStringExtra("name");
            accountantid = intent.getStringExtra("accountantId");
        }catch (NullPointerException ignored){

        }

        commonSharedPreference = new CommonSharedPreference(getApplicationContext());

        try {
            if (commonSharedPreference.getLoginSharedPref(getApplicationContext()).getToken() != null) {
                token = commonSharedPreference.getLoginSharedPref(getApplicationContext()).getToken();
            }else {
                token = "";
            }
        }catch (NullPointerException ignored){

        }


        initializeViews();
        getMyAccountantDetails();
    }

    private void getMyAccountantDetails() {
            ApiService api = UserMoreDetailActivity.RetroClient.getApiService();
            Call<AccountantDetail> call = api.getAccountantDetails(token,accountantid);
            call.enqueue(new Callback<AccountantDetail>() {
                @Override
                public void onResponse(Call<AccountantDetail> call, Response<AccountantDetail> response) {
                    progressBar.setVisibility(View.GONE);
                    try {
                        accountantDataList =response.body().getData();

                        accountantDetailsAdapter = new AccountantDetailsAdapter(getApplicationContext(),accountantDataList);
                        RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL, false);
                        rv_clientActivity.setLayoutManager(eLayoutManager);
//                recyclerView.setItemAnimator(new DefaultItemAnimator());
                        rv_clientActivity.setAdapter(accountantDetailsAdapter);
                    }catch (NullPointerException ignored){

                    }

                }

                @Override
                public void onFailure(Call<AccountantDetail> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();

                }
            });

        }



        private void initializeViews() {
        clientName =(TextView)findViewById(R.id.clientName);
        back = (ImageView)findViewById(R.id.back);
        rv_clientActivity = (RecyclerView)findViewById(R.id.rv_clienActivity);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        tv_cancel = (TextView)findViewById(R.id.tv_cancel);

    }
}
