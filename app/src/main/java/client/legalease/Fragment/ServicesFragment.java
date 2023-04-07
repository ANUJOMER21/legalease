package client.legalease.Fragment;

import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import client.legalease.Adapter.ServicesAdapter;
import client.legalease.Model.Services.ServicesParentModule.Service;
import client.legalease.Model.Services.ServicesParentModule.ServiceModel;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.R;
import client.legalease.UserMoreDetailActivity;
import client.legalease.WebServices.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicesFragment extends Fragment {
    RecyclerView rv_mySevices;
    ProgressBar progressBar;
    List<Service> servicesData ;
    ServicesAdapter servicesAdapter;
    CommonSharedPreference commonSharedPreference;
    String token = "";
    int id = 0;
    String mobile = "";
    String name = "";
    Context context;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_service, container, false);

        commonSharedPreference = new CommonSharedPreference(getActivity());



        try {

            if (commonSharedPreference.loggedin()){
                token = commonSharedPreference.getLoginSharedPref(getActivity()).getToken();
                id = commonSharedPreference.getLoginSharedPref(getActivity()).getId();
                mobile = commonSharedPreference.getLoginSharedPref(getActivity()).getMobile();
//                name = commonSharedPreference.getLoginSharedPref(getActivity()).getName();

            }else {
                token = "";
                id = 0;
                mobile = "";
                name= "User Name";

            }

        }catch (NullPointerException ignored){

        }catch (IndexOutOfBoundsException ignore){

        }
        getServiceData();

        initializeView(rootView);
        servicesData = new ArrayList<>();


        return rootView;
    }

    private void getServiceData() {

        ApiService api = UserMoreDetailActivity.RetroClient.getApiService();
        Call<ServiceModel> call = api.getSevicesData();
        call.enqueue(new Callback<ServiceModel>() {
            @Override
            public void onResponse(Call<ServiceModel> call, Response<ServiceModel> response) {
                progressBar .setVisibility(View.GONE);
                String status = "";
                try {
                    servicesData = response.body().getService();
                }catch (NullPointerException ignored){

                }catch (IndexOutOfBoundsException ignore){

                }
                Log.d("dsfsdb", String.valueOf(servicesData));


                servicesAdapter = new ServicesAdapter(getContext(),servicesData);

                GridLayoutManager manager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
                rv_mySevices.setLayoutManager(manager);
//                recyclerView.setItemAnimator(new DefaultItemAnimator());
                rv_mySevices.setAdapter(servicesAdapter);

            }

            @Override
            public void onFailure(Call<ServiceModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(),"Please check your internet connection",Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void initializeView(View rootView) {
        rv_mySevices = (RecyclerView)rootView.findViewById(R.id.rv_mySevices);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);
    }
}
