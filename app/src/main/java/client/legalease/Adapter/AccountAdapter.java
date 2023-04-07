package client.legalease.Adapter;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import client.legalease.BillUploadActivty;
import client.legalease.Model.AccountModel.AccountData;
import client.legalease.MyAccountantListActivity;
import client.legalease.R;
import client.legalease.ReportActivity;
import client.legalease.WebServices.ApiClient;
import client.legalease.WebServices.ApiService;

import static client.legalease.APIConstant.ApiConstant.IMAGEURL;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {

    ApiService apiService = ApiClient.getClient().create(ApiService.class);

    List<AccountData> accountData;
    private Context context;

    public AccountAdapter(Context context, List<AccountData> accountData) {
        this.context = context;
        this.accountData = accountData;
    }



    @NonNull
    @Override
    public AccountAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_account, viewGroup, false);


        return new AccountAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AccountAdapter.ViewHolder viewHolder, int i) {

        String name ="";

        String imageUrl = "";
        String finalImage = "";


        try {
            name = accountData.get(i).getName();

            imageUrl = accountData.get(i).getImage();

            viewHolder.account_name.setText(name);




        }catch (NullPointerException e){

        }catch (IndexOutOfBoundsException ignore){

        }
//
        finalImage =IMAGEURL+imageUrl;
//
//        if (imageUrl.equals("")||imageUrl.equals(null)){
//            Glide.with(context)
//                    .load(R.drawable.service)
//                    .apply(fitCenterTransform())
//                    .into(viewHolder.servie_image);
//
//        }else
//            Glide.with(context)
//                    .load(finalImage)
//                    .apply(fitCenterTransform())
//                    .into(viewHolder.servie_image);


    }

    @Override
    public int getItemCount() {
        return accountData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView account_name;

        de.hdodenhof.circleimageview.CircleImageView servie_image;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            account_name = (TextView) itemView.findViewById(R.id.account_name);
            servie_image = (de.hdodenhof.circleimageview.CircleImageView) itemView.findViewById(R.id.service_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String parentId = "";
                    String serviceTitle = "";
                    int pos = getAdapterPosition();
                    AccountData mydata = null;

                    try {
                        mydata = accountData.get(pos);
                        parentId = String.valueOf(mydata.getId());
                        serviceTitle = mydata.getName();

                    }catch (NullPointerException ignored){

                    }catch (IndexOutOfBoundsException ignore){

                    }

                    if (parentId.equals("1")){
                        Intent intent  =new Intent(context, MyAccountantListActivity.class);
                        intent.putExtra("parentId",parentId);
                        intent.putExtra("serviceTitle",serviceTitle);
                        context.startActivity(intent);

                    }else if (parentId.equals("2")){

                        Intent intent  =new Intent(context, BillUploadActivty.class);
                        intent.putExtra("parentId",parentId);
                        intent.putExtra("serviceTitle",serviceTitle);
                        context.startActivity(intent);
                    }else {
                        Intent intent  =new Intent(context, ReportActivity.class);
                        intent.putExtra("parentId",parentId);
                        intent.putExtra("serviceTitle",serviceTitle);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

}
