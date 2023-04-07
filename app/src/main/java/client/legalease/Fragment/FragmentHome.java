package client.legalease.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import client.legalease.Adapter.AccountAdapter;
import client.legalease.Adapter.ImageAdap;
import client.legalease.Adapter.IntroAdapter;
import client.legalease.Adapter.PartnerGridAdapterhome;
import client.legalease.Adapter.ServiceAdapterHome;
import client.legalease.Adapter.SubServicesListAdapter;
import client.legalease.Common.CustomLinearLayoutManager;
import client.legalease.Model.AccountModel.Account;
import client.legalease.Model.AccountModel.AccountData;
import client.legalease.Model.Appslider.AppSliderModel;
import client.legalease.Model.Appslider.SliderData;
import client.legalease.Model.PartnerModel.PartnerDetail;
import client.legalease.Model.Services.ServicesParentModule.Service;
import client.legalease.Model.Services.ServicesParentModule.ServiceModel;
import client.legalease.Model.SubServicesListModel.SubServiceListModel;
import client.legalease.Model.SubServicesListModel.SubservicesListData;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.R;
import client.legalease.RetrofitClient.RetrofitClient;
import client.legalease.UserMoreDetailActivity;
import client.legalease.ViewAllPartner;
import client.legalease.WebServices.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static client.legalease.APIConstant.ApiConstant.IMAGEURL;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class FragmentHome extends Fragment {

    CommonSharedPreference commonSharedPreference;
    int id = 0;
    String myToken ="";
    String name ="";
    String email = "";
    String id_cms_privileges = "";
    String mobile ="";
    String country = "";
    String state = "";
    String city = "";
    String pincode = "";
    String address = "";
    String qualification = "";

    List<SliderData> appsliderdata = null;
    ViewPager viewPager;
    String[] urls;
    private static int NUM_PAGES = 0;
    int currentPage = 1;

    RecyclerView rv_mySevices,rv_account;
    ProgressBar progressBar,progressBar_account;
    List<Service> servicesData ;
    List<AccountData> accountData ;

    ServiceAdapterHome servicesAdapter;
    AccountAdapter accountAdapter;
    LinearLayout linear;
    IntroAdapter introAdapter;
    RecyclerView rv_slider;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initializeViews(rootView);
        commonSharedPreference = new CommonSharedPreference(getActivity());

        try {
            if (commonSharedPreference.getLoginSharedPref(getActivity()).getToken() != null) {
                myToken = commonSharedPreference.getLoginSharedPref(getActivity()).getToken();
                Log.d("token", myToken);
                id = commonSharedPreference.getLoginSharedPref(getActivity()).getId();
                mobile = commonSharedPreference.getLoginSharedPref(getActivity()).getMobile();
            }

        }catch (NullPointerException ignored){

        }catch (IndexOutOfBoundsException ignore){

        }

        appsliderdata = new ArrayList<>();


        servicesData = new ArrayList<>();

        getIntroData();

        getServiceData();
        getAccountData();


        return rootView;
    }





    //perfect working
    private void getAccountData() {


        ApiService api = UserMoreDetailActivity.RetroClient.getApiService();
        Call<Account> call = api.getAccountData();
        call.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                progressBar_account .setVisibility(View.GONE);
                String status = "";
                try {
                    accountData = response.body().getAccountData();
                }catch (NullPointerException ignored){

                }catch (IndexOutOfBoundsException ignore){

                }


                accountAdapter = new AccountAdapter(getContext(),accountData);

                RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
                rv_account.setLayoutManager(eLayoutManager);
//                recyclerView.setItemAnimator(new DefaultItemAnimator());
                rv_account.setAdapter(accountAdapter);

            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d("accountdatafailure", "onFailure: "+t.toString());
                Toast.makeText(getContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();

            }
        });
    }

    //perfectly working only image are not loading
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
                Log.d("servicdata", String.valueOf(servicesData));


                servicesAdapter = new ServiceAdapterHome(getContext(),servicesData);

                GridLayoutManager manager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);


                rv_mySevices.setLayoutManager(manager);

//                recyclerView.setItemAnimator(new DefaultItemAnimator());
                rv_mySevices.setAdapter(servicesAdapter);

            }

            @Override
            public void onFailure(Call<ServiceModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d("servicedatafailure", "onFailure: "+t.toString());
                Toast.makeText(getActivity(),"Please chk your internet connection",Toast.LENGTH_SHORT).show();

            }
        });

    }




    // perfectly working but image notfound
    private void getIntroData() {

        ApiService api = RetrofitClient.getApiService();
        Call<AppSliderModel> call = api.getAppSlider();
        call.enqueue(new Callback<AppSliderModel>() {
            @Override
            public void onResponse(Call<AppSliderModel> call, Response<AppSliderModel> response) {
                Log.d("res1", "onResponse: "+response.toString());
                try {

                    ArrayList<String> arrlist= new ArrayList<String>();
                    arrlist.clear();
                    appsliderdata = response.body().getData();
                    urls = new String[appsliderdata.size()];

                    for (int i=0;i<appsliderdata.size();i++){
                        String imageUrl = IMAGEURL+appsliderdata.get(i).getImage();
                        urls[i]=imageUrl;

                    }

                    introAdapter = new IntroAdapter(getContext(),appsliderdata);
                    rv_slider.setLayoutManager(new CustomLinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                    Log.d("introdata", "getIntroData: "+urls[0]);
//                    RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
//                    rv_slider.setLayoutManager(eLayoutManager);
                    rv_slider.setItemAnimator(new DefaultItemAnimator());
                    rv_slider.setAdapter(introAdapter);
                    init();
                    final int speedScroll = 4000;
                    final Handler handler = new Handler();
                    final Runnable runnable = new Runnable() {
                        int count = 0;
                        boolean flag = true;
                        @Override
                        public void run() {
                            if(count < introAdapter.getItemCount()){
                                if(count==introAdapter.getItemCount()-1){
                                    flag = false;
                                }else if(count == 0){
                                    flag = true;
                                }
                                if(flag) count++;
                                else count--;

                                rv_slider.smoothScrollToPosition(count);
                                handler.postDelayed(this,speedScroll);
                            }
                        }
                    };

                    handler.postDelayed(runnable,speedScroll);

//                    init();

                } catch (NullPointerException ignored) {

                }catch (IllegalStateException ignore){

                }catch (IndexOutOfBoundsException ignor){

                }
                Log.d("dsfsdb", String.valueOf(appsliderdata));




            }


            @Override
            public void onFailure(Call<AppSliderModel> call, Throwable t) {

            }
        });
    }


    private void initializeViews(View rootView) {
        viewPager = (ViewPager)rootView.findViewById(R.id.viewPager);

        rv_mySevices = (RecyclerView)rootView.findViewById(R.id.rv_mySevices);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);
        progressBar_account = (ProgressBar)rootView.findViewById(R.id.progressBar_account);


        rv_account = (RecyclerView)rootView.findViewById(R.id.rv_account);
        rv_slider = (RecyclerView)rootView.findViewById(R.id.rv_slider);

        linear = (LinearLayout)rootView.findViewById(R.id.linear);






    }



    private void init() {
        Log.d("initview", "init: "+urls[0]);
        viewPager.setAdapter(new ImageAdap(getContext(),urls));

//        CirclePageIndicator indicator = (CirclePageIndicator)
//                findViewById(R.userId.indicator);
//
//        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;



        NUM_PAGES = urls.length;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 5000, 5000);


    }


}
