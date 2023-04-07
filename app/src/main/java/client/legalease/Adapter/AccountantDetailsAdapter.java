package client.legalease.Adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Build;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

import client.legalease.Model.AccountantDetails.AccountantData;
import client.legalease.R;
import client.legalease.WebServices.ApiClient;
import client.legalease.WebServices.ApiService;

public class AccountantDetailsAdapter extends RecyclerView.Adapter<AccountantDetailsAdapter.ViewHolder> {

    ApiService apiService = ApiClient.getClient().create(ApiService.class);

    List<AccountantData> accountantDataList;
    private Context context;

    public AccountantDetailsAdapter(Context context, List<AccountantData> accountantDataList) {
        this.context = context;
        this.accountantDataList = accountantDataList;
    }



    @NonNull
    @Override
    public AccountantDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_accountant_details, viewGroup, false);


        return new AccountantDetailsAdapter.ViewHolder(view);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull final AccountantDetailsAdapter.ViewHolder viewHolder, int i) {

        String inTime ="";
        String outTime ="";
        String craetedTime ="";
        String totalTime = "";
        String slNumber = "";
        SimpleDateFormat simpleDateFormat,simpleDateFormat1;
        String final_inTime= "";
        String final_outTime = "";
        Date date_in;
        Date date_out;
        date_in = new Date();
        date_out = new Date();




        try {
            inTime = accountantDataList.get(i).getIntime();
            outTime = accountantDataList.get(i).getOuttime();
            totalTime = accountantDataList.get(i).getTotalhours();
            craetedTime = accountantDataList.get(i).getCreatedAt();
            slNumber = String.valueOf(i+1);

            String[] date = craetedTime.split("\\s+");
            String final_Time = date[0];

//            Calendar calendar =Calendar.getInstance();
//
//            String muValue = DateFormat.getDateInstance(DateFormat.FULL).format(craetedTime);
//            Toast.makeText(context,muValue,Toast.LENGTH_SHORT).show();

//            SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd,yyyy hh:mm a");
//            Date date = null;
//            try
//            {
//                date = sdf.parse(craetedTime);
//            }
//            catch(Exception ex)
//            {
//                ex.printStackTrace();
//            }
//            SimpleDateFormat formatter = new SimpleDateFormat("EEEE MMMM dd,yyyy hh:mm a");
//            String newFormat = formatter.format(date);
            viewHolder.tv_inTime.setText(inTime);
            viewHolder.tv_outTime.setText(outTime);
            viewHolder.tv_totalTime.setText(totalTime + " hr");
            viewHolder.createdDate.setText(final_Time);
            viewHolder.slno.setText(slNumber);




        }catch (NullPointerException e){

        }catch (IndexOutOfBoundsException ignore){

        }


    }

    @Override
    public int getItemCount() {
        return accountantDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView createdDate,tv_inTime,tv_outTime,tv_totalTime,slno;
        ProgressBar image_progress;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            createdDate = (TextView) itemView.findViewById(R.id.createdDate);
            tv_inTime = (TextView) itemView.findViewById(R.id.tv_inTime);
            tv_outTime = (TextView) itemView.findViewById(R.id.tv_outTime);
            tv_totalTime = (TextView) itemView.findViewById(R.id.tv_totalTime);
            slno = (TextView) itemView.findViewById(R.id.slno);




        }
    }

}
