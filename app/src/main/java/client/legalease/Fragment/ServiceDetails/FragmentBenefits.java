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

import client.legalease.Adapter.AdapterBenefit;
import client.legalease.Model.Services.ServiceDetails.Benefit;
import client.legalease.Model.Services.ServiceDetails.ServiceDetails;
import client.legalease.R;
import client.legalease.ServicesDetails;
import client.legalease.UserMoreDetailActivity;
import client.legalease.WebServices.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentBenefits extends Fragment {


    public static List<Benefit> benefitList;
    String id = "";
    RecyclerView rv_benefit;
    ProgressBar progressBar;
    AdapterBenefit adapterBenefit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_benefit, container, false);

        initializeView(rootView);

        benefitList = new ArrayList<>();
        ServicesDetails activity = (ServicesDetails) getActivity();
        id = activity.getId();
        getServiceDetailsData();

        return rootView;
    }


    private void initializeView(View rootView) {
        rv_benefit = (RecyclerView)rootView.findViewById(R.id.rv_benefit);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);
    }
    private void getServiceDetailsData() {

        ApiService api = UserMoreDetailActivity.RetroClient.getApiService();
        Call<ServiceDetails> call = api.getSeviceDetailsData(id);
        call.enqueue(new Callback<ServiceDetails>() {
            @Override
            public void onResponse(Call<ServiceDetails> call, Response<ServiceDetails> response) {
                progressBar .setVisibility(View.GONE);

                try {
                    benefitList = response.body().getBenefits();



                    adapterBenefit = new AdapterBenefit(getContext(),benefitList);
                    RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
                    rv_benefit.setLayoutManager(eLayoutManager);
//                recyclerView.setItemAnimator(new DefaultItemAnimator());
                    rv_benefit.setAdapter(adapterBenefit);
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
