package client.legalease.Fragment.ServiceOfferFragment;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import client.legalease.Adapter.ServiceOfferAdapter;
import client.legalease.Model.ServiceOfferModel.Datum3;
import client.legalease.Model.ServiceOfferModel.ServiceOffermodel;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.R;
import client.legalease.RetrofitClient.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RejectedServiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class RejectedServiceFragment extends Fragment {
    RecyclerView rv_acceptedOrder;
    ProgressBar progressBar;
    int totalsize=0,size=0;
    Button previous, next;
    TextView pagenumber;
    List<Datum3> datum3List;
    boolean isscorolling=false;
    int page = 1;
    int lastpage = 1;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
private LinearLayout ll1;
    public RejectedServiceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RejectedServiceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RejectedServiceFragment newInstance(String param1, String param2) {
        RejectedServiceFragment fragment = new RejectedServiceFragment();
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
        View view = inflater.inflate(R.layout.fragment_rejected_service, container, false);
        initview(view);
        ll1.setVisibility(View.GONE);
        LinearLayoutManager manager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rv_acceptedOrder.setLayoutManager(manager);
        datum3List=new ArrayList<>();
        getservicedata(1);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(page>1){
                    getservicedata(--page);
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(page<lastpage&&lastpage!=1)
                {
                    getservicedata(++page);
                }
            }
        });
        rv_acceptedOrder.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isscorolling=true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(lastpage!=1){
                    if(isscorolling&& lastpage>page&& size!=totalsize){
                        getservicedata(++page);

                    }
                }
            }
        });
        return view;
    }
    private void getservicedata(int i) {
        CommonSharedPreference commonSharedPreference=new CommonSharedPreference(getContext());
        String token = "Bearer " + commonSharedPreference.getToken();

        int client_id=commonSharedPreference.getLoginSharedPref(getContext()).getId();
        Log.d("client_id", "getallrequest: "+client_id);
        final String p=String.valueOf(i);
        Call<ServiceOffermodel> call= RetrofitClient.getApiService().getServiceOffermodel(p,token,"rejected");
        call.enqueue(new Callback<ServiceOffermodel>() {
            @Override
            public void onResponse(Call<ServiceOffermodel> call, Response<ServiceOffermodel> response) {
                if(response.body()==null){
                    Toast.makeText(getContext(), "null", Toast.LENGTH_SHORT).show();
                }
                else {
                    lastpage=response.body().getOfferList().getLastPage();
                    progressBar.setVisibility(View.GONE);
             //       datum3List.addAll(response.body().getOfferList().getData());

           //         rv_acceptedOrder.setLayoutManager(manager);
                    List<Datum3>rejectedorderlist=new ArrayList<>();
                    for (int i=0;i< response.body().getOfferList().getData().size();i++
                    ) {
                        Datum3 d= response.body().getOfferList().getData().get(i);
                        if(d.getStatus().equals("2")){
                            rejectedorderlist.add(d);
                        }

                    }
                    datum3List.addAll(rejectedorderlist);
                    size= datum3List.size()+size;
                    totalsize=response.body().getOfferList().getTotal();
                    ServiceOfferAdapter serviceOfferAdapter=new ServiceOfferAdapter(getContext(),datum3List,2);
                    rv_acceptedOrder.setAdapter(serviceOfferAdapter);
                    serviceOfferAdapter.notifyDataSetChanged();



                }
            }

            @Override
            public void onFailure(Call<ServiceOffermodel> call, Throwable t) {

            }
        });

    }


    private void initview(View view) {
        rv_acceptedOrder=view.findViewById(R.id.rv_service_offer);
        progressBar=view.findViewById(R.id.progressBar_service_rorder);
        next=view.findViewById(R.id.nextspage);
        previous=view.findViewById(R.id.previouspage);
        pagenumber=view.findViewById(R.id.pagenum2);
        ll1=view.findViewById(R.id.ll1);
    }
}