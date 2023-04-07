package client.legalease.Adapter;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import client.legalease.Model.Services.ServicesParentModule.Service;
import client.legalease.R;
import client.legalease.SubServicesActivity;
import client.legalease.WebServices.ApiClient;
import client.legalease.WebServices.ApiService;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;
import static client.legalease.APIConstant.ApiConstant.IMAGEURL;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {

    ApiService apiService = ApiClient.getClient().create(ApiService.class);

    List<Service> servicesData;
    private Context context;

    public ServicesAdapter(Context context, List<Service> servicesData) {
        this.context = context;
        this.servicesData = servicesData;
    }



    @NonNull
    @Override
    public ServicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_services, viewGroup, false);


        return new ServicesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ServicesAdapter.ViewHolder viewHolder, int i) {

        String name ="";

        String imageUrl = "";
        String finalImage = "";


        try {
            name = servicesData.get(i).getTitle();

            imageUrl = servicesData.get(i).getPhoto();

            viewHolder.service_text.setText(name);




        }catch (NullPointerException e){

        }catch (IndexOutOfBoundsException ignore){

        }

        finalImage =IMAGEURL+imageUrl;

        if (imageUrl.equals("")||imageUrl.equals(null)){
            Glide.with(context)
                    .load(R.drawable.service)
                    .apply(fitCenterTransform())
                    .into(viewHolder.servie_image);

        }else
        Glide.with(context)
                .load(finalImage)
                .apply(fitCenterTransform())
                .into(viewHolder.servie_image);


    }

    @Override
    public int getItemCount() {
        return servicesData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView service_text;

        de.hdodenhof.circleimageview.CircleImageView servie_image;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            service_text = (TextView) itemView.findViewById(R.id.servie_name);
            servie_image = (de.hdodenhof.circleimageview.CircleImageView) itemView.findViewById(R.id.service_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String parentId = "";
                    String serviceTitle = "";
                    int pos = getAdapterPosition();
                    Service mydata = null;

                    try {
                        mydata = servicesData.get(pos);
                        parentId = String.valueOf(mydata.getId());
                        serviceTitle = mydata.getTitle();

                    }catch (NullPointerException ignored){

                    }catch (IndexOutOfBoundsException ignore){

                    }
                    Intent intent  =new Intent(context, SubServicesActivity.class);
                    intent.putExtra("parentId",parentId);
                    intent.putExtra("serviceTitle",serviceTitle);
                    context.startActivity(intent);

                }
            });
        }
    }

}
