package client.legalease.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import client.legalease.Adapter.GetServiceRequestAdapter;
import client.legalease.Adapter.ServiceRequestAdapter;
import client.legalease.Model.customerRequestListModel.CustomerRequestListmodel;
import client.legalease.Model.customerRequestListModel.Datum;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.R;
import client.legalease.RetrofitClient.RetrofitClient;
import client.legalease.Service_request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Service_request_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Service_request_fragment extends Fragment {
    ProgressBar progressBar;
    Boolean isscrolling=false;
    TabLayout tabLayout;
    ViewPager viewPager;
    int page=1,lastpage=1;

    List<Datum> datum2List;
    RecyclerView rv_serviceRequest;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Service_request_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Service_request_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Service_request_fragment newInstance(String param1, String param2) {
        Service_request_fragment fragment = new Service_request_fragment();
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
       View view=  inflater.inflate(R.layout.fragment_service_request_fragment, container, false);
       initView(view);
        ServiceRequestAdapter serviceRequestAdapter=new ServiceRequestAdapter(this.getChildFragmentManager());
        viewPager.setAdapter(serviceRequestAdapter);
        tabLayout.setupWithViewPager(viewPager);
       return view;
    }

    private void initView(View view) {
        tabLayout=view.findViewById(R.id.tablayoutaso);
        viewPager=view.findViewById(R.id.viewpager2);

    }

}