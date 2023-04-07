package client.legalease.Fragment.ServiceRequest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import client.legalease.Adapter.GetServiceRequestAdapter;
import client.legalease.Model.customerRequestListModel.CustomerRequestListmodel;
import client.legalease.Model.customerRequestListModel.Datum;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.R;
import client.legalease.RetrofitClient.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ServiceRequestActive#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServiceRequestActive extends Fragment {
    ProgressBar progressBar;
    Boolean isscrolling=false;
    int page=1,lastpage=1;
    int totalsize=0,size=0;
    List<Datum> datum2List;
    RecyclerView rv_serviceRequest;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ServiceRequestActive() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ServiceRequestActive.
     */
    // TODO: Rename and change types and number of parameters
    public static ServiceRequestActive newInstance(String param1, String param2) {
        ServiceRequestActive fragment = new ServiceRequestActive();
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
        View view= inflater.inflate(R.layout.fragment_service_request_active, container, false);
        initView(view);
        LinearLayoutManager manager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rv_serviceRequest.setLayoutManager(manager);
        datum2List=new ArrayList<>();
        getallrequest(page);
        rv_serviceRequest.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    if(isscrolling&& lastpage>=page && size!=totalsize){
                        getallrequest(++page);

                    }
                }
            }
        });
        return view;
    }
    private void initView(View view) {
        progressBar=view.findViewById(R.id.progressBar_service_rorder);
        rv_serviceRequest=view.findViewById(R.id.rv_service_offer);

    }
    private void getallrequest(int page1) {
        CommonSharedPreference commonSharedPreference=new CommonSharedPreference(getActivity());
        String token = "Bearer " + commonSharedPreference.getToken();
        Log.d("page", "getallrequest: "+page1);
        int client_id=commonSharedPreference.getLoginSharedPref(getContext()).getId();

        final String p=String.valueOf(page1);
        /** Call<ServiceRequestModel> call= RetrofitClient.getApiService().getServiceRequest(p,token,String.valueOf(client_id));
         call.enqueue(new Callback<ServiceRequestModel>() {
        @Override
        public void onResponse(Call<ServiceRequestModel> call, Response<ServiceRequestModel> response) {
        //   Log.d("response service", String.valueOf("onResponse: "+response.body().getOrderlist().getData().get(0).getAssoicate2()));
        if(response.body()==null){
        Toast.makeText(Service_request.this, "null", Toast.LENGTH_SHORT).show();
        Log.d("service_request", "onResponse: "+response);
        }
        else {
        progressBar.setVisibility(View.GONE);
        datum2List=response.body().getOrderlist().getData();
        Log.d("datum2list", "onResponse: "+response.body().getOrderlist().getData().get(0).getAssoicate2().getName());
        lastpage=response.body().getOrderlist().getLastPage();
        String p=String.valueOf(response.body().getOrderlist().getCurrentPage());
        pageview.setText(p);
        LinearLayoutManager manager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        rv_serviceRequest.setLayoutManager(manager);
        if(datum2List==null){

        }
        else
        {        GetServiceRequestAdapter getServiceRequestAdapter=new GetServiceRequestAdapter(getApplicationContext(),datum2List);
        rv_serviceRequest.setAdapter(getServiceRequestAdapter);
        getServiceRequestAdapter.notifyDataSetChanged();}
        }
        }

        @Override
        public void onFailure(Call<ServiceRequestModel> call, Throwable t) {

        }
        });
         **/
        Call<CustomerRequestListmodel> call = RetrofitClient.getApiService().customerreqli(p,token);
        call.enqueue(new Callback<CustomerRequestListmodel>() {
            @Override
            public void onResponse(Call<CustomerRequestListmodel> call, Response<CustomerRequestListmodel> response) {
                if(response.body()==null){
                    Toast.makeText(getActivity(), "null", Toast.LENGTH_SHORT).show();
                    Log.d("service_request", "onResponse: "+response);
                }
                else {
                    Log.d("service_request", "onResponse: "+response.body().getRequestlist());
                    progressBar.setVisibility(View.GONE);
                    Log.d("ServiceRequest", "onResponse: "+page+"lastpage"+lastpage);
                    List<Datum> list=new ArrayList<>();
                    for(Datum d: response.body().getRequestlist().getData()){
                        if(d.getStatus()==1){
                            list.add(d);
                        }
                    }
                    datum2List.addAll(list);

                    lastpage=response.body().getRequestlist().getLastPage();
                    size= list.size()+size;
                    totalsize=response.body().getRequestlist().getTotal();
                    Log.d("ServiceRequest", "onResponse: data"+size+"datum"+totalsize);
                    String p=String.valueOf(response.body().getRequestlist().getCurrentPage());



                    if(datum2List.size()==0){
                        Log.d("datum2list", "onResponse: "+datum2List.size());
                    }
                    else
                    {        GetServiceRequestAdapter getServiceRequestAdapter=new GetServiceRequestAdapter(getActivity(),datum2List);
                        rv_serviceRequest.setAdapter(getServiceRequestAdapter);
                        Log.d("last page", "onResponse: "+lastpage+"size "+datum2List.get(datum2List.size()-1).getId());
                        getServiceRequestAdapter.notifyDataSetChanged();}
                }
            }

            @Override
            public void onFailure(Call<CustomerRequestListmodel> call, Throwable t) {

            }
        });

    }
}