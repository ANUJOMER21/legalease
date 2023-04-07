package client.legalease.Fragment.WalletSubFragment;

import android.graphics.Typeface;
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

import client.legalease.Fragment.ServicesFragment;
import client.legalease.Model.WalletModel;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.R;
import client.legalease.UserMoreDetailActivity;
import client.legalease.WebServices.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TotalWalletFragment extends Fragment {

    TextView tv_hint;
    String referal ="";
    CommonSharedPreference commonSharedPreference;
    String token = "";
    String name = "";
    ProgressBar progressBar;
    TextView tv_walletPoint;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_totalwallet, container, false);

        commonSharedPreference = new CommonSharedPreference(getActivity());
        try {

            if (commonSharedPreference.loggedin()){
                token = commonSharedPreference.getLoginSharedPref(getActivity()).getToken();

//                name = commonSharedPreference.getLoginSharedPref(getActivity()).getName();

            }else {
                token = "";
                name= "User Name";

            }

        }catch (NullPointerException ignored){

        }catch (IndexOutOfBoundsException ignore){

        }
        progressBar=(ProgressBar)rootView.findViewById(R.id.progressBar);


        tv_hint = (TextView)rootView.findViewById(R.id.tv_hint);
        tv_walletPoint = (TextView)rootView.findViewById(R.id.tv_walletPoint);

        Typeface font2 = Typeface.createFromAsset(getActivity().getAssets(),"AbrilFatface-Regular.otf");
        Typeface font1 = Typeface.createFromAsset(getActivity().getAssets(),"Jipatha-Regular.ttf");


        tv_hint.setTypeface(font1);
        getMyWallet();

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