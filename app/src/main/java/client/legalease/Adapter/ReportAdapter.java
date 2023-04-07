package client.legalease.Adapter;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import client.legalease.CheckForSDCard;
import client.legalease.DownloadTask.DownloadServerFile;
import client.legalease.DownloadTask.DownloadTask;
import client.legalease.Model.ReportModel.ReportData;
import client.legalease.R;
import client.legalease.WebServices.ApiClient;
import client.legalease.WebServices.ApiService;

import static client.legalease.APIConstant.ApiConstant.IMAGEURL;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {

    ApiService apiService = ApiClient.getClient().create(ApiService.class);

    public static List<ReportData> reportData;

    private Context context;
    DownloadManager downoadManager;

    public ReportAdapter(Context context, List<ReportData> reportData) {
        this.context = context;
        this.reportData = reportData;
    }



    @NonNull
    @Override
    public ReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_report, viewGroup, false);


        return new ReportAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ReportAdapter.ViewHolder viewHolder, int i) {

        String title_value="";
        String[] parts;



        try {
            parts = reportData.get(0).getUploadedfile().split("/");
            String part = parts[3];

            viewHolder.title.setText(part);



        }catch (NullPointerException e){

        }catch (IndexOutOfBoundsException ignore){

        }


    }

    @Override
    public int getItemCount() {
        return reportData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView iv_image;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            iv_image =(ImageView)itemView.findViewById(R.id.iv_image);
            iv_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "";
                    int pos =0;
                    String[] urlName;
                    String filename = "";

                    ReportData rData = null;
                    try {
                        pos =getAdapterPosition();
                        rData = reportData.get(pos);
                        url =IMAGEURL+rData.getUploadedfile();
                        urlName = reportData.get(0).getUploadedfile().split("/");
                        filename= urlName[3];
//                        new DownloadTask(context, url,filename);
//                       new DownloadServerFile(context,url,filename);

                    }catch (NullPointerException ignored){

                    }catch (IndexOutOfBoundsException ignored){

                    }


                }
            });
        }
    }

}
