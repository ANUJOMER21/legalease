package client.legalease.Fragment.ServiceDetails;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import client.legalease.Adapter.AdapterPre;
import client.legalease.Model.Services.ServiceDetails.Requisty;
import client.legalease.Model.Services.ServiceDetails.ServiceDetails;
import client.legalease.R;
import client.legalease.ServicesDetails;
import client.legalease.UserMoreDetailActivity;
import client.legalease.WebServices.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentPre extends Fragment {

    String id ="";
    public static List<Requisty> preRequestList;
    RecyclerView rv_pre;
    ProgressBar progressBar;
    AdapterPre adapterPre;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pre, container, false);
        rv_pre = (RecyclerView)rootView.findViewById(R.id.rv_pre);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);

        preRequestList = new ArrayList<>();
        ServicesDetails activity = (ServicesDetails) getActivity();
        id = activity.getId();
        getServiceDetailsData();
        return rootView;
    }

    private void getServiceDetailsData() {

        ApiService api = UserMoreDetailActivity.RetroClient.getApiService();
        Call<ServiceDetails> call = api.getSeviceDetailsData(id);
        call.enqueue(new Callback<ServiceDetails>() {
            @Override
            public void onResponse(Call<ServiceDetails> call, Response<ServiceDetails> response) {
                progressBar .setVisibility(View.GONE);

                try {
                    preRequestList =response.body().getRequisties();




                    adapterPre = new AdapterPre(getContext(),preRequestList);
                    RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
                    rv_pre.setLayoutManager(eLayoutManager);
//                recyclerView.setItemAnimator(new DefaultItemAnimator());
                    rv_pre.setAdapter(adapterPre);
                }catch (NullPointerException ignored){

                }catch (Exception ignore){

                }

            }

            @Override
            public void onFailure(Call<ServiceDetails> call, Throwable t) {
                Toast.makeText(getContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);


            }
        });

    }

}
