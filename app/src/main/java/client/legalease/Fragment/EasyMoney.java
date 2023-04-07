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

import org.w3c.dom.Text;

import java.util.List;

import client.legalease.Adapter.NetworkAdapter;
import client.legalease.Adapter.PaymentMethodTypeModel;
import client.legalease.Model.MyEasyMoneyModel.MyEasyMoneyModel;
import client.legalease.Model.MyNetworkModel.NetworkUser;
import client.legalease.Model.PaymentMethodModel.EasyMoneyModel;
import client.legalease.Model.PaymentMethodModel.PaymentMethodData;
import client.legalease.Model.PaymentMethodModel.PaymentMethodModel;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.R;
import client.legalease.UserMoreDetailActivity;
import client.legalease.WebServices.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EasyMoney extends Fragment {
    RecyclerView rv_redeemOption;
    List<PaymentMethodData> paymentMethodData ;
    List<EasyMoneyModel> easyMoneyModels;

    RelativeLayout relative_paymentMethod;
    ProgressBar progressBar;
    TextView tv_noMethodDatahint;
    PaymentMethodTypeModel paymentMethodTypeModel;
    TextView tv_easyCash;
    String token = "";
    CommonSharedPreference commonSharedPreference;

    String cashMoney = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_easymoney, container, false);
        rv_redeemOption = (RecyclerView)rootView.findViewById(R.id.rv_redeemOption);
        relative_paymentMethod = (RelativeLayout)rootView.findViewById(R.id.relative_paymentMethod);
        progressBar =(ProgressBar)rootView.findViewById(R.id.progressBar);
        tv_noMethodDatahint  =(TextView)rootView.findViewById(R.id.tv_noMethodDatahint);
        tv_easyCash = (TextView)rootView.findViewById(R.id.tv_easyCash);

        commonSharedPreference = new CommonSharedPreference(getActivity());

        try {

            if (commonSharedPreference.loggedin()) {
                token = commonSharedPreference.getLoginSharedPref(getActivity()).getToken();

            } else {
                token = "";

            }
        }catch (NullPointerException ignored){
        }

        Log.d("easy", "onCreateView: "+token);
        getData();
        return  rootView;
    }




    private void getData() {

        ApiService api = UserMoreDetailActivity.RetroClient.getApiService();
        Call<PaymentMethodModel> call = api.myPaymentMethodType(token);
        call.enqueue(new Callback<PaymentMethodModel>() {
            @Override
            public void onResponse(Call<PaymentMethodModel> call, Response<PaymentMethodModel> response) {
                Log.d("easymoneyres", "onResponse: "+token);
                progressBar .setVisibility(View.GONE);
                try {
                    paymentMethodData = response.body().getData();
                    if (paymentMethodData.size()==0){
                        tv_noMethodDatahint.setVisibility(View.VISIBLE);
                        relative_paymentMethod.setVisibility(View.GONE);
                    }else {

                        tv_noMethodDatahint.setVisibility(View.GONE);
                        relative_paymentMethod.setVisibility(View.VISIBLE);

                    }
                    cashMoney =response.body().getEasymoney().get(0).getEasymoney();
                    tv_easyCash.setText(cashMoney);
                }catch (NullPointerException ignored){

                }catch (IndexOutOfBoundsException ignore){

                }


                paymentMethodTypeModel = new PaymentMethodTypeModel(getContext(),paymentMethodData,cashMoney);
                RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
                rv_redeemOption.setLayoutManager(eLayoutManager);
//                recyclerView.setItemAnimator(new DefaultItemAnimator());
                rv_redeemOption.setAdapter(paymentMethodTypeModel);

            }

            @Override
            public void onFailure(Call<PaymentMethodModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();
                Log.d("easyMoneyfail", "onFailure: "+t);

            }
        });
    }

}
