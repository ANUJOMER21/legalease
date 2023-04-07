package client.legalease.Fragment.Report;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import client.legalease.Adapter.Common.RecyclerItemClickListener;
import client.legalease.Adapter.ReportAdapter;
import client.legalease.DownloadTask.DownloadTask;
import client.legalease.HomeActivity;
import client.legalease.Model.ReportModel.ReportData;
import client.legalease.Model.ReportModel.ReportModel;
import client.legalease.Model.SheetModel.SheetData;
import client.legalease.Model.SheetModel.SheetModel;
import client.legalease.Preference.CommonSharedPreference;
import client.legalease.R;
import client.legalease.UserMoreDetailActivity;
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

public class ReportFragment extends Fragment {


    ImageView back;
    TextView tv_selectMonth,tv_year;
    TextView button_search;
    TextView serviceTitle;
    String[] monthList = {"January", "February", "March", "April",
            "May", "June", "July", "August", "September", "October", "November", "December"};
    String[] yearList = {"2016", "2017", "2018", "2019",
            "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};

    String month = "";
    String year = "";
    Dialog dialogMonth, dialogCategory;
    CommonSharedPreference commonSharedPreference;
    String token= "";
    ProgressBar progressBar;
    List<ReportData> reportDataList;
    RelativeLayout relative_data;
    PDFView pdfView;
    TextView tv_noData;
    String[] parts;
    RecyclerView rv_report;
    ReportAdapter reportAdapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_report, container, false);



        reportDataList = new ArrayList<>();
        commonSharedPreference = new CommonSharedPreference(getActivity());
        try {
            if (commonSharedPreference.getLoginSharedPref(getActivity()).getToken() != null) {
                token = commonSharedPreference.getLoginSharedPref(getActivity()).getToken();
            } else {
                token = "";
            }
        } catch (NullPointerException ignored) {

        }




        initializeViews(rootView);



        Calendar calendar = Calendar.getInstance();
        month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        year = String.valueOf(calendar.get(Calendar.YEAR));




        int m= Integer.parseInt(month);
        String monthString="";
        //Switch statement
        switch(m) {
            //case statements within the switch block
            case 1:
                monthString = "January";
                break;
            case 2:
                monthString = "February";
                break;
            case 3:
                monthString = "March";
                break;
            case 4:
                monthString = "April";
                break;
            case 5:
                monthString = "May";
                break;
            case 6:
                monthString = "June";
                break;
            case 7:
                monthString = "July";
                break;
            case 8:
                monthString = "August";
                break;
            case 9:
                monthString = "September";
                break;
            case 10:
                monthString = "October";
                break;
            case 11:
                monthString = "November";
                break;
            case 12:
                monthString = "December";
                break;


        }
        tv_selectMonth.setText(monthString);
        tv_year.setText(year);





        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
                getActivity().finishAffinity();

            }
        });

        tv_selectMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMonthListDialog();
            }
        });


        tv_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openYearListDialog();
            }
        });

        getMyData(token,month,year);

        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                getMyData(token,month,year);


            }
        });


        return rootView;
    }




    private void getMyData(String token,String month,String year) {
        ApiService api = UserMoreDetailActivity.RetroClient.getApiService();
        Call<ReportModel> call = api.getSheduleData(token,month,year);
        call.enqueue(new Callback<ReportModel>() {
            @Override
            public void onResponse(Call<ReportModel> call, Response<ReportModel> response) {
                int summery =0;
                String pdfFile = "";
                try {
                    reportDataList=response.body().getData();
                    summery = reportDataList.size();

                    if (summery==0){
                        progressBar.setVisibility(View.GONE);
                        tv_noData.setVisibility(View.VISIBLE);
                        rv_report.setVisibility(View.GONE);
                    }else{
                        progressBar.setVisibility(View.GONE);
                        tv_noData.setVisibility(View.GONE);
                        rv_report.setVisibility(View.VISIBLE);
                        pdfFile = IMAGEURL+reportDataList.get(0).getUploadedfile();



                        reportAdapter = new ReportAdapter(getContext(), reportDataList);
                        RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
                        rv_report.setLayoutManager(eLayoutManager);
//                recyclerView.setItemAnimator(new DefaultItemAnimator());
                        rv_report.setAdapter(reportAdapter);


                    }

                }catch (NullPointerException ignored){
                    progressBar.setVisibility(View.GONE);


                }catch (IndexOutOfBoundsException ignore){
                    progressBar.setVisibility(View.GONE);


                }

                rv_report.addOnItemTouchListener(
                        new RecyclerItemClickListener(getContext(), rv_report, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                String url = "";
                                Uri uri = null;
                                String[] urlName;
                                String filename = "";

                                try {
                                    url = IMAGEURL+reportDataList.get(position).getUploadedfile();
                                    uri = Uri.parse(url);
                                    urlName = reportDataList.get(position).getUploadedfile().split("/");
                                    filename= urlName[3];
                                }catch (NullPointerException ignored){

                                }catch (IndexOutOfBoundsException ignore){

                                }
//                                DownloadManager downloadManager = (DownloadManager)getContext().getSystemService(Context.DOWNLOAD_SERVICE);
//                                DownloadManager.Request request = new DownloadManager.Request(uri);
//                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                                Long reference = downloadManager.enqueue(request);
                                 new DownloadTask(getContext(), url,filename,uri);


                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                // do whatever
                            }
                        })
                );



            }

            @Override
            public void onFailure(Call<ReportModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(),"Please check your internet connection",Toast.LENGTH_SHORT).show();

            }
        });

    }





    private void initializeViews(View rootView) {
        serviceTitle = (TextView)rootView.findViewById(R.id.serviceTitle);
        back = (ImageView)rootView.findViewById(R.id.back);
        tv_selectMonth = (TextView)rootView.findViewById(R.id.tv_selectMonth);
        tv_year = (TextView)rootView.findViewById(R.id.tv_year);
        button_search = (TextView)rootView.findViewById(R.id.button_search);
        tv_noData = (TextView)rootView.findViewById(R.id.tv_noData);
        progressBar  =(ProgressBar)rootView.findViewById(R.id.progressBar);
        relative_data = (RelativeLayout)rootView.findViewById(R.id.relative_data);
        rv_report = (RecyclerView)rootView.findViewById(R.id.rv_report);

    }


    private void openMonthListDialog() {
        dialogMonth = new Dialog(getActivity());
        dialogMonth.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogMonth.setContentView(R.layout.dialog_month);
        dialogMonth.setCanceledOnTouchOutside(true);
        dialogMonth.setCancelable(true);
        dialogMonth.show();


        ListView listView = (ListView)dialogMonth.findViewById(R.id.lv_month);
        final ArrayAdapter<String> adapter;

        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, monthList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                int value = position + 1;
                month = String.valueOf(value);
                tv_selectMonth.setText(adapter.getItem(position));
                dialogMonth.dismiss();

            }
        });

    }

    private void openYearListDialog() {
        dialogMonth = new Dialog(getActivity());
        dialogMonth.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogMonth.setContentView(R.layout.dialog_month);
        dialogMonth.setCanceledOnTouchOutside(true);
        dialogMonth.setCancelable(true);
        dialogMonth.show();


        ListView listView = (ListView) dialogMonth.findViewById(R.id.lv_month);
        final ArrayAdapter<String> adapter;

        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, yearList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                year = adapter.getItem(position);
                tv_year.setText(year);
                dialogMonth.dismiss();

            }
        });

    }


}