package client.legalease.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import client.legalease.Adapter.ServiceOfferFragmentAdapter;
import client.legalease.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Service_offer_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Service_offer_fragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    ServiceOfferFragmentAdapter serviceOfferFragmentAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Service_offer_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Service_offer_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Service_offer_fragment newInstance(String param1, String param2) {
        Service_offer_fragment fragment = new Service_offer_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=         inflater.inflate(R.layout.fragment_service_offer_fragment, container, false);
initview(view);
        serviceOfferFragmentAdapter=new ServiceOfferFragmentAdapter(this.getChildFragmentManager());
        viewPager.setAdapter(serviceOfferFragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        serviceOfferFragmentAdapter=new ServiceOfferFragmentAdapter(this.getChildFragmentManager());
        viewPager.setAdapter(serviceOfferFragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initview(View view) {
        tabLayout=view.findViewById(R.id.tablayoutaso);
        viewPager=view.findViewById(R.id.viewpager2);
    }
}