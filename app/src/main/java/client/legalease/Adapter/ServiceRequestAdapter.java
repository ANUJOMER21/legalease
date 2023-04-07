package client.legalease.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import client.legalease.Fragment.ServiceRequest.ServiceRequestActive;
import client.legalease.Fragment.ServiceRequest.ServiceRequestRejected;

public class ServiceRequestAdapter  extends FragmentPagerAdapter {
    public ServiceRequestAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {
       Fragment fragment=null;
       if(i==0){
           fragment=new ServiceRequestActive();

       }
       else if(i==1){
           fragment=new ServiceRequestRejected();
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
            title = "Open Request";
        else if (position == 1)
            title = "Close Request";

        return title;
    }
}
