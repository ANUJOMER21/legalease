package client.legalease.Adapter;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.haozhang.lib.SlantedTextView;

import java.util.List;

import client.legalease.APIConstant.ApiConstant;
import client.legalease.Model.Services.SubServices.Subservice;
import client.legalease.R;
import client.legalease.ServicesDetails;
import client.legalease.SubServicesActivity;

public class SubServicesAdapter extends RecyclerView.Adapter<SubServicesAdapter.ViewHolder> {


    List<Subservice> subservices;
    private Context context;

    public SubServicesAdapter(Context context, List<Subservice> servicesData) {
        this.context = context;
        this.subservices = servicesData;
    }



    @NonNull
    @Override
    public SubServicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_subservices, viewGroup, false);


        return new SubServicesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SubServicesAdapter.ViewHolder viewHolder, int i) {

        String title ="";
        String subTitle = "";
        String photo = "";





        try {
            title = subservices.get(i).getTitle();

            subTitle = subservices.get(i).getSubTitle();
           // price = subservices.get(i).getPrice();
            photo= subservices.get(i).getPhoto();
            String finalimage=ApiConstant.IMAGEURL+photo;
            if (photo.equals("")||photo.equals(null)){
                Glide.with(context)
                        .load(R.drawable.service)
                        .apply(fitCenterTransform())
                        .into(viewHolder.servie_image);

            }else
                Glide.with(context)
                        .load(finalimage)
                        .apply(fitCenterTransform())
                        .into(viewHolder.servie_image);


            viewHolder.tv_title.setText(title);
            viewHolder.tv_subTitle.setText(subTitle);
         //  viewHolder.stv.setText(price);





        }catch (NullPointerException e){

        }catch (IndexOutOfBoundsException ignore){

        }


    }

    @Override
    public int getItemCount() {
        return subservices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title,tv_subTitle;


        de.hdodenhof.circleimageview.CircleImageView servie_image;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_subTitle = (TextView) itemView.findViewById(R.id.tv_subTitle);
            servie_image = itemView.findViewById(R.id.tv_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String parentId = "";
                    String name = "";
                    String serviceId="";
                    String price = "";
                    int pos = getAdapterPosition();
                    Subservice mydata = null;

                    try {
                        mydata = subservices.get(pos);
                        parentId = String.valueOf(mydata.getId());

                        name = mydata.getTitle();
                        price = mydata.getPrice();

                    }catch (NullPointerException ignored){

                    }catch (IndexOutOfBoundsException ignore){

                    }
                    Intent intent  =new Intent(context, ServicesDetails.class);
                    intent.putExtra("parentId",parentId);
                    intent.putExtra("name",name);
                    intent.putExtra("price",price);
                    context.startActivity(intent);
                    if(context instanceof SubServicesActivity){
                      ((SubServicesActivity) context).finish();
                    }
                }
            });
        }
    }

}
