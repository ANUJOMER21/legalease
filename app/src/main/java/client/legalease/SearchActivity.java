package client.legalease;

import android.os.Bundle;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import client.legalease.Adapter.SubServicesListAdapter;
import client.legalease.Model.SubServicesListModel.SubServiceListModel;
import client.legalease.Model.SubServicesListModel.SubservicesListData;
import client.legalease.WebServices.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    List<SubservicesListData> subservicesListData = new ArrayList<>();
    SubServicesListAdapter subServicesListAdapter;
    private SearchView searchView;
    private RecyclerView recycler_view;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchView =findViewById(R.id.search_view);
        recycler_view = (RecyclerView)findViewById(R.id.recycler_view);
        progressBar  = (ProgressBar)findViewById(R.id.progressBar);



        getSubServicesList();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryString) {
                subServicesListAdapter.getFilter().filter(queryString);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String queryString) {
                recycler_view.setVisibility(View.VISIBLE);

                subServicesListAdapter.getFilter().filter(queryString);
                return false;
            }
        });

    }


    private void getSubServicesList() {


        ApiService api = UserMoreDetailActivity.RetroClient.getApiService();
        Call<SubServiceListModel> call = api.getSubServiceList();
        call.enqueue(new Callback<SubServiceListModel>() {
            @Override
            public void onResponse(Call<SubServiceListModel> call, Response<SubServiceListModel> response) {
                progressBar .setVisibility(View.GONE);
                String status = "";
                try {
                    subservicesListData = response.body().getData();
                }catch (NullPointerException ignored){

                }catch (IndexOutOfBoundsException ignore){

                }


                subServicesListAdapter = new SubServicesListAdapter(SearchActivity.this,subservicesListData);

                RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL, false);
                recycler_view.setLayoutManager(eLayoutManager);
//                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recycler_view.setAdapter(subServicesListAdapter);

            }

            @Override
            public void onFailure(Call<SubServiceListModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();

            }
        });
    }

}
