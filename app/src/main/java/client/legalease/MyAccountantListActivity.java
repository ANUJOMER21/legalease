package client.legalease;

import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import client.legalease.Adapter.ClientAdapter;
import client.legalease.Model.AccountantModel.AccountantData;
import client.legalease.Model.AccountantModel.AccountantModel;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.WebServices.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAccountantListActivity extends AppCompatActivity {

    CommonSharedPreference commonSharedPreference;
    String token = "";
    ImageView back;
    TextView serviceTitle;
    String title = "";
    RecyclerView rv_accountant;
    List<AccountantData> accountantData ;
    ClientAdapter clientAdapter;
    ProgressBar progressBar;
    TextView tv_noAccountant;
    RelativeLayout relative_accoutant;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_accountant_list);

        commonSharedPreference  =new CommonSharedPreference(getApplicationContext());
        try {
            if (commonSharedPreference.getLoginSharedPref(getApplicationContext()).getToken() != null) {
                token = commonSharedPreference.getLoginSharedPref(getApplicationContext()).getToken();
            } else {
                token = "";
            }
        }catch (NullPointerException ignored){

        }


        initializeViews();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAccountantListActivity.this,HomeActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });


        try {
            Intent intent = getIntent();
            title = intent.getStringExtra("serviceTitle");
            serviceTitle.setText(title);
        }catch (NullPointerException ignored){

        }

        getaccountantData();

    }

    private void initializeViews() {
        back = (ImageView)findViewById(R.id.back);
        serviceTitle = (TextView)findViewById(R.id.serviceTitle);
        rv_accountant =(RecyclerView)findViewById(R.id.rv_accountant);
        progressBar  = (ProgressBar)findViewById(R.id.progressBar);
        tv_noAccountant= (TextView)findViewById(R.id.tv_noAccountant);
        relative_accoutant = (RelativeLayout)findViewById(R.id.relative_accoutant);
    }



    private void getaccountantData() {

        ApiService api = UserMoreDetailActivity.RetroClient.getApiService();
        Call<AccountantModel> call = api.getAccountantList(token);
        call.enqueue(new Callback<AccountantModel>() {
            @Override
            public void onResponse(Call<AccountantModel> call, Response<AccountantModel> response) {
                progressBar .setVisibility(View.GONE);
                try {
                    accountantData = response.body().getData();
                    if (accountantData.size()==0){
                        tv_noAccountant.setVisibility(View.VISIBLE);
                        relative_accoutant.setVisibility(View.GONE);
                    }else {

                        tv_noAccountant.setVisibility(View.GONE);
                        relative_accoutant.setVisibility(View.VISIBLE);

                    }
                }catch (NullPointerException ignored){

                }catch (IndexOutOfBoundsException ignore){

                }


                clientAdapter = new ClientAdapter(MyAccountantListActivity.this,accountantData);
                RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL, false);
                rv_accountant.setLayoutManager(eLayoutManager);
//                recyclerView.setItemAnimator(new DefaultItemAnimator());
                rv_accountant.setAdapter(clientAdapter);

            }

            @Override
            public void onFailure(Call<AccountantModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();

            }
        });

    }

}
