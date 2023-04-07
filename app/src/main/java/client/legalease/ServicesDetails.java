package client.legalease;

import android.content.Intent;
import android.graphics.Typeface;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import client.legalease.Fragment.ServiceDetails.FragmentBenefits;
import client.legalease.Fragment.ServiceDetails.FragmentDeleverables;
import client.legalease.Fragment.ServiceDetails.FragmentFaq;
import client.legalease.Fragment.ServiceDetails.FragmentOverView;
import client.legalease.Fragment.ServiceDetails.FragmentPre;
import client.legalease.Fragment.WalletFragment;
import client.legalease.Model.Services.ServiceDetails.Benefit;
import client.legalease.Model.Services.ServiceDetails.Deliverable;
import client.legalease.Model.Services.ServiceDetails.Faq;
import client.legalease.Model.Services.ServiceDetails.Requisty;
import client.legalease.Model.Services.ServiceDetails.ServiceDetails;
import client.legalease.WebServices.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicesDetails extends AppCompatActivity {
    String id ="";
    String name="";
    ImageView back;
    TextView serviceName;
    String title= "";
    String sub_title = "";
    String price = "";

    public static List<Benefit> benefitList;
    public static List<Deliverable> deliverableList;
    public static List<Faq> faqList;
    public static List<Requisty> requistyList;
    Button btn_getStarted;
    String packageId = "";
    String packageName = "";



    ViewPager viewpagerProfileFragment;
    private TabLayout tabLayout;
    ViewPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sercices_details);
        initializeViews();



        try {
            Intent intent = getIntent();
            id = intent.getStringExtra("parentId");
            name = intent.getStringExtra("name");
            serviceName.setText(name);
            price = intent.getStringExtra("price");
        }catch (NullPointerException ignored){
        }catch (Exception ignore){

        }
        Typeface font1 = Typeface.createFromAsset(getApplicationContext().getAssets(),"Jipatha-Regular.ttf");


        benefitList = new ArrayList<>();
        deliverableList = new ArrayList<>();
        faqList = new ArrayList<>();
        requistyList = new ArrayList<>();
        btn_getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                    Intent intent = new Intent(ServicesDetails.this, ViewAllPartner.class);
                    intent.putExtra("id", id);
                    intent.putExtra("name", name);
                   intent.putExtra("price", price);
intent.putExtra("servicename",serviceName.getText().toString());
                startActivity(intent);
            //    finish();

            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
     //           Intent intent= new Intent(ServicesDetails.this,SubServicesActivity.class);
//                intent.putExtra("parentId",id);
//                startActivity(intent);
            }
        });


        viewpagerProfileFragment = (ViewPager)findViewById(R.id.viewpagerServiceFragment);
        addTabs(viewpagerProfileFragment);




        tabLayout = (TabLayout)findViewById(R.id.serviceFragmnttabs);
        tabLayout.setupWithViewPager(viewpagerProfileFragment);
//        setupTabIcons();

        getServiceDetailsData();


        viewpagerProfileFragment.setAdapter(adapter);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            //noinspection ConstantConditions
            TextView tv = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.service_details_custum_tab,null);
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

    }


    public List<Benefit> getbenefit(){
        return benefitList;
    }public List<Deliverable> getdeliver(){
        return deliverableList;
    }public List<Faq> getFaq(){
        return faqList;
    }

    public List<Requisty> getRequity(){
        return requistyList;
    }
    public String getId(){
        return id;

    }

    private void initializeViews() {
        back = (ImageView)findViewById(R.id.back);
        serviceName = (TextView)findViewById(R.id.serviceTitle);
        btn_getStarted =(Button)findViewById(R.id.btn_getStarted);
    }

    private void addTabs(ViewPager viewPager) {

         adapter = new ViewPagerAdapter(getSupportFragmentManager());
        String overView ="OverView" ;
        String benefits = "Benefits";
        String pre = "Pre Requiste";
        String deliverable ="Deleverables" ;
        String faq = "FAQs";



        adapter.addFrag(new FragmentOverView(), overView);
        adapter.addFrag(new FragmentBenefits(), benefits);
        adapter.addFrag(new FragmentPre(), pre);
        adapter.addFrag(new FragmentDeleverables(), deliverable);
        adapter.addFrag(new FragmentFaq(), faq);



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


// API CALL


    private void getServiceDetailsData() {

        ApiService api = UserMoreDetailActivity.RetroClient.getApiService();
        Call<ServiceDetails> call = api.getSeviceDetailsData(id);
        call.enqueue(new Callback<ServiceDetails>() {
            @Override
            public void onResponse(Call<ServiceDetails> call, Response<ServiceDetails> response) {

                benefitList = response.body().getBenefits();
                deliverableList = response.body().getDeliverables();
                faqList = response.body().getFaq();
                requistyList = response.body().getRequisties();



            }

            @Override
            public void onFailure(Call<ServiceDetails> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();

            }
        });

    }



}
