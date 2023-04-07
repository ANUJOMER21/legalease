package client.legalease.Fragment;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import client.legalease.Fragment.Order.ClosedFragment;
import client.legalease.Fragment.Order.OpenFragment;
import client.legalease.R;

public class MyOrder extends Fragment {




    ViewPager viewpagerProfileFragment;
    private TabLayout tabLayout;
    ViewPagerAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_myorder, container, false);


        viewpagerProfileFragment = (ViewPager)rootView.findViewById(R.id.viewpagerServiceFragment);




        tabLayout = (TabLayout)rootView.findViewById(R.id.serviceFragmnttabs);
        addTabs(viewpagerProfileFragment);

        tabLayout.setupWithViewPager(viewpagerProfileFragment);



        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();


    }

    private void addTabs(ViewPager viewPager) {


        adapter = new ViewPagerAdapter(this.getChildFragmentManager());
        String openOrder ="Open Orders" ;
        String paidOrder = "Completed Orders";



        adapter.addFrag(new OpenFragment(), openOrder);
        adapter.addFrag(new ClosedFragment(), paidOrder);

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