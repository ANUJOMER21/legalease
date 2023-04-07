package client.legalease.Fragment;

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

import client.legalease.Fragment.Order.OpenFragment;
import client.legalease.Fragment.Order.ClosedFragment;
import client.legalease.R;

public class BillingFragment extends Fragment {



    ViewPager viewpagerProfileFragment;
    private TabLayout tabLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_billing, container, false);
//
//
//        viewpagerProfileFragment = (ViewPager)rootView.findViewById(R.id.viewpagerServiceFragment);
//        addTabs(viewpagerProfileFragment);
//
//
//
//
//        tabLayout = (TabLayout)rootView.findViewById(R.id.serviceFragmnttabs);
//        tabLayout.setupWithViewPager(viewpagerProfileFragment);
        return rootView;
    }

    private void addTabs(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        String openInvoice ="Open Invoices" ;
        String paidInvoce = "Paid Invoices";



        adapter.addFrag(new OpenFragment(), openInvoice);
        adapter.addFrag(new ClosedFragment(), paidInvoce);

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