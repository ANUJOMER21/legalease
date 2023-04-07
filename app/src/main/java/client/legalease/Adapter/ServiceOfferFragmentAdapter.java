package client.legalease.Adapter;



import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import client.legalease.Fragment.ServiceOfferFragment.AcceptedServiceFragment;
import client.legalease.Fragment.ServiceOfferFragment.RejectedServiceFragment;

public class ServiceOfferFragmentAdapter extends FragmentPagerAdapter {
    public ServiceOfferFragmentAdapter(@NonNull FragmentManager fm){
        super(fm);
    }
    @NonNull
    @Override
    public Fragment getItem(int i) {
     Fragment fragment=null;
     if(i==0){
         fragment=new AcceptedServiceFragment();

     }
     else if(i==1){
         fragment=new RejectedServiceFragment();

     }
     return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position)
    {
        String title = null;
        if (position == 0)
            title = "Active Offer";
        else if (position == 1)
            title = "Rejected Offer";

        return title;
    }
}
