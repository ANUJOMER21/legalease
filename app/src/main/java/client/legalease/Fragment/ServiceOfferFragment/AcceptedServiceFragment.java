package client.legalease.Fragment.ServiceOfferFragment;

import android.os.Bundle;
;
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

import client.legalease.Adapter.ServiceOfferAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

import client.legalease.Model.ServiceOfferModel.Datum3;
import client.legalease.Model.ServiceOfferModel.ServiceOffermodel;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.R;
import client.legalease.RetrofitClient.RetrofitClient;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AcceptedServiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AcceptedServiceFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int totalsize=0,size=0;
    boolean isscorolling=false;
RecyclerView rv_acceptedOrder;
ProgressBar progressBar;
Button previous,next;
TextView pagenumber,error;
LinearLayout ll1;
    int page=1;
    int lastpage=1;
    List<Datum3> datum3List1;
    public AcceptedServiceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AcceptedServiceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AcceptedServiceFragment newInstance(String param1, String param2) {
        AcceptedServiceFragment fragment = new AcceptedServiceFragment();
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
        datum3List1=new ArrayList<>();
        View view=inflater.inflate(R.layout.fragment_accepted_service, container, false);
        initview(view);
        LinearLayoutManager manager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rv_acceptedOrder.setLayoutManager(manager);
        datum3List1=new ArrayList<>();
        ll1.setVisibility(View.GONE);
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
            if(isscorolling&& lastpage>page && size!=totalsize){
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

getservicelist("1,3,4,5",p,token);

    }
  int rvstartpos=0;
    private void getservicelist(String pending, String p, String token) {

        Call<ServiceOffermodel> call= RetrofitClient.getApiService().getServiceOffermodel(p,token,pending);
        call.enqueue(new Callback<ServiceOffermodel>() {
            @Override
            public void onResponse(Call<ServiceOffermodel> call, Response<ServiceOffermodel> response) {
                Log.d("url", "onResponse: "+response.toString());
                if(response.body()==null){
                //    Toast.makeText(getContext(), "No Offer is Accepted", Toast.LENGTH_SHORT).show();

                    progressBar.setVisibility(View.GONE);
                } else if (response.body().getOfferList().getData().size()==0&&p.equals(1)) {
                //    error.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
                /**     else {
                         lastpage=response.body().getOfferList().getLastPage();


                         datum3List1.addAll(response.body().getOfferList().getData());
                         List<Datum3>rejectedorderlist=new ArrayList<>();
                         for (Datum3 data:response.body().getOfferList().getData()
                              ) {
                             if(data.getStatus().equals("2"))
                             {
                                 datum3List1.remove(data);
                             }
                         }
                         size= datum3List1.size()+size;
                         totalsize=response.body().getOfferList().getTotal();

                         progressBar.setVisibility(View.GONE);
                         ServiceOfferAdapter serviceOfferAdapter = new ServiceOfferAdapter(getContext(),datum3List1, 0);
                         rv_acceptedOrder.setAdapter(serviceOfferAdapter);
                         serviceOfferAdapter.notifyDataSetChanged();
                         rvstartpos=rvstartpos+response.body().getOfferList().getData().size();





                     }**/
                else {
                    lastpage=response.body().getOfferList().getLastPage();
                    progressBar.setVisibility(View.GONE);


                    //         rv_acceptedOrder.setLayoutManager(manager);

                    Log.d("response", "onResponse: "+response.body().getOfferList().getData().size());

                    datum3List1.addAll(response.body().getOfferList().getData());
                    size= datum3List1.size()+size;
                    totalsize=response.body().getOfferList().getTotal();
                    ServiceOfferAdapter serviceOfferAdapter=new ServiceOfferAdapter(getContext(),datum3List1,2);
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
        error=view.findViewById(R.id.errortxt);
        rv_acceptedOrder=view.findViewById(R.id.rv_service_offer);
        progressBar=view.findViewById(R.id.progressBar_service_rorder);
        next=view.findViewById(R.id.nextspage);
        previous=view.findViewById(R.id.previouspage);
        pagenumber=view.findViewById(R.id.pagenum2);
        ll1=view.findViewById(R.id.ll1);
    }
}