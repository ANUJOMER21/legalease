package client.legalease.Adapter;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;
import static com.krishna.fileloader.utility.FileExtension.JSON;
import static client.legalease.APIConstant.ApiConstant.IMAGEURL;

import android.content.Context;
import android.content.Intent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;

import client.legalease.Model.PartnerModel.PartnerDetail;
import client.legalease.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class PartnerGridAdapterhome extends RecyclerView.Adapter<PartnerGridAdapterhome.ViewHolder>{
    Context context;
    List<PartnerDetail> partnerDetails;

    public PartnerGridAdapterhome(Context context, List<PartnerDetail> partnerDetails) {
        this.context = context;
        this.partnerDetails = partnerDetails;
    }

    @NonNull
    @Override
    public PartnerGridAdapterhome.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_partner_detail_home,viewGroup,false);
        return new PartnerGridAdapterhome.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PartnerGridAdapterhome.ViewHolder viewHolder, int i) {
        String name ="";

        String imageUrl = "";
        String finalImage = "";


        try {
            name = partnerDetails.get(i).getName();

            imageUrl = partnerDetails.get(i).getPhoto();

            viewHolder.name.setText(name);




        }catch (NullPointerException e){

        }catch (IndexOutOfBoundsException ignore){

        }

        finalImage =imageUrl;

        if (imageUrl.equals("")||imageUrl.equals(null)){
            Glide.with(context)
                    .load(R.drawable.service)
                    .apply(fitCenterTransform())
                    .into(viewHolder.partner_image);

        }else
            Glide.with(context)
                    .load(finalImage)
                    .apply(fitCenterTransform())
                    .into(viewHolder.partner_image);


    }



    @Override
    public int getItemCount() {
        return partnerDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView partner_image;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            partner_image=itemView.findViewById(R.id.partner_image);
            name=itemView.findViewById(R.id.partner_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    PartnerDetail partnerDetail=null;
                    try{
                        partnerDetail=partnerDetails.get(pos);

                    }catch (NullPointerException ignored){

                    }catch (IndexOutOfBoundsException ignore){

                    }
                    Gson gson=new Gson();
                    String partnergson= gson.toJson(partnerDetail);
                    Log.d("partner_detail", "onClick: "+partnergson);
                    Intent i=new Intent(context, client.legalease.PartnerDetail.class);
                    i.putExtra("partner_detail",partnergson);
                    context.startActivity(i);
                }
            });

        }
    }
}
