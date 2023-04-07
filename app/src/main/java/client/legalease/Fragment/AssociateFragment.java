package client.legalease.Fragment;

import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import client.legalease.Adapter.MeetingAdapter;
import client.legalease.Adapter.ReachUsAdapter;
import client.legalease.Adapter.viewallPartnerAdapter;
import client.legalease.Common.CustomLinearLayoutManager;
import client.legalease.HomeActivity;
import client.legalease.Model.Meetingbookmodel.Meetingschedulemodel;
import client.legalease.Model.PartnerModel.Datum;
import client.legalease.Model.PartnerModel.Partnermodel;
import client.legalease.Model.ReachUsModel.ReachUsData;
import client.legalease.Model.ReachUsModel.ReachUsModel;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.R;
import client.legalease.RetrofitClient.RetrofitClient;
import client.legalease.ViewAllPartner;
import client.legalease.WebServices.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static client.legalease.APIConstant.ApiConstant.IMAGEURL;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AssociateFragment extends Fragment {
    ImageView back;

    ProgressBar progressBar;
    Boolean isscrolling=false;

    List<Datum> datumList;
    private String  price, servicename, lat, lon;
    CommonSharedPreference commonSharedPreference;
    String token = "";
    int id = 0;
    String mobile = "";
    String name = "";
    String latitude = "";
    String longitude = "";
    List<ReachUsData> reachUsData = new ArrayList<>();
    ReachUsAdapter reachUsAdapter;
    RecyclerView rv_associate;


    int page=1;
    int lastpage=1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_associate, container, false);
        HomeActivity activity = (HomeActivity) getActivity();
        rv_associate=(RecyclerView)rootView.findViewById(R.id.rv_viewallpartner);
        datumList = new ArrayList<>();
        LinearLayoutManager manager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rv_associate.setLayoutManager(manager);

        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar_viewall);
commonSharedPreference=new CommonSharedPreference(getContext());

        try {
            getallpartner(page);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        rv_associate.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                       if(isscrolling&& lastpage>page){
                           try {
                               getallpartner(page++);
                           } catch (IOException e) {
                               throw new RuntimeException(e);
                           }

                       }
                   }
               }
           });
        return rootView;
    }
    private void getallpartner(final int page) throws IOException{
        Log.d("data1", "getallpartner: ");
        CommonSharedPreference commonSharedPreference = new CommonSharedPreference(getContext());
       String token = "Bearer " + commonSharedPreference.getToken();
        //String token="Bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczovL3Byb2Zlc3Npb25hbHNhYXRoaS5jb20vYXBpL3YxL3BhcnRuZXJMb2dpbiIsImlhdCI6MTY3OTIxMjUwMiwiZXhwIjoxNjk0OTgwNTAyLCJuYmYiOjE2NzkyMTI1MDIsImp0aSI6Ijg3bHh1aTlqWTVHbDljSHoiLCJzdWIiOjEzMjYsInBydiI6Ijg3ZTBhZjFlZjlmZDE1ODEyZmRlYzk3MTUzYTE0ZTBiMDQ3NTQ2YWEifQ.cWo2VJgx0slo0FrxZxpv5c_wiFaPRmL2g_eibz6ecR8";

     Call<Meetingschedulemodel>call=RetrofitClient.getApiService().meetings(String.valueOf(page),token);
     call.enqueue(new Callback<Meetingschedulemodel>() {
         @Override
         public void onResponse(Call<Meetingschedulemodel> call, Response<Meetingschedulemodel> response) {
             Log.d("data", "onResponse: "+response.body().toString());
             if(response.body().getStatus().equals("success")){
                 progressBar.setVisibility(View.GONE);
                 if(response.body().getMeetinglist().getData()!=null){
                     MeetingAdapter meetingAdapter =new MeetingAdapter(getContext(),response.body().getMeetinglist().getData());
                     rv_associate.setAdapter(meetingAdapter);
                     }
                 }

             }


         @Override
         public void onFailure(Call<Meetingschedulemodel> call, Throwable t) {

         }
     });





    }



    private void getData() {
            ApiService api = RetrofitClient.getApiService();
        String token = "Bearer " + commonSharedPreference.getToken();
     //   Call <Partnermodel> call = RetrofitClient.getApiService().getAssociateList(String.valueOf(page),token,commonSharedPreference.getlat(),commonSharedPreference.getlon(),id);


        }
}