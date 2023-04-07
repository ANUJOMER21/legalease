package client.legalease.Fragment.WalletSubFragment;

import android.os.Bundle;

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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import client.legalease.Fragment.ServicesFragment;
import client.legalease.Model.WalletModel;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.R;
import client.legalease.UserMoreDetailActivity;
import client.legalease.WebServices.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsedWalletFragment extends Fragment {


    RelativeLayout relative_buyServices;
    TextView tv_walletPoint;
    CommonSharedPreference commonSharedPreference;
    String token = "";
    String referal="";
    ProgressBar progressBar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_usedwallet, container, false);

        commonSharedPreference = new CommonSharedPreference(getActivity());
        try {

            if (commonSharedPreference.loggedin()){
                token = commonSharedPreference.getLoginSharedPref(getActivity()).getToken();

//                name = commonSharedPreference.getLoginSharedPref(getActivity()).getName();

            }else {
                token = "";

            }

        }catch (NullPointerException ignored){

        }catch (IndexOutOfBoundsException ignore){

        }
        relative_buyServices = (RelativeLayout)rootView.findViewById(R.id.relative_buyServices);
        tv_walletPoint = (TextView)rootView.findViewById(R.id.tv_walletPoint);
        progressBar=(ProgressBar)rootView.findViewById(R.id.progressBar);


        getMyWallet();

        relative_buyServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ServicesFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return rootView;
    }
    private void getMyWallet() {

        ApiService api = UserMoreDetailActivity.RetroClient.getApiService();
        Call<WalletModel> call = api.getWalletData(token);
        call.enqueue(new Callback<WalletModel>() {
            @Override
            public void onResponse(Call<WalletModel> call, Response<WalletModel> response) {
                progressBar.setVisibility(View.GONE);
                try {
                    referal =response.body().getWallet();
                    if (referal.equals("")){
                        tv_walletPoint.setText("00 /-");
                    }else {
                        tv_walletPoint.setText("  " + referal + " /-");
                    }
                }catch (NullPointerException ignored){

                }

            }

            @Override
            public void onFailure(Call<WalletModel> call, Throwable t) {
                Toast.makeText(getContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);


            }
        });

    }





}