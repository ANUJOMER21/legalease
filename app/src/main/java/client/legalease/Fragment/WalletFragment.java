package client.legalease.Fragment;

import android.graphics.Typeface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import client.legalease.Adapter.AdapterQuiz;
import client.legalease.Fragment.WalletSubFragment.TotalWalletFragment;
import client.legalease.Fragment.WalletSubFragment.UsedWalletFragment;
import client.legalease.Model.QuizModel.QuestionSet.SetModel;
import client.legalease.Model.WalletModel;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.R;
import client.legalease.UserMoreDetailActivity;
import client.legalease.WebServices.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletFragment extends Fragment {


    CommonSharedPreference commonSharedPreference;
    String token = "";
    String name = "";
    String referal ="";
    TextView tv_walletPoint;
    ProgressBar progressBar;

    private ViewPager viewpagerProfileFragment;
    ViewPagerAdapter adapter;
    private TabLayout tabLayout;
    TextView tv_credit;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wallet, container, false);



        commonSharedPreference = new CommonSharedPreference(getActivity());

        initializeView(rootView);




        Typeface font2 = Typeface.createFromAsset(getActivity().getAssets(),"AbrilFatface-Regular.otf");
        Typeface font1 = Typeface.createFromAsset(getActivity().getAssets(),"Jipatha-Regular.ttf");


        tv_walletPoint.setTypeface(font1);
        tv_credit.setTypeface(font1);



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


        getMyWallet();
        addTabs(viewpagerProfileFragment);

        tabLayout.setupWithViewPager(viewpagerProfileFragment);
//        setupTabIcons();


        viewpagerProfileFragment.setAdapter(adapter);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            //noinspection ConstantConditions
            TextView tv = (TextView)LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab,null);
            tv.setTypeface(font1);
            tabLayout.getTabAt(i).setCustomView(tv);
        }
        viewpagerProfileFragment.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                viewpagerProfileFragment.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


        return rootView;
    }

    private void initializeView(View rootView) {
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);
        tv_walletPoint  =(TextView)rootView.findViewById(R.id.tv_walletPoint);
        viewpagerProfileFragment = (ViewPager)rootView.findViewById(R.id.viewpagerProfileFragment);
        tabLayout = (TabLayout)rootView.findViewById(R.id.profileFragmnttabs);
        tv_credit = (TextView)rootView.findViewById(R.id.tv_credit);


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





    private void addTabs(ViewPager viewPager) {

        String toatlWallet = "Credit History";
        String usedWallet = "Use Credit";



        adapter = new ViewPagerAdapter(this.getChildFragmentManager());
        adapter.addFrag(new TotalWalletFragment(), toatlWallet);
        adapter.addFrag(new UsedWalletFragment(), usedWallet);


        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}