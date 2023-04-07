package client.legalease.Adapter;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;

import client.legalease.APIConstant.ApiConstant;
import client.legalease.HomeActivity;
import client.legalease.Model.PartnerModel.Datum;
import client.legalease.PartnerDetail;
import client.legalease.R;
import client.legalease.ServiceOfferDetail;
import client.legalease.ViewAllPartner;
import de.hdodenhof.circleimageview.CircleImageView;

public class viewallPartnerAdapter extends RecyclerView.Adapter<viewallPartnerAdapter.ViewHolder> {
    Context context;
    List<Datum> list;
String servicename;
    public viewallPartnerAdapter(Context context, List<Datum> list,String servicename) {
        this.context = context;
        this.list = list;
        this.servicename=servicename;
    }

    @NonNull
    @Override
    public viewallPartnerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_all_partner, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewallPartnerAdapter.ViewHolder viewHolder, int i) {
                     String name="",service="",image="";
        try {
            name = list.get(i).getName();
            service = list.get(i).getCompanyName();
            image = list.get(i).getPhoto();

            viewHolder.name.setText(name);
            viewHolder.service.setText("Company :-"+service);
            Glide.with(context).load(ApiConstant.IMAGEURL +image).into(viewHolder.circleImageView);






        }catch (NullPointerException e){

        }catch (IndexOutOfBoundsException ignore){

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView circleImageView;
        TextView name,service;
        CardView cv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView=itemView.findViewById(R.id.tv_userimage);
            name=itemView.findViewById(R.id.tv_name);
            service=itemView.findViewById(R.id.tv_service);
            cv=itemView.findViewById(R.id.partnercv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Gson gson=new Gson();
                    int position=getAdapterPosition();
                    String data=gson.toJson(list.get(position));
                    String from="0";
                    if(context instanceof HomeActivity ){
                        from="1";
                    }
                    Intent i=new Intent(context, PartnerDetail.class);
                    i.putExtra("partner_detail",data);
                    i.putExtra("from",from);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("service", servicename);
                    Long assid = Long.valueOf(list.get(position).getId());
                    i.putExtra("assid", assid.toString());
                    context.startActivity(i);

                }
            });
 /** itemView.setOnClickListener(new View.OnClickListener() {

    public void onClick(View v) {
        int pos=getAdapterPosition();
        Gson gson=new Gson();
        String data=gson.toJson(list.get(pos));
        Intent i=new Intent(context, client.legalease.PartnerDetail.class);


        i.putExtra("partner_detail",data);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
});
**/


        }

        @Override
        public void onClick(View v) {

        }
    }
}
