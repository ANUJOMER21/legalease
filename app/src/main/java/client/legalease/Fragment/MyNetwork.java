package client.legalease.Fragment;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import client.legalease.Adapter.ClientAdapter;
import client.legalease.Adapter.NetworkAdapter;
import client.legalease.Model.AccountantModel.AccountantModel;
import client.legalease.Model.MyNetworkModel.MyNetworkModel;
import client.legalease.Model.MyNetworkModel.NetworkUser;
import client.legalease.MyAccountantListActivity;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.R;
import client.legalease.UserMoreDetailActivity;
import client.legalease.WebServices.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyNetwork extends Fragment {

    RecyclerView rv_myNetwrok;
    ProgressBar progressBar;
    CommonSharedPreference commonSharedPreference;
    String token = "";
    String id ="";
    String mobile = "";
    List<NetworkUser> networkUsers ;
    TextView tv_networkNoData;
    RelativeLayout relative_network;
    NetworkAdapter networkAdapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mynetwork, container, false);
        commonSharedPreference = new CommonSharedPreference(getActivity());

        try {

            if (commonSharedPreference.loggedin()) {
                token = commonSharedPreference.getLoginSharedPref(getActivity()).getToken();

            } else {
                token = "";

            }
        }catch (NullPointerException ignored){
        }
        Log.d("gvhg",token);
        Log.d("vf",token);

        initializeView(rootView);
        getData();
        return rootView;
    }

    private void getData() {

        ApiService api = UserMoreDetailActivity.RetroClient.getApiService();
        Call<MyNetworkModel> call = api.myNetworkList(token);
        call.enqueue(new Callback<MyNetworkModel>() {
            @Override
            public void onResponse(Call<MyNetworkModel> call, Response<MyNetworkModel> response) {
                Log.d("netweorkres", "onResponse: "+response);
                progressBar .setVisibility(View.GONE);
                try {
                    networkUsers = response.body().getUser();
                    if (networkUsers.size()==0){
                        tv_networkNoData.setVisibility(View.VISIBLE);
                        relative_network.setVisibility(View.GONE);
                    }else {

                        tv_networkNoData.setVisibility(View.GONE);
                        relative_network.setVisibility(View.VISIBLE);

                    }
                }catch (NullPointerException ignored){

                }catch (IndexOutOfBoundsException ignore){

                }


                Log.d("networkres", "onResponse: "+response.toString());
                networkAdapter = new NetworkAdapter(getContext(),networkUsers);
                RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
                rv_myNetwrok.setLayoutManager(eLayoutManager);
//                recyclerView.setItemAnimator(new DefaultItemAnimator());
                rv_myNetwrok.setAdapter(networkAdapter);

            }

            @Override
            public void onFailure(Call<MyNetworkModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initializeView(View rootView) {
        progressBar  = (ProgressBar)rootView.findViewById(R.id.progressBar);
        rv_myNetwrok =(RecyclerView)rootView.findViewById(R.id.rv_myNetwrok);
        tv_networkNoData = (TextView)rootView.findViewById(R.id.tv_networkNoData);
        relative_network = (RelativeLayout)rootView.findViewById(R.id.relative_network);
    }

}
