package client.legalease.Fragment.Order;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import client.legalease.Adapter.OrderAdapter;
import client.legalease.Model.Acceptedordermodel.AcceptedOrderModel;
import client.legalease.Model.Acceptedordermodel.Datum;
import client.legalease.Model.OrderModel.OrderData;
import client.legalease.Model.OrderModel.OrderModel;
import client.legalease.Model.myorder;

import client.legalease.Preference.CommonSharedPreference;
import client.legalease.R;
import client.legalease.UserMoreDetailActivity;
import client.legalease.WebServices.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpenFragment extends Fragment {


    ViewPager viewpagerProfileFragment;
    private TabLayout tabLayout;
    RecyclerView rv_order;
    List<Datum> datumList=new ArrayList<>();
    OrderAdapter orderAdapetr;
    Boolean isscrolling=false;
    int totalsize=0,size=0;
    int page1=1,lastpage=1;
    String type = "1";
    ProgressBar progressBar;
    CommonSharedPreference commonSharedPreference;
    String token  ="";
    String selector = "1";
    TextView tv_noData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_open_order, container, false);

        commonSharedPreference = new CommonSharedPreference(getActivity());

        try {

            if (commonSharedPreference.loggedin()) {
                token = commonSharedPreference.getLoginSharedPref(getActivity()).getToken();

            } else {
                token = "";

            }
        }catch (NullPointerException ignored){
        }

        rv_order = (RecyclerView)rootView.findViewById(R.id.rv_order);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);
        tv_noData = (TextView)rootView.findViewById(R.id.tv_noData);
        RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        rv_order.setLayoutManager(eLayoutManager);
        datumList=new ArrayList<>();
        getInvoiceData(page1);
        rv_order.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isscrolling=true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(lastpage!=1){
                    if(isscrolling&& lastpage>=page1&& size!=totalsize){
                        getInvoiceData(++page1);

                    }
                }
            }
        });

        return rootView;
    }






    private void getInvoiceData(int page) {
            ApiService api = UserMoreDetailActivity.RetroClient.getApiService();
        CommonSharedPreference commonSharedPreference=new CommonSharedPreference(getActivity());
        String token = "Bearer " + commonSharedPreference.getToken();
        Call<AcceptedOrderModel> call = api.getMyBills(token,String.valueOf(page));
call.enqueue(new Callback<AcceptedOrderModel>() {
    @Override
    public void onResponse(Call<AcceptedOrderModel> call, Response<AcceptedOrderModel> response) {

        if (response.body() == null) {
            Toast.makeText(getActivity(), "null", Toast.LENGTH_SHORT).show();
            Log.d("service_request", "onResponse: " + response);
        } else {
            Log.d("openfragment", "onResponse: "+page+"lastpage"+lastpage);
            progressBar.setVisibility(View.GONE);
            List<Datum> data = new ArrayList<>();
            lastpage = response.body().getOrderslist().getLastPage();
           // page1=response.body().getOrderslist().getCurrentPage();
            try {
               for (Datum datum3 : response.body().getOrderslist().getData()) {
                    if (!datum3.getStatus().equals("8")) {
                        data.add(datum3);
                    }

                }
                datumList.addAll(data);

size= data.size()+size;
totalsize=response.body().getOrderslist().getTotal();
                Log.d("openfragmentpage", "onResponse: data"+size+"datum"+totalsize);
                if (datumList.size() == 0) {
                    rv_order.setVisibility(View.GONE);
                    tv_noData.setVisibility(View.VISIBLE);
                } else {
                    tv_noData.setVisibility(View.GONE);
                    rv_order.setVisibility(View.VISIBLE);
                    Log.d("data", "onResponse: " + response);
                    orderAdapetr = new OrderAdapter(getContext(), datumList, selector);
orderAdapetr.notifyDataSetChanged();
//                recyclerView.setItemAnimator(new DefaultItemAnimator());
                    rv_order.setAdapter(orderAdapetr);
                }
            } catch (Exception e) {
                tv_noData.setVisibility(View.VISIBLE);
                Log.d("exceptionfrag", "onResponse: " + e.toString());
                rv_order.setVisibility(View.GONE);
            }

        }
    }


    @Override
    public void onFailure(Call<AcceptedOrderModel> call, Throwable t) {
        progressBar.setVisibility(View.GONE);
      //  Toast.makeText(getContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();
    }
});

          /**  Call<OrderModel> call = api.getMyBills(token,type);
            call.enqueue(new Callback<OrderModel>() {
                @Override
                public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                    progressBar.setVisibility(View.GONE);
                    try {
                        orderData = response.body().getData();
                        if (orderData.size()==0){
                            tv_noData.setVisibility(View.VISIBLE);
                            rv_order.setVisibility(View.GONE);
                        }else{
                            tv_noData.setVisibility(View.GONE);
                            rv_order.setVisibility(View.VISIBLE);

                            orderAdapetr = new OrderAdapter(getContext(), orderData,selector);
                            RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
                            rv_order.setLayoutManager(eLayoutManager);
//                recyclerView.setItemAnimator(new DefaultItemAnimator());
                            rv_order.setAdapter(orderAdapetr);
                        }


                    }catch (NullPointerException ignored){
                        tv_noData.setVisibility(View.VISIBLE);
                        rv_order.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onFailure(Call<OrderModel> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(),"Please check your internet connection",Toast.LENGTH_SHORT).show();

                }
            });**/


        }

    }