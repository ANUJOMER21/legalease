package client.legalease.Fragment.ServiceDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;

import java.io.File;

import client.legalease.Model.Services.ServiceDetails.ServiceDetails;
import client.legalease.R;
import client.legalease.ServicesDetails;
import client.legalease.UserMoreDetailActivity;
import client.legalease.WebServices.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static client.legalease.APIConstant.ApiConstant.IMAGEURL;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentOverView extends Fragment {

    String id = "";
    String title = "";
    String subTitle = "";
    String price = "";
    String finalTital = "";
    String pdfFile = "";
    TextView tv_title,tv_subtile;
    ProgressBar progressBar;
    PDFView pdfView;
    TextView tv_noData;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_overview, container, false);

        tv_title=(TextView)rootView.findViewById(R.id.tv_title);
        tv_noData=(TextView)rootView.findViewById(R.id.tv_noData);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);
        pdfView = (PDFView)rootView.findViewById(R.id.pdfView);



        ServicesDetails activity = (ServicesDetails) getActivity();
        id = activity.getId();
        getServiceDetailsData();
        return rootView;
    }

    private void getServiceDetailsData() {
        ApiService api = UserMoreDetailActivity.RetroClient.getApiService();
        Call<ServiceDetails> call = api.getSeviceDetailsData(id);
        call.enqueue(new Callback<ServiceDetails>() {
            @Override
            public void onResponse(Call<ServiceDetails> call, Response<ServiceDetails> response) {
                progressBar .setVisibility(View.GONE);
//                try {
//               title = response.body().getOverview().get(0).getTitle();
//               subTitle = response.body().getOverview().get(0).getUploadedfile();
//               price =response.body().getSubservice().get(0).getPrice();
//                    pdfFile = response.body().getOverview().get(0).getUploadedfile();
//               finalTital=title +"\n\n"+ " Rs"+price;
//
//               tv_title.setText(finalTital);
//               tv_subtile.setText(subTitle);
//           }catch (NullPointerException ignored){
//
//           }catch (Exception ignore){
//
//           }










                int summery =0;
                String pdfFile = "";
                try {
                    summery = response.body().getOverview().size();

                    if (summery==0){
                        progressBar.setVisibility(View.GONE);
                        tv_noData.setVisibility(View.VISIBLE);
                        pdfView.setVisibility(View.GONE);
                    }else{
                        progressBar.setVisibility(View.GONE);
                        tv_noData.setVisibility(View.GONE);
                        pdfView.setVisibility(View.VISIBLE);
                        pdfFile = IMAGEURL+response.body().getOverview().get(0).getUploadedfile();



                        FileLoader.with(getContext())
                                .load(pdfFile)
                                .fromDirectory("test4", FileLoader.DIR_INTERNAL)
                                .asFile(new FileRequestListener<File>() {
                                    @Override
                                    public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                                        File file = response.getBody();

                                        pdfView.fromFile(file)
                                                .password(null)
                                                .defaultPage(0)
                                                .enableSwipe(true)
                                                .load();
                                        // do something with the file
                                    }

                                    @Override
                                    public void onError(FileLoadRequest request, Throwable t) {
                                    }
                                });




                    }

                }catch (NullPointerException ignored){
                    progressBar.setVisibility(View.GONE);


                }catch (IndexOutOfBoundsException ignore){
                    progressBar.setVisibility(View.GONE);


                }




            }

            @Override
            public void onFailure(Call<ServiceDetails> call, Throwable t) {
                Toast.makeText(getContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);

            }
        });

    }

}
